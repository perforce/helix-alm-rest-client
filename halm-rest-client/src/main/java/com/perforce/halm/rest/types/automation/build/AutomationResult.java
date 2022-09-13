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
 * Object defining an automation build result
 */
public class AutomationResult {
    private String name;
    private String uniqueName;
    private IDLabelPair status;
    private List<String> tags;
    private String device;
    private String manufacturer;
    private String model;
    private String os;
    private String osVersion;
    private String browser;
    private String browserVersion;
    private String startDate;
    private Number duration;
    private String externalURL;
    private String errorMessage;
    private List<NameValuePair> properties;

    /**
     * @return Name representing the test result
     */
    public String getName() { return name; }

    /**
     * @param name Name representing the test result
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return Unique name representing the test result
     */
    public String getUniqueName() { return uniqueName; }

    /**
     * @param uniqueName Unique name representing the test result
     */
    public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }

    /**
     * @return Representation of the test result status
     */
    public IDLabelPair getStatus() { return status; }

    /**
     * @param status Representation of the test result status
     */
    public void setStatus(IDLabelPair status) { this.status = status; }

    /**
     * @return List of tags associate with the test
     */
    public List<String> getTags() { return tags; }

    /**
     * @param tags List of tags associate with the test
     */
    public void setTags(List<String> tags) { this.tags = tags; }

    /**
     * @return Device used for the test
     */
    public String getDevice() { return device; }

    /**
     * @param device Device used for the test
     */
    public void setDevice(String device) { this.device = device; }

    /**
     * @return Manufacturer of the device used for the test
     */
    public String getManufacturer() { return manufacturer; }

    /**
     * @param manufacturer Manufacturer of the device used for the test
     */
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    /**
     * @return Model of the device used for the test
     */
    public String getModel() { return model; }

    /**
     * @param model Model of the device used for the test
     */
    public void setModel(String model) { this.model = model; }

    /**
     * @return Operating system used for the test
     */
    public String getOS() { return os; }

    /**
     * @param os Operating system used for the test
     */
    public void setOS(String os) { this.os = os; }

    /**
     * @return Version of the operating system used for the test
     */
    public String getOSVersion() { return osVersion; }

    /**
     * @param osVersion Version of the operating system used for the test
     */
    public void setOSVersion(String osVersion) { this.osVersion = osVersion; }

    /**
     * @return Browser used for the test
     */
    public String getBrowser() { return browser; }

    /**
     * @param browser Browser used for the test
     */
    public void setBrowser(String browser) { this.browser = browser; }

    /**
     * @return Version of the browser used for the test
     */
    public String getBrowserVersion() { return browserVersion; }

    /**
     * @param browserVersion Version of the browser used for the test
     */
    public void setBrowserVersion(String browserVersion) { this.browserVersion = browserVersion; }

    /**
     * @return Start date for the test
     */
    public String getStartDate() { return startDate; }

    /**
     * @param startDate Start date for the test
     */
    public void setStartDate(String startDate) { this.startDate = startDate; }

    /**
     * @return Test duration in milliseconds
     */
    public Number getDuration() { return duration; }

    /**
     * @param duration Test duration in milliseconds
     */
    public void setDuration(Number duration) { this.duration = duration; }

    /**
     * @return Report URL for the test result
     */
    public String getExternalURL() { return externalURL; }

    /**
     * @param externalURL Report URL for the test result
     */
    public void setExternalURL(String externalURL) { this.externalURL = externalURL; }

    /**
     * @return Error message associated with the test
     */
    public String getErrorMessage() { return errorMessage; }

    /**
     * @param errorMessage Error message associated with the test
     */
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    /**
     * @return List of properties for the result
     */
    public List<NameValuePair> getProperties() { return properties; }

    /**
     * @param property The property to add to our list of properties for the result
     */
    public void addProperty(NameValuePair property) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        this.properties.add(property);
    }

    /**
     * @param properties List of properties for the result
     */
    public void setProperties(List<NameValuePair> properties) { this.properties = properties; }
}
