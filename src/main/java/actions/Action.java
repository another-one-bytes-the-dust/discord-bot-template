package actions;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public interface Action {

    ActionResult perform(String[] args, MessageReceivedEvent event);

    String usage();
    String description();
    String name();
}
