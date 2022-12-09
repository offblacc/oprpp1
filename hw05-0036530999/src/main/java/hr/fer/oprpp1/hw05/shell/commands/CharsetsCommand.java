package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List; // TODO izvuci sve welcome stringove i 4096b u konstante

import static java.lang.System.exit;

public class CharsetsCommand implements ShellCommand {
    public static final String NAME = "charsets";
    public static final List<String> DESCRIPTION = Arrays.asList("lists names of supported charsets for your Java platform",
            "A single charset name is written per line.");
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        Charset.availableCharsets().forEach((k, v) -> {
            try {
                env.writeln(k);
            } catch (ShellIOException e) {
                exit(1);
            }
        });
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
