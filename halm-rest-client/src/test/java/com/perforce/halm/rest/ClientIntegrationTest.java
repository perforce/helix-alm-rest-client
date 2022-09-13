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

import com.perforce.halm.rest.types.VersionInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class ClientIntegrationTest {
    private final static ConnectionInfo sharedConnInfo = new ConnectionInfo(
        "https://localhost:8443",
        "administrator",
        "");

    @BeforeAll
    static void setup() throws Exception {
        var certInfo = CertUtils.getServerCertStatus(sharedConnInfo);
        sharedConnInfo.pemCerts = certInfo.getPemCertificates();
    }

    @Test
    void getProjects() {
        var client = new Client(sharedConnInfo);
        var projects = client.getProjects();
        assertNotNull(projects, "List of returned projects shouldn't be null");
        assertEquals(3, projects.getProjects().size(), "Expected 3 projects to be returned.");
    }

    @Test
    void getMenu() {
        var client = new Client(sharedConnInfo);
        var projects = client.getProjects();
        assertTrue(projects.getProjects().size() > 0, "Expected at least one project");

        var projectID = projects.getProjects().get(0).getUuid();

        AuthInfoToken token = client.getAuthToken(projectID);
        assertNotNull(token, "AuthInfo token was null");
        assertFalse(token.isExpired(), "Token was expired");

        // Get a menu definition. We'll go for the Run Suite field.
        var menuID = "2147483637";
        var menuResponse = client.getMenu(projectID, menuID);
        assertNotNull(menuResponse, "Menu response should not be null");
        assertEquals(menuID, menuResponse.getId().toString(), "Menu IDs should match");
    }

    @Test
    void getVersions() {
        var client = new Client(sharedConnInfo);
        VersionInfo versionInfo = client.getVersions();

        assertNotNull(versionInfo, "Returned versionInfo shouldn't be null");
        assertFalse(versionInfo.getRESTAPIServer().isEmpty(), "REST API Server version shouldn't be empty.");
        assertFalse(versionInfo.getHALMServer().isEmpty(), "HALM Server version shouldn't be empty.");
        assertFalse(versionInfo.getHALMCommsLibrary().isEmpty(), "Comms Library version shouldn't be empty.");
    }

    @Test
    void getAuthToken() {
        var client = new Client(sharedConnInfo);

        var projects = client.getProjects();
        assertTrue(projects.getProjects().size() > 0, "Expected at least one project");

        AuthInfoToken token = client.getAuthToken(projects.getProjects().get(0).getUuid());
        assertNotNull(token, "AuthInfo token was null");
        assertFalse(token.isExpired(), "Token was expired");
    }

    @Test
    void getAutomationSuites() {
        var client = new Client(sharedConnInfo);
        var projects = client.getProjects();
        assertTrue(projects.getProjects().size() > 0, "Expected at least one project");

        var projectID = projects.getProjects().get(0).getUuid();

        AuthInfoToken token = client.getAuthToken(projectID);
        assertNotNull(token, "AuthInfo token was null");
        assertFalse(token.isExpired(), "Token was expired");

        // Now... lets get an AutomationSuite
        var suites = client.getAutomationSuites(projectID);
        assertNotNull(suites, "Suites should not be null");
    }

    @Test
    void getServerCertStatus() {
        var connInfo = new ConnectionInfo(sharedConnInfo.url, "", "");
        var client = new Client(connInfo);
        var certStatus = client.getServerCertStatus();
        assertEquals(CertificateStatus.INVALID_DOWNLOADABLE, certStatus.getStatus(), "Unexpected certificate status.");
    }

    @Test
    void testErrorResponse_Credentials() {
        var connInfo = new ConnectionInfo(sharedConnInfo.url, "JonesMcCoy", "");
        connInfo.setPemCertContents(sharedConnInfo.getPemCertContents());
        var client = new Client(connInfo);

        var response = client.getProjects();
        assertTrue(response.isError(), "Request should have failed.");
        assertNotNull(response.getErrorResponse(), "Request should have returned a HALM Error Response.");
        assertEquals("The username or password you entered is not recognized. Enter a valid username and password.",
            response.getErrorResponse().getMessage(),
            "Error message was not as expected");
    }
}