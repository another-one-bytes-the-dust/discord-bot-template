package logs;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;


public class DiscordLogWriter extends AbstractLogWriter {

    private final TextChannel logChannel;

    private final List<String> pool = new ArrayList<>();
    private final int LOG_POOL_SIZE = 30;

    public DiscordLogWriter(TextChannel channel, boolean save) {
        super(save);
        logChannel = channel;
    }

    @Override
    public void writeLogs(String msg) {
        if (!saveLogs) return;

        pool.add(msg);

        if (pool.size() >= LOG_POOL_SIZE) {
            StringBuilder sb = new StringBuilder();

            for (String logMessage : pool) {
                sb.append(addDate(logMessage)).append('\n');
            }

            logChannel.sendMessage(sb.toString()).queue();
            pool.clear();
        }
    }
}
