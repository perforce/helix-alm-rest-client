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

import java.util.List;

/**
 * Version information for the Helix ALM Server, REST API Server, and the Helix ALM Communications Library that
 * facilitates communication between them.
 */
public class VersionInfo {
    private String RESTAPIServer;
    private String HALMCommsLibrary;
    private String HALMServer;
    private List<APIError> errors;

    /**
     * @return Version of the REST API Server. Format is Major.Minor.Maintenance.Build
     */
    public String getRESTAPIServer() {
        return RESTAPIServer;
    }

    /**
     * @return Helix ALM Communication Library version. Format is Major.Minor.Maintenance.Build. If the version cannot
     *         be determined, the value is set to &lt;unknown&gt;
     */
    public String getHALMCommsLibrary() {
        return HALMCommsLibrary;
    }

    /**
     * @return Helix ALM Server version. Format is Major.Minor.Maintenance.Build . If the version cannot
     *         be determined, the value is set to &lt;unknown&gt;
     */
    public String getHALMServer() {
        return HALMServer;
    }

    /**
     * @return Errors encountered when attempting to determine version for all components.
     */
    public List<APIError> getErrors() {
        return errors;
    }
}
