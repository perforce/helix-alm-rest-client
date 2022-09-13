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

package com.perforce.halm.rest.responses;

import feign.FeignException;

/**
 * Abstract base class for any API responses, so we can capture API error responses.
 */
public abstract class AbstractAPIResponse {
    protected int statusCode = 0;
    protected String errorMessage;
    protected ErrorResponse errorResponse;

    /**
     * If the request was successful or not
     * @return See description
     */
    public boolean isSuccess() {
        return !this.isError();
    }

    /**
     * If the request encountered an error or not
     * @return See description
     */
    public boolean isError() {
        return this.statusCode > 0 || (this.errorMessage != null && !this.errorMessage.isEmpty()) || this.errorResponse != null;
    }

    /**
     * Handles the given exception generically for all response types.
     * @param exception The exception to handle
     * @param inErrorResponse Parsed HALM REST API error response.
     */
    public void handleFeignException(FeignException exception, ErrorResponse inErrorResponse) {
        if (inErrorResponse != null) {
            this.errorMessage = inErrorResponse.getMessage();
        } else {
            errorMessage = exception.getLocalizedMessage();
        }

        statusCode = exception.status();
        errorResponse = inErrorResponse;
    }

    /**
     * @return The API response HTTP status code.
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * If the response had a Helix ALM REST API Error response object, then this is the message from that. Otherwise
     * this defaults to being the error message from an exception. Finally, it is possible for someone to directly set
     * this via {@link #setErrorMessage(String)}, so it could be a customized error message.
     *
     * @return If this was initialized from an HTTP request exception, this returns the exception error message.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Allows caller to override or set the error message.
     *
     * @param errorMessage - Message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return If the Helix ALM REST API returned an error response, this contains that error response.
     */
    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }
}
