package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

public class TreeCommand implements ShellCommand {
    public static final String NAME = "tree";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Command expects a single argument: directory name and prints a tree",
            "recursively listing all files and directories in the directory and its subdirectories."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return null;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
