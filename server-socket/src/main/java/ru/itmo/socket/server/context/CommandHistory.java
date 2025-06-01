package ru.itmo.socket.server.context;

import ru.itmo.socket.common.command.AppCommand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommandHistory {
    private static final int MAX_HISTORY_SIZE = 7;
    private static final Queue<AppCommand> history = new LinkedList<>();

    // unused
    private CommandHistory() {
    }

    public static void addCommand(AppCommand command) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.poll();
        }
        history.add(command);
    }

    public static List<AppCommand> getHistory() {
        return new ArrayList<>(history);
    }

    public static int getMaxHistorySize() {
        return MAX_HISTORY_SIZE;
    }
}
