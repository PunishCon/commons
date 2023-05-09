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

/**
 * Test configuration for building an embed from a file
 */
@ConfigurationOptions(relativeFilePath = "embed/message.json")
public class MessageEmbedConfiguration extends EmbedConfiguration {

    public static MessageEmbedConfiguration instance;

    public MessageEmbedConfiguration() {
        MessageEmbedConfiguration.instance = this;
    }

}