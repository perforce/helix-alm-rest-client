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

import java.security.InvalidParameterException;

/**
 * Authorization information used to perform 'ApiKey' based authentication.
 */
public class AuthInfoAPIKey implements IAuthInfo {
    private final String id;
    private final String secret;

    /**
     * Constructor
     *
     * @param id API Key id
     * @param secret API Key secret
     */
    public AuthInfoAPIKey(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    /**
     * Constructor
     *
     * @param idAndSecret Combined API key id and secret. Values must be seperated by a ':'
     */
    public AuthInfoAPIKey(String idAndSecret) {
        if (!idAndSecret.contains(":")) {
            throw new InvalidParameterException("ApiKey ID and Secret must be seperated by a ':' character.");
        }

        String[] splitKey = idAndSecret.split(":", 2);
        this.id = splitKey[0];
        this.secret = splitKey[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthorizationHeader() {
        return String.format("APIKey %s:%s", this.id, this.secret);
    }

    /**
     * @return Returns the API Key ID
     */
    public String getId() {
        return id;
    }
}
