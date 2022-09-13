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

import com.perforce.halm.rest.types.administration.field.FieldStub;
import com.perforce.halm.rest.types.administration.field.FieldStubsContainer;
import com.perforce.halm.rest.types.administration.MenuItem;
import com.perforce.halm.rest.types.administration.MenuItemsContainer;

import java.util.List;

/**
 * Response object defining a dropdown menu and its items
 */
public class MenuResponse extends AbstractAPIResponse {
    private String name;
    private Integer id;
    private MenuItemsContainer items;
    private FieldStubsContainer fields;

    /**
     * @return Menu name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Menu ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Menu items
     */
    public List<MenuItem> getItems() { return items.getItems(); }

    /**
     * @return Field stubs associated with this menu
     */
    public List<FieldStub> getFields() { return fields.getFields(); }
}
