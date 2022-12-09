package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.parser.MyShellParser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

public class LSCommand implements ShellCommand {
    public static final String NAME = "ls";
    public static final List<String> DESCRIPTION = List.of(
            "Command takes a single argument – directory – and writes a directory listing (not recursive)."
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
        File[] files = new File(pathString).listFiles();

        if (files == null) {
            try {
                env.writeln("Invalid path.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }

        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Path path = file.toPath();
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
            );

            BasicFileAttributes attributes = null;
            try {
                attributes = faView.readAttributes();
            } catch (IOException e) {
                exit(1);
            }
            FileTime fileTime = attributes.creationTime();
            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));


            sb.append(file.isDirectory() ? "d" : "-");
            sb.append(file.canRead() ? "r" : "-");
            sb.append(file.canWrite() ? "w" : "-");
            sb.append(file.canExecute() ? "x" : "-");
            sb.append(" ")
                    .append(String.format("%10", file.length()))
                    .append(" ")
                    .append(formattedDateTime)
                    .append(" ")
                    .append(file.getName())
                    .append(System.lineSeparator());
        }

        try {
            env.write(sb.toString());
        } catch (ShellIOException e) {
            exit(1);
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
