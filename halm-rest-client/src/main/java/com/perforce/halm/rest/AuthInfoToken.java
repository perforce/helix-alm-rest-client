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

import java.time.Instant;

/**
 * Authorization information used to perform 'Bearer' based token authentication.
 */
public class AuthInfoToken implements IAuthInfo {
    private String tokenType = "Bearer";
    private Instant expiresOn = Instant.now();
    protected String accessToken;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthorizationHeader() {
        return this.tokenType + " " + this.accessToken;
    }

    /**
     * Checks if this token is expired.
     *
     * @return Returns true if this token is expired
     */
    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresOn);
    }

    /**
     * Gets the precise date and time when this token expires.
     *
     * @return Precise date and time when this token expires.
     */
    public Instant getExpiresOn() {
        return this.expiresOn;
    }
}
