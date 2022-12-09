package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;
import static java.lang.System.exit;

public class Crypto {
    private static final int BUFFER_SIZE = 4096;
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        if (args[0].equals("checksha")) {
            checkSHA(args[1]);
        } else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
            try {
                encryptDecrypt(args[0], args[1], args[2]);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                     InvalidKeyException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Invalid first argument");
        }
    }

    /**
     * Checks whether the SHA-256 digest of file matches the expected digest.
     * If result is printed to the standard output.
     *
     * @param filename - name of the file
     */
    private static void checkSHA(String filename) {
        System.out.printf("Please provide expected sha-256 digest for %s:%n> ", filename);
        Scanner sc = new Scanner(System.in);
        String expectedDigest = sc.nextLine();
        String actualDigest = null;
        sc.close();

        try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(filename)))) {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[4096];
            int numOfBytesRead;
            while ((numOfBytesRead = is.read(buffer)) > 0) {
                sha.update(buffer, 0, numOfBytesRead);
            }
            actualDigest = Util.bytetohex(sha.digest());
        } catch (IOException e) {
            System.out.println("Error: File not found");
            exit(0);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: SHA-256 algorithm not found in current environment");
            exit(0);
        }
        if (actualDigest.equals(expectedDigest)) {
            System.out.printf("Digesting completed. Digest of %s matches expected digest.%n", filename);
        } else {
            System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s%n", filename, actualDigest);
        }
    }

    private static void encryptDecrypt(String process, String sourceFilename, String destFilename) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        if (!(process.equals("encrypt") || process.equals("decrypt"))) {
            throw new IllegalArgumentException("Invalid argument: \"encrypt\" or \"decrypt\" expected, got " + process);
        }
        boolean encrypt = process.equals("encrypt");

        System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
        Scanner sc = new Scanner(System.in);
        String keyText = sc.nextLine();
        System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
        String ivText = sc.nextLine();
        sc.close();

        SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);


        try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(sourceFilename)));
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(destFilename)))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int numOfBytesRead;
            while ((numOfBytesRead = is.read(buffer)) > 0) {
                byte[] newBlock;
                newBlock = cipher.update(buffer, 0, numOfBytesRead);
                os.write(newBlock);
            }
            os.write(cipher.doFinal());
        } catch (IOException e) {
            System.out.println("IO error");
            System.out.println("Original message: " + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Error while finalizing encryption.");
            System.out.println(e.getMessage());
        }
    }
}
