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

package com.perforce.halm.rest.types.automation.build;

import com.perforce.halm.rest.types.IDLabelPair;
import com.perforce.halm.rest.types.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Object defining an automation build
 */
public class AutomationBuild {
    private String number;
    private String description;
    private String branch;
    private IDLabelPair testRunSet;
    private AutomationBuildRunConfiguration runConfigurationInfo;
    private String externalURL;
    private String startDate;
    private Number duration;
    private String sourceOverride;
    private String pendingRunID;
    private List<NameValuePair> properties;
    private List<AutomationResult> results;

    /**
     * @return Build number identifier
     */
    public String getNumber() { return number; }

    /**
     * @param number Build number identifier
     */
    public void setNumber(String number) { this.number = number; }

    /**
     * @return Build description
     */
    public String getDescription() { return description; }

    /**
     * @param description Build description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * @return Build branch
     */
    public String getBranch() { return branch; }

    /**
     * @param branch Build branch
     */
    public void setBranch(String branch) { this.branch = branch; }

    /**
     * @return Helix ALM test run set associated with the build
     */
    public IDLabelPair getTestRunSet() { return testRunSet; }

    /**
     * @param testRunSet Helix ALM test run set associated with the build
     */
    public void setTestRunSet(IDLabelPair testRunSet) { this.testRunSet = testRunSet; }

    /**
     * @return Automation platform specific run configuration information
     */
    public AutomationBuildRunConfiguration getRunConfigurationInfo() { return runConfigurationInfo; }

    /**
     * @param runConfigurationInfo Automation platform specific run configuration information
     */
    public void setRunConfigurationInfo(AutomationBuildRunConfiguration runConfigurationInfo) { this.runConfigurationInfo = runConfigurationInfo; }

    /**
     * @return External URL for viewing build results
     */
    public String getExternalURL() { return externalURL; }

    /**
     * @param externalURL External URL for viewing build results
     */
    public void setExternalURL(String externalURL) { this.externalURL = externalURL; }

    /**
     * @return Start date for the build
     */
    public String getStartDate() { return startDate; }

    /**
     * @param startDate Start date for the build
     */
    public void setStartDate(String startDate) { this.startDate = startDate; }

    /**
     * @return Build duration in milliseconds
     */
    public Number getDuration() { return duration; }

    /**
     * @param duration Build duration in milliseconds
     */
    public void setDuration(Number duration) { this.duration = duration; }

    /**
     * @return The value to send to the REST API as this build's source
     */
    public String getSourceOverride() { return sourceOverride; }

    /**
     * @param source The value to send to the REST API as this build's source
     */
    public void setSourceOverride(String source) { this.sourceOverride = source; }

    /**
     * @return The identifier for the pending run, in the event the run was started from Helix ALM
     */
    public String getPendingRunID() { return pendingRunID; }

    /**
     * @param pendingRunID The identifier for the pending run, in the event the run was started from Helix ALM
     */
    public void setPendingRunID(String pendingRunID) { this.pendingRunID = pendingRunID; }

    /**
     * @return List of build properties used for the build
     */
    public List<NameValuePair> getProperties() { return properties; }

    /**
     * @param property The build property to add to our list of build properties used for the build
     */
    public void addProperty(NameValuePair property) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        this.properties.add(property);
    }

    /**
     * @param properties List of build properties used for the build
     */
    public void setProperties(List<NameValuePair> properties) { this.properties = properties; }

    /**
     * @return List of build results
     */
    public List<AutomationResult> getResults() { return results; }

    /**
     * @param result The build result to add to our list of build results
     */
    public void addResult(AutomationResult result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        this.results.add(result);
    }

    /**
     * @param results List of build results
     */
    public void setResults(List<AutomationResult> results) { this.results = results; }
}
