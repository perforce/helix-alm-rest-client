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

package com.perforce.halm.rest.types.automation.jenkins;

/**
 * Use the 'text' type Jenkins parameters for all non-sensitive data. All Jenkins parameters are ultimately sent
 * to the Jenkins server as text. This is because of how the Jenkins API functions.
 * For boolean parameters supply the text 'true' or 'false'. For choice parameters, use the text of the choice value.
 * For a field like 'User Credentials' you can supply the 'name' of the credentials.
 */
public class JenkinsBuildParameterText extends JenkinsBuildParameter {
    private String text;

    public static final String TYPE_VALUE = "text";
    public JenkinsBuildParameterText() {
        super(TYPE_VALUE);
    }

    /**
     * @return Value for a text parameter to send to Jenkins when starting a build
     */
    public String getText() {
        return text;
    }

    /**
     * @param text Value for a text parameter to send to Jenkins when starting a build
     */
    public void setText(String text) {
        this.text = text;
    }
}
