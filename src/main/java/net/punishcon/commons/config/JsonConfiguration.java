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
import net.punishcon.commons.config.annotations.ConfigurationValue;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class JsonConfiguration extends AbstractJsonConfiguration implements IConfiguration {

    public JsonConfiguration() {
        loadConfiguration();
        initialFields();
    }

    private void initialFields() {
        for (final Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigurationValue.class)) {
                initialField(field);
            }
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void initialField(@NotNull final Field field) {
        final ConfigurationValue configurationValue = field.getAnnotation(ConfigurationValue.class);
        final String key = configurationValue.key();

        final Class<?> fieldClass = field.getType();
        field.setAccessible(true);

        try {
            if (Short.class.isAssignableFrom(fieldClass)) {
                field.set(this, getShort(key).get());
            } else if (Integer.class.isAssignableFrom(fieldClass)) {
                field.set(this, getInteger(key).get());
            } else if (Long.class.isAssignableFrom(fieldClass)) {
                field.set(this, getLong(key).get());
            } else if (Float.class.isAssignableFrom(fieldClass)) {
                field.set(this, getFloat(key).get());
            } else if (Double.class.isAssignableFrom(fieldClass)) {
                field.set(this, getDouble(key).get());
            } else if (Boolean.class.isAssignableFrom(fieldClass)) {
                field.set(this, getBoolean(key).get());
            } else if (String.class.isAssignableFrom(fieldClass)) {
                field.set(this, getString(key).get());
            } else if (List.class.isAssignableFrom(fieldClass)) {
                Class<?> genericType = (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                if (genericType == String.class) {
                    field.set(this, getStringList(key));
                }
            }
        } catch (NoSuchElementException e) {
            try {
                field.set(this, null);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final @NotNull Optional<Short> getShort(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsShort());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<Integer> getInteger(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsInt());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<Long> getLong(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsLong());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<Float> getFloat(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsFloat());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<Double> getDouble(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsDouble());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<Boolean> getBoolean(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            return Optional.of(jsonPrimitive.getAsBoolean());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull Optional<String> getString(@NotNull String key) {
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonPrimitive jsonPrimitive) {
            return Optional.of(jsonPrimitive.getAsString());
        }
        return Optional.empty();
    }

    @Override
    public final @NotNull List<String> getStringList(@NotNull String key) {
        final List<String> stringList = new ArrayList<>();
        final JsonElement jsonElement = getJsonElement(key);
        if (jsonElement instanceof final JsonArray jsonArray) {
            for (final JsonElement element : jsonArray) {
                stringList.add(element.getAsString());
            }
        }
        return stringList;
    }

}
