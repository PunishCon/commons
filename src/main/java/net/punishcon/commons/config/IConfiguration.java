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

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface IConfiguration {

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Short}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Short> getShort(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Integer}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Integer> getInteger(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Long}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Long> getLong(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Float}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Float> getFloat(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Double}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Double> getDouble(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link Boolean}, {@link Optional} can be empty
     */
    @NotNull
    Optional<Boolean> getBoolean(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link String}, {@link Optional} can be empty
     */
    @NotNull
    Optional<String> getString(@NotNull final String key);

    /**
     * Read a data type from the configuration
     *
     * @param key Identifier of data set
     * @return {@link Optional} data set of type {@link List<String>}, {@link Optional} can be empty
     */
    @NotNull
    List<String> getStringList(@NotNull final String key);

}
