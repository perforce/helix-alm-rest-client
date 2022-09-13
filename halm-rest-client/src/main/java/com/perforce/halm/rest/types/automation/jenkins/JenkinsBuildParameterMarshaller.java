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

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Marshaller object for Jenkins build parameter data
 */
public class JenkinsBuildParameterMarshaller implements JsonSerializer<JenkinsBuildParameter>, JsonDeserializer<JenkinsBuildParameter> {
    private static final String TYPE_NAME = "type";

    /**
     * Serializes the specified build parameter based on the actual type of data stored.
     *
     * @param parameter The build parameter object being serialized
     * @param type the actual type (fully genericized version) of the source object
     * @param context Any applicable context for the serialization operation
     * @return The JSON representation of the build parameter
     */
    @Override
    public JsonElement serialize(JenkinsBuildParameter parameter, Type type, JsonSerializationContext context) {
        String typeName = parameter.getType();
        switch (typeName) {
            case JenkinsBuildParameterText.TYPE_VALUE:
                return context.serialize(parameter, JenkinsBuildParameterText.class);
            case JenkinsBuildParameterPassword.TYPE_VALUE:
                return context.serialize(parameter, JenkinsBuildParameterPassword.class);
            case JenkinsBuildParameterIgnore.TYPE_VALUE:
                return context.serialize(parameter, JenkinsBuildParameterIgnore.class);
            default:
                return context.serialize(parameter, JenkinsBuildParameter.class);
        }
    }

    /**
     * Parses the specified JSON into a Jenkins build parameter
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context Any applicable context for the deserialization operation
     * @return The Jenkins build parameter
     * @throws JsonParseException If there is an exception thrown during JSON parsing
     */
    @Override
    public JenkinsBuildParameter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeName = jsonObject.get(TYPE_NAME).getAsString();

        switch (typeName) {
            case JenkinsBuildParameterText.TYPE_VALUE:
                return context.deserialize(json, JenkinsBuildParameterText.class);
            case JenkinsBuildParameterPassword.TYPE_VALUE:
                return context.deserialize(json, JenkinsBuildParameterPassword.class);
            case JenkinsBuildParameterIgnore.TYPE_VALUE:
                return context.deserialize(json, JenkinsBuildParameterIgnore.class);
            default:
                return context.deserialize(json, JenkinsBuildParameter.class);
        }
    }
}
