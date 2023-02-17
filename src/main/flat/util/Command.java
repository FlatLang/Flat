package flat.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that is used to issue a command to the Operating System.
 *
 * @version Nov 23, 2013 at 5:32:00 PM
 * @author Braden Steffaniak
 * @since Nov 23, 2013 at 5:32:00 PM
 */
public class Command {
    private File directory;

    private Thread commandThread, commandThread2;

    private String commands[];

    private ArrayList<CommandListener> listeners;

    public Command(String command, String directory) {
        this(command, new File(directory));
    }

    /**
     * Create a command with the specified Display instance, command,
     * and directory to issue the command in.
     *
     * @param command   The shell command to issue to the Operating System.
     * @param directory The directory to issue the command in.
     */
    public Command(String command, File directory) {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);

        while (m.find()) {
            String s = m.group(1);

            s = (s.startsWith("\"") || s.startsWith("'")) && (s.endsWith("\"") || s.endsWith("'")) ? s.substring(1, s.length() - 1) : s;

            list.add(s);
        }

        init(list.toArray(new String[0]), directory);
    }

    /**
     * Create a command with the specified Display instance, commands,
     * and directory to issue the commands in.
     *
     * @param commands  The list of shell commands to issue to the
     *                  Operating System.
     * @param directory The directory to issue the commands in.
     */
    public Command(String commands[], String directory) {
//		this.commands = commands;
        this(commands, new File(directory));
    }

    public Command(String commands[], File directory) {
        init(commands, directory);
    }

    private void init(String command[], File directory) {
        this.directory = directory;

        this.commands = command;

        listeners = new ArrayList<CommandListener>();
    }

    public void execute() throws IOException {
        execute(null);
    }

    public void execute(String title) throws IOException {
//		System.out.println(Arrays.asList(commands) + ", " + directory);

        ProcessBuilder builder = new ProcessBuilder(commands);

        if (directory != null) {
            builder.directory(directory);
        }

        final File error = File.createTempFile("processError", ".txt");
        final File input = File.createTempFile("processInput", ".txt");

        final InputStream errorStream = new FileInputStream(error);
        final InputStream inputStream = new FileInputStream(input);

//		builder.redirectError(error);
//		builder.redirectInput(input);

        final Result result = new Result();
        final ExecValues values = new ExecValues();

        final Process process = builder.start();

        commandThread = new Thread() {
            public void run() {
                commandThread2 = new Thread() {
                    public void run() {
                        values.lsr = new LogStreamReader(process.getInputStream(), listeners, System.getProperty("user.dir") + "/");
                        values.thread = new Thread(values.lsr, "LogStreamReader");
                        values.thread.start();

                        values.reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                        try {
                            String line = null;

                            while ((line = values.reader.readLine()) != null) {
                                result.value = 1;

                                for (int i = 0; i < listeners.size(); i++) {
                                    CommandListener listener = listeners.get(i);

                                    listener.errorReceived(line);
                                }
                            }

                            if (result.value == 0) {
                                result.value = process.waitFor();

                                StringBuilder builder = new StringBuilder();

                                line = null;

                                while ((line = values.reader.readLine()) != null) {
                                    builder.append(line + "\n");
                                }

                                if (builder.length() > 0) {
                                    while (builder.charAt(builder.length() - 1) == '\n' || builder.charAt(builder.length() - 1) == '\r') {
                                        builder.deleteCharAt(builder.length() - 1);
                                    }

                                    for (int i = 0; i < listeners.size(); i++) {
                                        CommandListener listener = listeners.get(i);

                                        listener.errorReceived(builder.toString());
                                    }
                                }

                                values.thread.join();
                                values.reader.close();
                                values.lsr.stop();
                                inputStream.close();
                                errorStream.close();

                                error.delete();
                                input.delete();
                            }

                            process.waitFor();

                            process.destroy();

                            for (int i = listeners.size() - 1; i >= 0; i--) {
                                listeners.get(i).resultReceived(result.value);
                            }

                            for (int i = listeners.size() - 1; i >= 0; i--) {
                                listeners.get(i).commandExecuted();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                commandThread2.start();
            }
        };

        commandThread.start();
    }

    public void terminate() throws InterruptedException {
        commandThread.join(1);
        commandThread2.join(1);
    }

    public void addCommandListener(CommandListener lisetener) {
        listeners.add(lisetener);
    }

    private class Result {
        int value;
    }

    private class ExecValues {
        LogStreamReader lsr;
        BufferedReader reader;
        Thread thread;
    }
}

/**
 * @version Mar 24, 2013 at 1:35:58 AM
 * @author Braden Steffaniak
 * @since Mar 24, 2013 at 1:35:58 AM
 * @since v0.1
 * @version v0.1
 */
class LogStreamReader implements Runnable {
    private boolean running;

    private String location, line;

    private BufferedReader reader;

    private ArrayList<CommandListener> listeners;

    public LogStreamReader(InputStream is, ArrayList<CommandListener> listeners, String location) {
        this.location = location;

        this.reader = new BufferedReader(new InputStreamReader(is));

        this.listeners = listeners;

        running = true;
    }

    public void run() {
        try {
            while ((line = reader.readLine()) != null) {
                if (running) {
                    for (int i = 0; i < listeners.size(); i++) {
                        CommandListener listener = listeners.get(i);

                        listener.messageReceived(line);
                    }
                } else {
                    return;
                }
            }

            line = null;

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }
}