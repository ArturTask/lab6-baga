package ru.itmo.lena.ex.commands;

import ru.itmo.lena.ex.exception.CommandExecutionException;
import ru.itmo.lena.ex.manager.CommandManager;
import ru.itmo.lena.ex.util.Console;

import java.util.Map;

/**
 * Команда для вывода списка доступных команд
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;
    private final Console console;

    public HelpCommand(CommandManager commandManager, Console console) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) throws CommandExecutionException {
        try {
            if (args.length > 0) {
                throw new CommandExecutionException("Команда не принимает аргументов");
            }

            Map<String, Command> commands = commandManager.getCommands();

            console.println("Доступные команды");
            commands.values().forEach(cmd ->
                    console.println(String.format("%-20s - %s", cmd.getName(), cmd.getDescription()))
            );

        } catch (Exception e) {
            throw new CommandExecutionException("Ошибка при выводе справки", e);
        }
    }
}
