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

package com.perforce.halm.rest.types;

/**
 * Object containing user information
 */
public class User implements ItemWithID {
    private Integer id;
    private String firstName;
    private String lastName;
    private String mi;
    private String username;

    /**
     * @return The user's identifier
     */
    @Override
    public Integer getID() { return this.id; }

    /**
     * @param id The user's identifier
     */
    @Override
    public void setID(Integer id) { this.id = id; }

    /**
     * @return The user's first name
     */
    public String getFirstName() { return firstName; }

    /**
     * @param firstName The user's first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * @return The user's last name
     */
    public String getLastName() { return lastName; }

    /**
     * @param lastName The user's last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * @return The user's middle initial
     */
    public String getMi() { return mi; }

    /**
     * @param mi The user's middle initial
     */
    public void setMi(String mi) { this.mi = mi; }

    /**
     * @return The user's username
     */
    public String getUsername() { return username; }

    /**
     * @param username The user's middle initial
     */
    public void setUsername(String username) { this.username = username; }
}
