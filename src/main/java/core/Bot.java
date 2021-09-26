package core;

import actions.ActionHandler;

import static core.utilities.DateTimeInfo.ZONE_ID;
import static core.utilities.DateTimeInfo.setLaunchDateTime;

import actions.ActionResult;
import listeners.emoteListener.EmoteListener;
import listeners.ActionListener;
import logs.DiscordLogWriter;
import logs.AbstractLogWriter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Properties;


public class Bot {

    private static JDA jda;

    private static ActionHandler actionHandler;
    private static AbstractLogWriter logWriter;
    
    public static AbstractLogWriter getLogWriter() { return logWriter; }
    
    public static ActionResult performAction(String[] args, MessageReceivedEvent event) {
        return actionHandler.performAction(args, event);
    }

    public static void main(String[] args) {

        actionHandler = new ActionHandler();

        try {
            Properties properties = loadProperties();
            String token = properties.getProperty("token");


            jda = JDABuilder
                    .createDefault(token)
                    .setActivity(Activity.listening("\uD83C\uDFA7"))
                    .addEventListeners(
                            new ActionListener(),
                            new EmoteListener())
                    .build()
                    .awaitReady();

            String guildId = properties.getProperty("guildId");
            String channelId = properties.getProperty("logChannelId");
            String saveLogs = properties.getProperty("saveLogs");

            Guild guild = Objects.requireNonNull(jda.getGuildById(guildId));
            TextChannel channel = guild.getTextChannelById(channelId);
            boolean save = Boolean.parseBoolean(saveLogs);

            logWriter = new DiscordLogWriter(channel, save);

        } catch (NullPointerException | LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ZonedDateTime zdt = Instant.now().atZone(ZONE_ID);
        setLaunchDateTime(zdt);
    }

    private static Properties loadProperties() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();

        try (InputStream stream = loader.getResourceAsStream("config.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}

