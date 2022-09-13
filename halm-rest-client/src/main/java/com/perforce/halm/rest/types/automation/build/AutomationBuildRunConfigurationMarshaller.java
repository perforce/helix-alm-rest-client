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

import com.google.gson.*;
import com.perforce.halm.rest.types.automation.jenkins.*;

import java.lang.reflect.Type;

/**
 * Marshaller object for automation build run configurations
 */
public class AutomationBuildRunConfigurationMarshaller implements JsonSerializer<AutomationBuildRunConfiguration>, JsonDeserializer<AutomationBuildRunConfiguration> {
    private static final String TYPE_NAME = "type";

    /**
     * Serializes the specified run configuration based on the actual type of data stored.
     *
     * @param runConfiguration The run configuration object being serialized
     * @param type the actual type (fully genericized version) of the source object
     * @param context Any applicable context for the serialization operation
     * @return The JSON representation of the run configuration
     */
    @Override
    public JsonElement serialize(AutomationBuildRunConfiguration runConfiguration, Type type, JsonSerializationContext context) {
        String typeName = runConfiguration.getType();
        if (typeName.equals(AutomationBuildRunConfigurationJenkins.TYPE_VALUE)) {
            return context.serialize(runConfiguration, AutomationBuildRunConfigurationJenkins.class);
        } else {
            return context.serialize(runConfiguration, AutomationBuildRunConfiguration.class);
        }
    }

    /**
     * Parses the specified JSON into an automation build run configuration
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context Any applicable context for the deserialization operation
     * @return The automation build run configuration data
     * @throws JsonParseException If there is an exception thrown during JSON parsing
     */
    @Override
    public AutomationBuildRunConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeName = jsonObject.get(TYPE_NAME).getAsString();

        if (typeName.equals(AutomationBuildRunConfigurationJenkins.TYPE_VALUE)) {
            return context.deserialize(json, AutomationBuildRunConfigurationJenkins.class);
        } else {
            return context.deserialize(json, AutomationBuildRunConfiguration.class);
        }
    }
}
