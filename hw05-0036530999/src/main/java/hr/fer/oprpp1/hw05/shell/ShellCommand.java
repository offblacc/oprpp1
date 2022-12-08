package hr.fer.oprpp1.hw05.shell;

import java.util.List;

public interface ShellCommand {
    ShellStatus executeCommand(Environment env, String arguments);
    String getCommandName();
    List<String> getCommandDescription();
}
