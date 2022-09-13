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

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Helix ALM Server connection information
 */
public class ConnectionInfo {
    protected String url;

    protected IAuthInfo authInfo;

    @Nullable
    protected List<String> pemCerts;

    /**
     * Constructor
     *
     * @param apiURL - API Root address (ex: https://localhost:8443 )
     * @param authInfo - Authorization information for the URL
     */
    public ConnectionInfo(String apiURL, IAuthInfo authInfo) {
        this(apiURL, authInfo, null);
    }

    /**
     * Constructor
     *
     * @param apiURL - API Root address (ex: https://localhost:8443 )
     * @param authInfo - Authorization information for the URL
     * @param pemCerts - List of certificates to consider 'valid'
     */
    public ConnectionInfo(String apiURL, IAuthInfo authInfo, @Nullable List<String> pemCerts) {
        this.url = apiURL;
        this.authInfo = authInfo;
        this.pemCerts = pemCerts;
    }

    /**
     * Constructor
     *
     * @param apiURL - API Root address (ex: https://localhost:8443 )
     * @param username - REST API username
     * @param password - REST API password
     */
    public ConnectionInfo(String apiURL, String username, String password) {
        this(apiURL, new AuthInfoBasic(username, password));
    }

    /**
     * Constructor
     *
     * @param apiURL - API Root address (ex: https://localhost:8443 )
     * @param username - REST API username
     * @param password - REST API password
     * @param pemCerts - List of certificates to consider 'valid'
     */
    public ConnectionInfo(String apiURL, String username, String password, List<String> pemCerts) {
        this(apiURL, new AuthInfoBasic(username, password), pemCerts);
    }

    /**
     * Helix ALM REST API URL. Ex: https://localhost:8443
     *
     * @return Helix ALM REST API url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the Helix AL MREST API URL. Ex: https://localhost:8443
     *
     * @param url URL to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Get the authentication info
     */
    public IAuthInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * @param authInfo Authentication info
     */
    public void setAuthInfo(IAuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    /**
     * @return SSL certificates encoded in the PEM format.
     */
    public List<String> getPemCertContents() {
        return this.pemCerts;
    }

    /**
     * @param pemCerts - SSL Certificate
     */
    public void setPemCertContents(List<String> pemCerts) { this.pemCerts = pemCerts; }
}
