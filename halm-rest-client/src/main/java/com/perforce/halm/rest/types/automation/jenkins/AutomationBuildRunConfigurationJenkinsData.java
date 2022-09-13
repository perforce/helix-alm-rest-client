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

import java.util.ArrayList;
import java.util.List;

/**
 * Object containing Jenkins-specific automation build run configuration data
 */
public class AutomationBuildRunConfigurationJenkinsData {
    private List<JenkinsBuildParameter> buildParameters;

    /**
     * @return List of Jenkins build parameters for the run configuration
     */
    public List<JenkinsBuildParameter> getBuildParameters() {
        return buildParameters;
    }

    /**
     * @param parameter The Jenkins build parameter to add to the run configuration
     */
    public void addBuildParameter(JenkinsBuildParameter parameter) {
        if (this.buildParameters == null) {
            this.buildParameters = new ArrayList<>();
        }
        this.buildParameters.add(parameter);
    }

    /**
     * @param buildParameters List of Jenkins build parameters for the run configuration
     */
    public void setBuildParameters(List<JenkinsBuildParameter> buildParameters) {
        this.buildParameters = buildParameters;
    }
}
