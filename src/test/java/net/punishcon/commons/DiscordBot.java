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

package net.punishcon.commons;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.punishcon.commons.config.MessageEmbedConfiguration;
import net.punishcon.commons.config.SettingsConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Main class for running a discord application
 */
public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) {
        new DiscordBot();
    }

    public DiscordBot() {
        final SettingsConfiguration settingsConfiguration = new SettingsConfiguration();
        final ShardManager shardManager = DefaultShardManagerBuilder
                .createDefault(settingsConfiguration.discordBotToken)
                .build();
        shardManager.addEventListener(this);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild() || event.getMember().getUser().isBot())
            return;
        final EmbedBuilder embedBuilder = MessageEmbedConfiguration.instance == null ? new MessageEmbedConfiguration().getEmbedBuilder() : MessageEmbedConfiguration.instance.getEmbedBuilder();
        event.getChannel()
                .sendMessage("")
                .addEmbeds(embedBuilder.build())
                .queue(message -> message.delete().queueAfter(30, TimeUnit.SECONDS));
    }
}
