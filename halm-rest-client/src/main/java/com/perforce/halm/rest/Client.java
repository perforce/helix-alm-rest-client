/*
 * *****************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2022, Perforce Software, Inc.  
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to use, 
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
 * Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 * *****************************************************************************
 */

package com.perforce.halm.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.perforce.halm.rest.responses.AbstractAPIResponse;
import com.perforce.halm.rest.responses.ErrorResponse;
import com.perforce.halm.rest.responses.MenuResponse;
import com.perforce.halm.rest.responses.ProjectListResponse;
import com.perforce.halm.rest.responses.SubmitAutomationBuildResponse;
import com.perforce.halm.rest.types.VersionInfo;
import com.perforce.halm.rest.types.automation.build.AutomationBuild;
import com.perforce.halm.rest.types.automation.build.AutomationBuildRunConfiguration;
import com.perforce.halm.rest.types.automation.build.AutomationBuildRunConfigurationMarshaller;
import com.perforce.halm.rest.types.automation.jenkins.JenkinsBuildParameter;
import com.perforce.halm.rest.types.automation.jenkins.JenkinsBuildParameterMarshaller;
import com.perforce.halm.rest.types.deserializers.InstantDeserializer;
import com.perforce.halm.rest.types.automation.suite.AutomationSuiteRunConfiguration;
import com.perforce.halm.rest.types.automation.suite.AutomationSuiteRunConfigurationMarshaller;
import com.perforce.halm.rest.types.automation.suite.AutomationSuite;
import com.perforce.halm.rest.types.automation.suite.AutomationSuitesContainer;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.OkHttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

interface GenericAPIRequest <T extends AbstractAPIResponse> {
    T call();
}

/**
 * Helix ALM REST API client
 */
public class Client {
    private final ConnectionInfo connectionInfo;
    private final HelixALM halmClient;
    private final Gson gson = this.buildGson();

    private final HashMap<String, AuthInfoToken> projectTokens = new HashMap<>();

    /**
     * Constructor
     *
     * @param inConnectionInfo - Helix ALM REST API connection information
     */
    public Client(final ConnectionInfo inConnectionInfo) {
        this.connectionInfo = inConnectionInfo;

        OkHttpClient httpClient;
        if (inConnectionInfo.pemCerts == null) {
            httpClient = HttpClientUtils.getSafeOkHttpClient();
        }
        else {
            httpClient = HttpClientUtils.getTrustingOkHttpClient(inConnectionInfo.pemCerts);
        }

        this.halmClient = Feign.builder()
            .client(new feign.okhttp.OkHttpClient(httpClient))
            .encoder(new GsonEncoder(this.gson))
            .decoder(new GsonDecoder(this.gson))
            .target(HelixALM.class, connectionInfo.url);
    }

    /**
     * Checks the certificate status for the currently configured connection.
     *
     * @return Information about the current certificate status.
     */
    public CertificateInfo getServerCertStatus() {
        return CertUtils.getServerCertStatus(this.connectionInfo);
    }

    /**
     * Retrieve version information from the REST API.
     *
     * @return REST API, HALM Server version information.
     */
    public VersionInfo getVersions() {
        return this.halmClient.getVersions();
    }

    /**
     * Checks to see if the currently configured connection has a live REST API server on the other side.
     * This request ignores all certificate and security concerns and will attempt to get version information about
     * whatever is on the other side.
     *
     * @return null on success, the exception that attempting to connect resulted in otherwise.
     */
    public Exception doesServerExist() {
        Exception err = null;
        OkHttpClient unsafeClient = HttpClientUtils.getUnsafeOkHttpClient();

        HelixALM tmpClient = Feign.builder()
            .client(new feign.okhttp.OkHttpClient(unsafeClient))
            .encoder(new GsonEncoder(this.gson))
            .decoder(new GsonDecoder(this.gson))
            .target(HelixALM.class, this.connectionInfo.getUrl());

        try {
            // Run the version request. If this is the REST API, it should succeed.
            tmpClient.getVersions();
        }
        catch (Exception ex) {
            err = ex;
        }

        return err;
    }

    /**
     * Retrieves the Helix ALM Server projects.
     *
     * @return Helix ALM project information
     */
    public ProjectListResponse getProjects() {
        return this.callAPIHandleErrors(() -> this.halmClient.getProjects(this.connectionInfo.authInfo.getAuthorizationHeader()), ProjectListResponse::new);
    }

    /**
     * Retrieves a specific menu definition.
     *
     * @param projectID Helix ALM project identifier
     * @param menuID Menu identifier.
     * @return Menu information
     */
    public MenuResponse getMenu(final String projectID, final String menuID) {
        return this.callAPIHandleErrors(() -> this.halmClient.getMenu(this.getAuthorizationHeader(projectID), projectID, menuID), MenuResponse::new);
    }

    /**
     * Retrieves a Helix ALM authorization token, this retrieved token is cached on this client instance.
     *
     * @param projectID Helix ALM project identifier.
     * @return Helix ALM Authorization token
     */
    public AuthInfoToken getAuthToken(final String projectID) {
        AuthInfoToken token = this.halmClient.getAuthToken(this.connectionInfo.authInfo.getAuthorizationHeader(),
            projectID);

        this.projectTokens.put(projectID, token);
        return token;
    }

    /**
     * Retrieves Helix ALM automation suites from the specified project
     *
     * @param projectID Helix ALM project identifier
     * @return List of AutomationSuites
     */
    public List<AutomationSuite> getAutomationSuites(final String projectID) {
        AuthInfoToken authToken = this.getNewOrExistingAuthToken(projectID);
        AutomationSuitesContainer suitesContainer = this.halmClient.getAutomationSuites(authToken.getAuthorizationHeader(), projectID);

        return suitesContainer.getAutomationSuitesData();
    }

    /**
     * Submits a build and its results to a Helix ALM automation suite
     *
     * @param automationBuild The build to submit
     * @param projectID Helix ALM project identifier
     * @param suiteID Helix ALM automation suite identifier
     * @return Submit automation build response
     */
    public SubmitAutomationBuildResponse submitAutomationBuild(final AutomationBuild automationBuild, final String projectID, final String suiteID) {
        return this.callAPIHandleErrors(() -> this.halmClient.submitAutomationBuild(automationBuild, this.getAuthorizationHeader(projectID), projectID, suiteID), SubmitAutomationBuildResponse::new);
    }

    /**
     * Convenience function to get an authorization header for the specified project
     *
     * @param projectID The project ID to get the authorization header string for
     * @return See description
     */
    protected String getAuthorizationHeader(final String projectID) {
        AuthInfoToken authToken = this.getNewOrExistingAuthToken(projectID);
        return authToken != null ? authToken.getAuthorizationHeader() : "";
    }

    /**
     * Parses an error response body.
     *
     * @param errorBody Body of the error response
     * @return A parsed ErrorResponse
     */
    protected ErrorResponse parseErrorResponse(String errorBody) {
        ErrorResponse response = null;

        try {
            response = gson.fromJson(errorBody, ErrorResponse.class);
        }
        catch (JsonSyntaxException ex) {
            // Don't want the error handling to throw an exception.
            //todo: Enhancement: Improve logging in this area.
            ex.printStackTrace();
        }

        return response;
    }

    /**
     * Convenience function that generically handles all API call errors
     *
     * @param request The request lambda to process that will return the response if successful
     * @param responseSupplier The supplier to create an object of type T if we are forced to create our own
     * @return See description
     * @param <T> The type of response we are dealing with for this API call
     */
    protected <T extends AbstractAPIResponse> T callAPIHandleErrors(GenericAPIRequest<T> request, Supplier<T> responseSupplier) {
        T response;
        try {
            response = request.call();
        }
        catch (FeignException ex) {
            //todo: Enhancement: Improve logging in this area.

            ErrorResponse errorResponse = null;
            if (ex.responseBody().isPresent()) {
                String responseBody = StandardCharsets.UTF_8.decode(ex.responseBody().get()).toString();
                errorResponse = parseErrorResponse(responseBody);
            }

            response = responseSupplier.get();
            response.handleFeignException(ex, errorResponse);
        }
        return response;
    }

    /**
     * Checks if we already have an auth token for the specified project, retrieves one if needed, and returns it .
     *
     * @param projectID Project identifier
     * @return Returns a Helix ALM auth token
     */
    protected AuthInfoToken getNewOrExistingAuthToken(final String projectID) {
        AuthInfoToken result = this.projectTokens.get(projectID);

        if (result == null) {
            result = this.getAuthToken(projectID);
        }

        return result;
    }

    /**
     * Builds an instance of Gson. This function exists so that we can unit test deserialization.
     *
     * @return Gson instance
     */
    protected Gson buildGson() {
        return new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantDeserializer())
            .registerTypeAdapter(AutomationSuiteRunConfiguration.class, new AutomationSuiteRunConfigurationMarshaller())
            .registerTypeAdapter(AutomationBuildRunConfiguration.class, new AutomationBuildRunConfigurationMarshaller())
            .registerTypeAdapter(JenkinsBuildParameter.class, new JenkinsBuildParameterMarshaller())
            .create();
    }
}
