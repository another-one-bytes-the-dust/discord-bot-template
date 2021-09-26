package listeners.emoteListener;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;


abstract class ValidationNode {
    ValidationNode next;

    ValidationNode setNext(ValidationNode next) {
        this.next = next;
        return next;
    }

    abstract boolean validate(MessageReactionAddEvent event);
}
