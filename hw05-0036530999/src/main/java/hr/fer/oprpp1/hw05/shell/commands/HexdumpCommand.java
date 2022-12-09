package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.oprpp1.hw05.parser.MyShellParser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.System.exit;

public class HexdumpCommand implements ShellCommand {
    public static final String NAME = "hexdump";
    public static final List<String> DESCRIPTION = List.of(
            "Expects a single argument: file name and produces hex-output"
    );
    private static final int BUFFER_SIZE = 16; // might break if changed // TODO fix before exam!
    private static final int DUMP_WIDTH = 16;

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = MyShellParser.parseArgumentsSupportingQuotes(arguments);
        if (args.size() != 1) {
            try {
                env.writeln("Hexdump command expects a single argument: file name");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }

        Path source = Paths.get(args.get(0));
        if (!Files.isReadable(source)) {
            try {
                env.writeln("File " + source + " is not readable or does not exist.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        } // TODO promjeni parser da vraća String[], nepotrebno je List<String>
        try (InputStream is = new BufferedInputStream(Files.newInputStream(source))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int hexCounter = 0;
            StringBuilder sbLine = new StringBuilder();
            while ((bytesRead = is.read(buffer)) > 0) {
                sbLine.append(String.format("%08d", hexCounter)).append(':'); // TODO izvuci ovo u row len
                for (int i = 0; i < bytesRead; i++) {
                    if (i == 7) sbLine.append('|');
                    if (i != 7) sbLine.append(' ');
                    sbLine.append(String.format("%02X", buffer[i]));
                }

                if (bytesRead < DUMP_WIDTH) {
                    for (int i = bytesRead; i < DUMP_WIDTH; i++) {
                        if (i == 7) sbLine.append('|');
                        if (i != 7) sbLine.append(' ');
                        sbLine.append("  ");
                    }
                }

                sbLine.append(" | ");
                for (int i = 0; i < bytesRead; i++) {
                    if (buffer[i] < 32 || buffer[i] > 127) sbLine.append('.');
                    else sbLine.append((char) buffer[i]);
                }

                env.writeln(sbLine.toString());
                hexCounter += 10;
                sbLine = new StringBuilder();
            }
        } catch (IOException e) {
            try {
                env.writeln("Error while reading file " + source);
            } catch (ShellIOException e1) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
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
