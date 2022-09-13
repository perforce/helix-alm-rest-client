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

package com.perforce.halm.rest.types;

import com.google.gson.*;
import com.perforce.halm.rest.AuthInfoToken;
import com.perforce.halm.rest.Client;
import com.perforce.halm.rest.ConnectionInfo;
import com.perforce.halm.rest.responses.ProjectListResponse;
import com.perforce.halm.rest.types.automation.suite.AutomationSuitesContainer;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class TypeDeserializationTest {
    private static final String RESOURCE_DIR = "src/test/resources/exampleJson";

    private static final GsonBuilderClient gsonBuilder = new GsonBuilderClient();

    private <T> T deserializeFile(final String filename, Class<T> resultType) throws IOException {
        var jsonFile = new File(RESOURCE_DIR, filename);
        var jsonData = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);

        var gson = gsonBuilder.buildGson();
        return gson.fromJson(jsonData, resultType);
    }

    @Test
    void deserializeProjects() throws IOException {
        var projects = deserializeFile("projects.json", ProjectListResponse.class);
        assertEquals(3, projects.getProjects().size(), "Should have 3 projects loaded.");
    }

    @Test
    void deserializeVersions() throws IOException {
        var versions = deserializeFile("versions.json", VersionInfo.class);
        assertEquals("<unknown>", versions.getRESTAPIServer(), "REST API version not as expected.");
        assertEquals("2022.1.0.0", versions.getHALMServer(), "HALM Server version not as expected.");
    }

    @Test
    void deserializeAuthToken() throws IOException {
        var authToken = deserializeFile("authToken.json", AuthInfoToken.class);

        assertTrue(authToken.isExpired(), "Token should be expired");
    }

    @Test
    void deserializeAutomationSuite() throws IOException {
        var automationSuites = deserializeFile("automationSuites.json", AutomationSuitesContainer.class);

        assertEquals(1, automationSuites.getAutomationSuitesData().size(), "Should have 1 suite.");
    }

    /**
     * This extension exists so that we can get access to the same Gson deserialiation that the client
     * is using. This ensures this unit test is deserializing files the same way that the REST client
     */
    private static class GsonBuilderClient extends Client {
        public GsonBuilderClient() {
            super(new ConnectionInfo("https://example.com", "", ""));
        }

        @Override
        public Gson buildGson() {
            return super.buildGson();
        }
    }
}
