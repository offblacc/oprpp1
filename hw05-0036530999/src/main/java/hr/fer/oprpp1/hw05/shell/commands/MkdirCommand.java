package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.parser.MyShellParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class MkdirCommand  implements ShellCommand {
    public static final String NAME = "mkdir";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Command takes a single argument: directory name,",
            "and creates the appropriate directory structure."
    );
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = MyShellParser.parseArgumentsSupportingQuotes(arguments);
        if (args.size() != 1) {
            try {
                env.writeln("Expected 1 argument.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }
        String pathString = args.get(0);
        File file = new File(pathString);
        if (file.mkdirs()) {
            try {
                env.writeln("Directory created.");
            } catch (ShellIOException e) {
                exit(1);
            }
        } else {
            try {
                env.writeln("Directory not created. Something went wrong.");
            } catch (ShellIOException e) {
                exit(1);
            }
        }
        return ShellStatus.CONTINUE;
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
