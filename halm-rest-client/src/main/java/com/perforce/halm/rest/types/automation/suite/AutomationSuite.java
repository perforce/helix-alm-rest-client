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

package com.perforce.halm.rest.types.automation.suite;

import com.perforce.halm.rest.types.ItemWithID;
import com.perforce.halm.rest.types.User;

import java.util.List;

/**
 * Object defining a Helix ALM automation suite
 */
public class AutomationSuite implements ItemWithID {
    private Integer id;
    private String name;
    private String self;
    private String description;
    private boolean active;
    private String scriptIDPrefix;
    private AutomationSuiteHistoryInfo modifiedInfo;
    private AutomationSuiteHistoryInfo createdInfo;
    private List<Integer> testCaseIDs;
    private List<User> owners;
    private AutomationSuiteRunConfiguration runConfiguration;

    /**
     * @return The automation suite's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The automation suite's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The self link for retrieving the automation suite from the Helix ALM REST API
     */
    public String getSelf() {
        return self;
    }

    /**
     * @param self The self link for retrieving the automation suite from the Helix ALM REST API
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * @return The automation suite's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The automation suite's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Whether the automation suite is active or not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active Whether the automation suite is active or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return The script ID prefix associated with the automation suite
     */
    public String getScriptIDPrefix() {
        return scriptIDPrefix;
    }

    /**
     * @param scriptIDPrefix The script ID prefix associated with the automation suite
     */
    public void setScriptIDPrefix(String scriptIDPrefix) {
        this.scriptIDPrefix = scriptIDPrefix;
    }

    /**
     * @return Historical information for the automation suite
     */
    public AutomationSuiteHistoryInfo getModifiedInfo() {
        return modifiedInfo;
    }

    /**
     * @param modifiedInfo Historical information for the automation suite
     */
    public void setModifiedInfo(AutomationSuiteHistoryInfo modifiedInfo) {
        this.modifiedInfo = modifiedInfo;
    }

    /**
     * @return Information about the creation of the automation suite
     */
    public AutomationSuiteHistoryInfo getCreatedInfo() {
        return createdInfo;
    }

    /**
     * @param createdInfo Information about the creation of the automation suite
     */
    public void setCreatedInfo(AutomationSuiteHistoryInfo createdInfo) {
        this.createdInfo = createdInfo;
    }

    /**
     * @return The entity IDs for the Helix ALM Test Cases associated with this automation suite
     */
    public List<Integer> getTestCaseIDs() {
        return testCaseIDs;
    }

    /**
     * @param testCaseIDs The entity IDs for the Helix ALM Test Cases associated with this automation suite
     */
    public void setTestCaseIDs(List<Integer> testCaseIDs) {
        this.testCaseIDs = testCaseIDs;
    }

    /**
     * @return The owners of the automation suite
     */
    public List<User> getOwners() {
        return owners;
    }

    /**
     * @param owners The owners of the automation suite
     */
    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    /**
     * @return The automation suite's run configuration
     */
    public AutomationSuiteRunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    /**
     * @param runConfiguration The automation suite's run configuration
     */
    public void setRunConfiguration(AutomationSuiteRunConfiguration runConfiguration) {
        this.runConfiguration = runConfiguration;
    }

    /**
     * @return The automation suite's identifier
     */
    @Override
    public Integer getID() {
        return this.id;
    }

    /**
     * @param id The automation suite's run configuration
     */
    @Override
    public void setID(Integer id) { this.id = id; }
}
