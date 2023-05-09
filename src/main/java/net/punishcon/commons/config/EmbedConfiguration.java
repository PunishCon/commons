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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.punishcon.commons.config.annotations.ConfigurationValue;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Setter
public class EmbedConfiguration extends AbstractJsonConfiguration {

    @Setter(AccessLevel.NONE)
    private EmbedBuilder embedBuilder = new EmbedBuilder();

    @ConfigurationValue(key = "author.iconUrl")
    private String authorIconUrl;

    @ConfigurationValue(key = "author.url")
    private String authorUrl;

    @ConfigurationValue(key = "author.text")
    private String authorText;

    @ConfigurationValue(key = "body.thumbnailUrl")
    private String bodyThumbnailUrl;

    @ConfigurationValue(key = "body.titleUrl")
    private String bodyTitleUrl;

    @ConfigurationValue(key = "body.title")
    private String bodyTitle;

    @ConfigurationValue(key = "body.description")
    private String bodyDescription;

    @ConfigurationValue(key = "body.imageUrl")
    private String bodyImageUrl;

    @ConfigurationValue(key = "footer.iconUrl")
    private String footerIconUrl;

    @ConfigurationValue(key = "footer.text")
    private String footerText;

    @ConfigurationValue(key = "footer.timestamp")
    private String footerTimestamp;

    @ConfigurationValue(key = "hexColor")
    private String hexColor;

    public void addField(@NotNull final String name, @NotNull final String value, final boolean inline) {
        this.embedBuilder.addField(name, value, inline);
    }

    public EmbedConfiguration() {
        loadConfiguration();
        processConfiguration();
    }

    private void processConfiguration() {
        if (this.configuration != null) {
            final JsonObject configuration = this.configuration.getAsJsonObject();
            final JsonObject authorObject = configuration.getAsJsonObject("author");
            if (authorObject != null) {
                final JsonElement authorIconUrl = authorObject.get("iconUrl");
                if (authorIconUrl != null) {
                    setAuthorIconUrl(authorIconUrl.getAsString());
                }
                final JsonElement authorUrl = authorObject.get("url");
                if (authorUrl != null) {
                    setAuthorUrl(authorUrl.getAsString());
                }
                final JsonElement authorText = authorObject.get("text");
                if (authorText != null) {
                    setAuthorText(authorText.getAsString());
                }
            }
            final JsonObject bodyObject = configuration.getAsJsonObject("body");
            if (bodyObject != null) {
                final JsonElement bodyThumbnailUrl = bodyObject.get("thumbnailUrl");
                if (bodyThumbnailUrl != null) {
                    setBodyThumbnailUrl(bodyThumbnailUrl.getAsString());
                }
                final JsonElement bodyTitleUrl = bodyObject.get("titleUrl");
                if (bodyTitleUrl != null) {
                    setBodyTitleUrl(bodyTitleUrl.getAsString());
                }
                final JsonElement bodyTitle = bodyObject.get("title");
                if (bodyTitle != null) {
                    setBodyTitle(bodyTitle.getAsString());
                }
                final JsonElement bodyDescription = bodyObject.get("description");
                if (bodyDescription != null) {
                    setBodyDescription(bodyDescription.getAsString());
                }
                final JsonElement bodyImageUrl = bodyObject.get("imageUrl");
                if (bodyImageUrl != null) {
                    setBodyImageUrl(bodyImageUrl.getAsString());
                }
                final JsonArray bodyFieldsArray = bodyObject.getAsJsonArray("fields");
                if (bodyFieldsArray != null) {
                    bodyFieldsArray.forEach(element -> {
                        final JsonObject fieldObject = element.getAsJsonObject();
                        final JsonElement name = fieldObject.get("name");
                        final JsonElement value = fieldObject.get("value");
                        final JsonElement inline = fieldObject.get("inline");
                        addField(name.getAsString(), value.getAsString(), inline.getAsBoolean());
                    });
                }
            }
            final JsonObject footerObject = configuration.getAsJsonObject("footer");
            if (footerObject != null) {
                final JsonElement footerIconUrl = footerObject.get("iconUrl");
                if (footerIconUrl != null) {
                    setFooterIconUrl(footerIconUrl.getAsString());
                }
                final JsonElement footerText = footerObject.get("text");
                if (footerText != null) {
                    setFooterText(footerText.getAsString());
                }
                final JsonElement footerTimestamp = footerObject.get("timestamp");
                if (footerTimestamp != null) {
                    setFooterTimestamp(footerTimestamp.getAsString());
                }
            }
            final JsonElement hexColor = configuration.get("hexColor");
            if (hexColor != null) {
                setHexColor(hexColor.getAsString());
            }
        }
    }

    public @NotNull EmbedBuilder getEmbedBuilder() {
        this.embedBuilder.setAuthor(this.authorText, this.authorUrl, this.authorIconUrl);
        this.embedBuilder.setThumbnail(this.bodyThumbnailUrl);
        this.embedBuilder.setTitle(this.bodyTitle, this.bodyTitleUrl);
        this.embedBuilder.setDescription(this.bodyDescription);
        this.embedBuilder.setImage(this.bodyImageUrl);
        this.embedBuilder.setFooter(this.footerText, this.footerIconUrl);
        if (this.footerTimestamp != null) {
            if (this.footerTimestamp.equalsIgnoreCase("<now>")) {
                this.embedBuilder.setTimestamp(LocalDateTime.now());
            } else {
                final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                final TemporalAccessor temporalAccessor = dateTimeFormatter.parse(this.footerTimestamp);
                this.embedBuilder.setTimestamp(temporalAccessor);
            }
        }
        if (this.hexColor != null) {
            this.embedBuilder.setColor(Color.decode(this.hexColor));
        }
        return this.embedBuilder;
    }
}