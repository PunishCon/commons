/*
 * Copyright 2023 PunishCon.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.punishcon.commons.config;

import net.punishcon.commons.config.annotations.ConfigurationOptions;
import net.punishcon.commons.config.annotations.ConfigurationValue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test for configurations with type JSON
 */
@ConfigurationOptions(relativeFilePath = "config.json")
public class JsonConfigurationTest extends JsonConfiguration {

    @ConfigurationValue(key = "test")
    public String test;

    @ConfigurationValue(key = "array")
    public List<String> array;

    @ConfigurationValue(key = "object.string")
    public String objectString;

    @ConfigurationValue(key = "object.int")
    public int objectInt;

    public static void main(String[] args) {
        new JsonConfigurationTest();
    }

    public JsonConfigurationTest() {
        final Logger logger = Logger.getGlobal();
        logger.log(Level.INFO, "--- DEBUGGING JsonTestConfiguration ---");
        logger.log(Level.INFO, "field test | value: " + this.test);
        logger.log(Level.INFO, "field array | values: " + String.join(", ", this.array));
        logger.log(Level.INFO, "field objectString | value: " + this.objectString);
        logger.log(Level.INFO, "field objectInt | value: " + this.objectInt);
        logger.log(Level.INFO, "--- DONE ---");
    }
}
