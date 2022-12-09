package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.parser.MyShellParser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class CopyCommand implements ShellCommand {
    public static final String NAME = "copy";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Command takes two arguments: source file name and destination file name (i.e. paths and names).",
            "If destination file exists prompts the user whether it should be overwritten.",
            "If the second argument is directory, it is assumed the user wants to copy the",
            "original file into this directory using the original file name.",
            "This command works only with files and does not work with directories."
    );
    public static final int BUFFER_SIZE = 4096;

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = MyShellParser.parseArgumentsSupportingQuotes(arguments);
        if (args.size() != 2) {
            try {
                env.writeln("Command copy must have exactly two arguments.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }
        Path source = Paths.get(args.get(0));
        Path destination = Paths.get(args.get(1));
        if (Files.isDirectory(destination)) {
            destination = Paths.get(args.get(1) + File.separator + source.getFileName());
        }

        if (source.equals(destination)) {
            try {
                env.writeln("Source and destination files are the same file. Aborting.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }

        if (Files.exists(destination)) {
            try {
                env.writeln("File " + destination + " already exists. Overwrite? (y/n)");
            } catch (ShellIOException e) {
                exit(1);
            }
            String answer = null;
            try {
                answer = env.readLine();
            } catch (ShellIOException e) {
                exit(1);
            }
            if (!answer.equals("y")) {
                return ShellStatus.CONTINUE;
            }
        }

        if (!Files.exists(source)) {
            try {
                env.writeln("File " + source + " does not exist.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }

        try (InputStream is = new BufferedInputStream(Files.newInputStream(source));
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            try {
                env.writeln("Error while copying files.");
            } catch (ShellIOException e1) {
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
