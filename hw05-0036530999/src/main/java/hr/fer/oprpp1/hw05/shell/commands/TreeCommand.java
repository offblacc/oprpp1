package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.parser.MyShellParser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class TreeCommand implements ShellCommand {
    public static final String NAME = "tree";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Command expects a single argument: directory name and prints a tree",
            "recursively listing all files and directories in the directory and its subdirectories."
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
        SimpleFileVisitor<Path> visitor = new MyFileSystemVisitor(env);
        try {
            env.writeln(pathString);
        } catch (ShellIOException e) {
            exit(1);
        }

        try {
            Files.walkFileTree(Path.of(pathString), visitor);
        } catch (IOException e) {
            try {
                env.writeln("Error while visiting file.");
            } catch (ShellIOException ex) {
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

    class MyFileSystemVisitor extends SimpleFileVisitor<Path> {
        private int level = 0;
        private Environment env;

        public MyFileSystemVisitor(Environment env) {
            this.env = env;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            try {
                env.write(" ".repeat(2 * level));
                env.writeln(dir.getFileName().toString());
            } catch (ShellIOException e) {
                exit(1);
            }

            level++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                env.write(" ".repeat(2 * level));
                env.writeln(file.getFileName().toString());
            } catch (ShellIOException e) {
                exit(1);
            }
            return FileVisitResult.CONTINUE;
        }
    }

}
