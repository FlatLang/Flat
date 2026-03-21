package flat.util;

import flat.Flat;

import java.io.*;
import java.util.function.Consumer;

/**
 * Utility methods for File operations, such as: reading, writing, and manipulating file extensions.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 19, 2014 at 1:51:23 AM
 * @version v0.2 Apr 5, 2014 at 3:32:39 PM
 */
public class FileUtils {
    /**
     * Read the contents of a File instance and return the contents in a String instance.
     *
     * @param file The File to read from.
     * @return The contents of the File.
     * @throws IOException Thrown if there was an error reading from the file.
     */
    public static String readFile(File file) throws IOException {
        StringBuilder source = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();

        while (line != null) {
            source.append(line);

            line = reader.readLine();

            if (line != null) {
                source.append('\n');
            }
        }

        reader.close();

        return source.toString();
    }

    /**
     * Write the given 'source' text to a new file created in the current working directory.
     *
     * @param fileName The name of the file to write the contents in.
     * @param source The contents to write into the new file.
     * @return The newly written File instance.
     * @throws IOException Thrown if there is an error writing the file.
     */
    public static File writeFile(String fileName, String source) throws IOException {
        File parent = new File(System.getProperty("user.dir"));

        return writeFile(fileName, parent, source);
    }

    /**
     * Write the given 'source' text to the file at the specified location.
     *
     * @param fileName The name of the file to write the contents in.
     * @param parentDir The directory to write the file inside of.
     * @param source The contents to write into the new file.
     * @return The newly written File instance.
     * @throws IOException Thrown if there is an error writing the file.
     */
    public static File writeFile(String fileName, File parentDir, String source)
        throws IOException {
        File file = new File(parentDir, fileName);

        PrintWriter writer = getFileWriter(file);

        writer.print(source);

        writer.close();

        return file;
    }

    public static PrintWriter getFileWriter(File file) throws IOException {
        return getFileWriter(file, false);
    }

    public static PrintWriter getFileWriter(File file, boolean append) throws IOException {
        return new PrintWriter(new FileWriter(file, append));
    }

    public static PrintWriter getFileWriter(String fileName, File parentDir) throws IOException {
        return getFileWriter(new File(parentDir, fileName));
    }

    public static String getFileExtension(String filename) {
        int lastIndex = 0;

        if ((lastIndex = filename.lastIndexOf('.')) >= 0) {
            return filename.substring(lastIndex + 1);
        }

        return null;
    }

    /**
     * Remove the extension from a file name.<br>
     * <br>
     * For example: An input of "file.txt" would return "file"
     *
     * @param filename The name of the file to remove the extension of.
     * @return The filename without an extension.
     */
    public static String removeFileExtension(String filename) {
        int lastIndex = 0;

        if ((lastIndex = filename.lastIndexOf('.')) < 0) {
            return filename;
        }

        return filename.substring(0, lastIndex);
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }

        return directory.delete();
    }

    /**
     * Format a path according to how the specified OS needs it.
     *
     * @param path The path to format for the OS standards.
     * @return The formatted path.
     */
    public static String formatPath(String path) {
        path = path.replace("\\", "/");

        path = formAbsolutePath(path);

        if (Flat.OS == Flat.WINDOWS) {
            path = StringUtils.removeSurroundingQuotes(path);

            return '"' + path + '"';
        } else {
            return StringUtils.escapeSpaces(path);
        }
    }

    /**
     * Remove all of the relative syntax from the given path.<br>
     * <br>
     * For example: Passing a path of "C:/folder/../dir1/dir2" would return a path of "C:/dir1/dir2"
     *
     * @param path The path to remove the relative syntax from.
     * @return The newly formatted path.
     */
    public static String formAbsolutePath(String path) {
        path = path.replace('\\', '/');

        StringBuilder absolute = new StringBuilder(path);

        int index = absolute.indexOf("..");

        while (index >= 0) {
            int index2 = absolute.lastIndexOf("/", index - 2);

            absolute.delete(index2, index + 2);

            index = absolute.indexOf("..");
        }

        return absolute.toString();
    }

    /**
     * Find the location that the given filename is located within the compilation's library
     * directories.
     *
     * @param filename The name of the file to search for.
     * @return The location of the file with the given filename. If the location was not found, null
     *         is returned.
     */
    public static String findFileLocation(String filename, String[] directories) {
        for (String dir : directories) {
            String location = StringUtils.removeSurroundingQuotes(dir) + "/" + filename;

            File f = new File(location);

            if (f.isFile()) {
                return location;
            }
        }

        return null;
    }

    public static void clearFile(File file) {
        try {
            new PrintWriter(file).close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean writeIfDifferent(File file, String source) throws IOException {
        return writeIfDifferent(file, writer -> {
            writer.write(source);
        }, false);
    }

    public static boolean writeIfDifferent(File file, Consumer<PrintWriter> write)
        throws IOException {
        return writeIfDifferent(file, write, false);
    }

    public static boolean writeIfDifferent(File file, Consumer<PrintWriter> write, boolean force)
        throws IOException {
        long lastModified = file.lastModified();

        String previous = null;

        if (file.isFile()) {
            previous = readFile(file);
        }

        final StringBuilder builder = new StringBuilder();

        // final String lineSeparator = java.security.AccessController.doPrivileged(
        // new sun.security.action.GetPropertyAction("line.separator"));

        PrintWriter writer = new PrintWriter(new FileWriter(file)) {
            @Override
            public void print(char c) {
                builder.append(c);

                super.print(c);
            }

            @Override
            public void print(char[] s) {
                builder.append(s);

                super.print(s);
            }

            @Override
            public PrintWriter append(char c) {
                builder.append(c);

                return super.append(c);
            }

            @Override
            public void write(String s) {
                builder.append(s);

                super.write(s);
            }

            @Override
            public void println() {
                builder.append('\n');

                write('\n');
            }

            @Override
            public void println(char x) {
                builder.append(x);

                write(x);
                println();
            }

            @Override
            public void println(char[] x) {
                builder.append(x);

                write(x);
                println();
            }
        };

        write.accept(writer);

        writer.close();

        return force || checkModified(file, lastModified, previous, builder.toString());
    }

    public static boolean checkModified(File file, long lastModified, String previous,
        String current) {
        if (previous != null && current.trim().equals(previous.trim())) {
            file.setLastModified(lastModified);

            return false;
        }

        return true;
    }

    public static class FileBuilder extends Writer {
        final StringBuilder builder = new StringBuilder();

        public void print(char c) {
            builder.append(c);
        }

        public void print(char[] s) {
            builder.append(s);
        }

        public FileBuilder append(char c) {
            builder.append(c);

            return this;
        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void close() throws IOException {

        }

        public FileBuilder append(String c) {
            builder.append(c);

            return this;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {

        }

        public void write(String s) {
            builder.append(s);
        }

        public void print(String s) {
            builder.append(s);
        }

        public void print(Object s) {
            print(s.toString());
        }

        public void println() {
            builder.append('\n');
        }

        public void println(char x) {
            builder.append(x);
        }

        public void println(char[] x) {
            builder.append(x);
        }

        public String toString() {
            return builder.toString();
        }
    }
}

