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

import java.text.MessageFormat;
import java.util.Base64;

/**
 * Authorization information used to perform 'Basic' Username and Password based authentication
 */
public class AuthInfoBasic implements IAuthInfo {
    private final String username;
    private final String password;

    /**
     * Constructor
     *
     * @param username REST API User's username
     * @param password REsT API User's password
     */
    public AuthInfoBasic(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthorizationHeader() {
        String usernamePass = this.username + ":" + this.password;

        // NOSONAR_REASON: Every guide I can find indicates using 'getBytes' is the proper way of base64 encoding a string.
        String base64Encoded = Base64.getEncoder().encodeToString(usernamePass.getBytes()); //NOSONAR
        return MessageFormat.format("Basic {0}", base64Encoded);
    }

    /**
     * @return Returns the REST API User's username
     */
    public String getUsername() {
        return username;
    }
}
