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
 * Object containing Jenkins-specific automation suite run configuration data
 */
public class AutomationSuiteRunConfigurationJenkinsData {
    private String projectName;
    private String remoteAuthenticationToken;
    private List<JenkinsBuildParameter> defaultBuildParameters;

    /**
     * @return The Jenkins project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName The Jenkins project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return Remote authentication token to use
     */
    public String getRemoteAuthenticationToken() {
        return remoteAuthenticationToken;
    }

    /**
     * @param remoteAuthenticationToken Remote authentication token to use
     */
    public void setRemoteAuthenticationToken(String remoteAuthenticationToken) {
        this.remoteAuthenticationToken = remoteAuthenticationToken;
    }

    /**
     * @return List of default Jenkins build parameters for the run configuration
     */
    public List<JenkinsBuildParameter> getDefaultBuildParameters() {
        return defaultBuildParameters;
    }

    /**
     * @param parameter The Jenkins build parameter to add to the run configuration
     */
    public void addDefaultBuildParameter(JenkinsBuildParameter parameter) {
        if (this.defaultBuildParameters == null) {
            this.defaultBuildParameters = new ArrayList<>();
        }
        this.defaultBuildParameters.add(parameter);
    }
    /**
     * @param defaultBuildParameters List of default Jenkins build parameters for the run configuration
     */
    public void setDefaultBuildParameters(List<JenkinsBuildParameter> defaultBuildParameters) {
        this.defaultBuildParameters = defaultBuildParameters;
    }
}
