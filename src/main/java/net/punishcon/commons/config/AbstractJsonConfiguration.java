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

import com.google.gson.*;
import net.punishcon.commons.config.annotations.ConfigurationOptions;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;

public abstract class AbstractJsonConfiguration {

    private static final HashMap<String, JsonElement> fileCache = new HashMap<>();

    protected JsonElement configuration;

    public AbstractJsonConfiguration() {
        loadConfiguration();
    }

    protected void loadConfiguration() {
        if (this.getClass().isAnnotationPresent(ConfigurationOptions.class)) {
            final ConfigurationOptions options = this.getClass().getAnnotation(ConfigurationOptions.class);
            final String relativeFilePath = options.relativeFilePath();
            if (AbstractJsonConfiguration.fileCache.containsKey(relativeFilePath)) {
                this.configuration = AbstractJsonConfiguration.fileCache.get(relativeFilePath);
            } else {
                final String directory = options.directory();
                final File file = new File(directory + relativeFilePath);
                if (!file.exists()) {
                    try {
                        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("configurations/" + relativeFilePath);
                        if (inputStream != null) {
                            FileUtils.copyInputStreamToFile(inputStream, file);
                        } else {
                            throw new FileNotFoundException("Configuration file \"configurations/" + relativeFilePath + "\" cannot be found.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    this.configuration = AbstractJsonConfiguration.fileCache.put(relativeFilePath, JsonParser.parseReader(new FileReader(file)));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected final @NotNull JsonElement getJsonElement(@NotNull final String path) {
        final String[] parts = path.split("[.\\[\\]]");
        JsonElement result = this.configuration;
        for (@NotNull String key : parts) {
            key = key.trim();
            if (key.isEmpty()) {
                continue;
            }
            if (result == null) {
                result = JsonNull.INSTANCE;
                break;
            }
            if (result.isJsonObject()) {
                result = ((JsonObject) result).get(key);
            }  else if (result.isJsonArray()) {
                int ix = Integer.parseInt(key) - 1;
                result = ((JsonArray) result).get(ix);
            }
            else break;
        }
        return result;
    }

}