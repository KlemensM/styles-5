package kmo.styles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class FilesReadExample {

    private static final String EXAMPLE_FILE_TXT = "src/main/resources/ExampleFile.txt";

    private FilesReadExample() {
    }

    public static void main(String[] args) {
        System.out.println("scanner");
        System.out.println(scanner(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("scanner2");
        System.out.println(scanner2(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("scanner3");
        System.out.println(scanner3(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("files");
        System.out.println(files(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("files2");
        System.out.println(files2(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("oldschool");
        System.out.println(oldschool(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
        System.out.println("oldschool2");
        System.out.println(oldschool2(EXAMPLE_FILE_TXT).orElseThrow());
        System.out.println();
    }

    public static Optional<String> scanner(final String path) {
        final var content = new StringBuilder();
        try (var scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
                content.append(System.lineSeparator());
            }
            return Optional.of(content.toString());
        } catch (final IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> scanner2(final String path) {
        try (final var scanner = new Scanner(new FileReader(path))) {
            scanner.useDelimiter("\\Z");
            return Optional.of(scanner.tokens().collect(Collectors.joining()));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> scanner3(final String path) {
        try (final var scanner = new Scanner(new FileReader(path))) {
            scanner.useDelimiter("\\Z");
            return Optional.of(scanner.next());
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> files(final String path) {
        try {
            return Optional.of(Files.readAllLines(Paths.get(path)).stream().reduce("",
                    (s, str) -> s.concat(str).concat(System.lineSeparator())));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> files2(final String path) {
        try {
            return Optional.of(Files.lines(Paths.get(path)).collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> oldschool(final String filename) {
        final var file = new File(filename);
        try (final var reader = new FileReader(file)) {
            final var chars = new char[(int) file.length()];
            reader.read(chars);
            return Optional.of(new String(chars));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> oldschool2(final String filename) {
        final var file = new File(filename);
        final var lineBuffer = new StringBuilder();
        try (final var fin = new FileInputStream(file); final var bin = new BufferedInputStream(fin)) {
            int character;
            while ((character = bin.read()) != -1) {
                lineBuffer.append((char) character);
            }
            return Optional.of(lineBuffer.toString());
        } catch (final IOException e) {
            return Optional.empty();
        }
    }

}
