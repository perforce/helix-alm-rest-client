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

import com.perforce.halm.rest.responses.SubmitAutomationBuildResponse;
import com.perforce.halm.rest.responses.MenuResponse;
import com.perforce.halm.rest.responses.ProjectListResponse;
import com.perforce.halm.rest.types.VersionInfo;
import com.perforce.halm.rest.types.automation.build.AutomationBuild;
import com.perforce.halm.rest.types.automation.suite.AutomationSuitesContainer;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Helix ALM REST API interface
 */
interface HelixALM {
    /**
     * Retrieve Helix ALM Server version information
     *
     * @return Helix ALM Server version information
     */
    @RequestLine("GET /helix-alm/api/v0/versions")
    VersionInfo getVersions();

    /**
     * Retrieves the Helix ALM Server projects.
     *
     * @param authHeader Authorization header. This can be either Basic or ApiKey.
     * @return Helix ALM project listing
     */
    @RequestLine("GET /helix-alm/api/v0/projects")
    @Headers("Authorization: {authHeader}")
    ProjectListResponse getProjects(@Param("authHeader") String authHeader);

    /**
     * Retrieves a dropdown menu definition
     *
     * @param authHeader Authorization header. This can be either Basic or ApiKey.
     * @param projectID Project identifier. This can be either a Project recordID, UUID, or name
     * @param menuID Menu identifier
     * @return Returns a menu definition
     */
    @RequestLine("GET /helix-alm/api/v0/{projectID}/configs/menus/{menuID}?expand=items,fields")
    @Headers("Authorization: {authHeader}")
    MenuResponse getMenu(@Param("authHeader") String authHeader, @Param("projectID") String projectID, @Param("menuID") String menuID);

    /**
     * Retrieves a secure Helix ALM Server authorization token.
     *
     * @param authHeader Authorization header. This can be either Basic or ApiKey
     * @param projectID Project identifier. This can be either a Project recordID, UUID, or name
     * @return Returns an auth token
     */
    @RequestLine("GET /helix-alm/api/v0/{projectID}/token")
    @Headers("Authorization: {authHeader}")
    AuthInfoToken getAuthToken(@Param("authHeader") String authHeader, @Param("projectID") String projectID);

    /**
     * Retrieves a list of Automation Suites
     *
     * @param authHeader Authorization header. This must be an AuthToken.
     * @return Returns authorization suites
     */
    @RequestLine("GET /helix-alm/api/v0/{projectID}/automationSuites")
    @Headers("Authorization: {authHeader}")
    AutomationSuitesContainer getAutomationSuites(@Param("authHeader") String authHeader, @Param("projectID") String projectID);

    /**
     * Submits a build to the specified automation suite
     *
     * @param automationBuild Automation build to submit, by passing this as the first parameter it will become the request body.
     * @param authHeader Authorization header. This must be an AuthToken.
     * @param projectID Project identifier. This can be either a Project recordID, UUID, or name
     * @param suiteID Automation suite identifier
     * @return Returns a stub for the build that was created
     */
    @RequestLine("POST /helix-alm/api/v0/{projectID}/automationSuites/{suiteID}/submitBuild")
    @Headers({"Authorization: {authHeader}", "Content-Type: application/json"})
    SubmitAutomationBuildResponse submitAutomationBuild(AutomationBuild automationBuild, @Param("authHeader") String authHeader, @Param("projectID") String projectID, @Param("suiteID") String suiteID);
}
