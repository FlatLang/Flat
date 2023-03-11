package flat;

import flat.error.SyntaxErrorException;
import flat.tree.*;
import flat.tree.match.Match;
import flat.util.FileUtils;
import flat.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

/**
 * Class used for the compilation process.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:00:04 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Flat {
    public static String objectClassType = "class";
    public static boolean keepSuperCallInConstructor = false;
    private boolean testClasses;

    public boolean deleteOutputDirectory;

    private long flags;
    private long startTime, endTime;
    private static volatile int processedSteps = 0, stepsToProcess = 0, estimatedStepsToProcess = 0, lastProgress = 0;
    private static boolean logged = false;

    public File outputFile;

    public File workingDir, targetEngineWorkingDir;

    public String target, targetRuntime, formattedTarget;
    public ArrayList<String> targetFileExtensions = new ArrayList<>();

    private SyntaxTree tree;

    public ArrayList<String> externalImports, externalIncludes, libraries;
    public ArrayList<String> errors, warnings, messages;

    private String[] postArgs;

    public String installDirectoryArg;
    public File outputDirectory, installDirectory;

    public HashMap<String, String> outputDirectories;


    public HashSet<String> includeDirectories;
    public HashSet<String> defaultImports;
    public HashSet<String> defaultStaticImports;
    public ArrayList<File> inputFiles, excludeFiles;//, includeDirectories;
    public Stack<Long> flagsStack;

    public HashMap<File, ArrayList<File>> libraryFiles;

    public CodeGeneratorEngine codeGeneratorEngine;
    public CompileEngine compileEngine;

    public static final int OS;

    public static final String EXECUTABLE_EXTENSION, DYNAMIC_LIB_EXT;

    public boolean isTesting = false;

    private boolean expectingCompileError = false;

    public static final boolean ANDROID_DEBUG = false;
    public static final boolean PRIMITIVE_OVERLOADS = false;

    public static final boolean DEBUG = false;
    //	public static final boolean	DEBUG         = true;
//	public static final boolean USE_INSTALLED_TARGET = false;
    public static final boolean USE_INSTALLED_TARGET = true;
    //	public static final boolean USE_INSTALLED_STDLIB = false;
    public static final boolean USE_INSTALLED_STDLIB = true;

    // Set to 0 to not benchmark.
    public static final int BENCHMARK = 0;

    public static final long SINGLE_THREAD = 0x1000000000000000l;
    public static final long SMALL_BIN = 0x0100000000000000l;
    public static final long NO_GC = 0x0010000000000000l;
    public static final long LIBRARY = 0x0001000000000000l;
    public static final long RUNTIME = 0x0000100000000000l;
    public static final long C_ARGS = 0x0000010000000000l;
    //////////////////////////////////////////////
    public static final long SINGLE_FILE = 0x0000001000000000l;
    public static final long DRY_RUN = 0x0000000100000000l;
    public static final long VERBOSE = 0x0000000010000000l;
    public static final long FORMATC = 0x0000000001000000l;
    public static final long CSOURCE = 0x0000000000100000l;
    public static final long NO_C_OUTPUT = 0x0000000000010000l;
    public static final long NO_OPTIMIZE = 0x0000000000001000l;
    public static final long QUOTE_PATHS = 0x0000000000000100l;
    public static final long REPLACE_LOG_LINE = 0x0000000000000010l;

    public static final int WINDOWS = 1;
    public static final int MACOSX = 2;
    public static final int LINUX = 3;

    public static final String LANGUAGE_NAME = "Flat";
    public static final String VERSION = "v0.3.8";

    public static Flat instance;

    /**
     * Find out which operating system the compiler is running on.
     */
    static {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.startsWith("win")) {
            OS = WINDOWS;
            EXECUTABLE_EXTENSION = ".exe";
            DYNAMIC_LIB_EXT = ".dll";
        } else if (osName.startsWith("mac")) {
            OS = MACOSX;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT = ".dylib";
        } else if (osName.startsWith("lin")) {
            OS = LINUX;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT = ".so";
        } else {
            OS = 0;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT = "";
        }
    }

    private static class ProcessResponse {
        private String[] stdout, stderr;
        private int exitCode;

        public ProcessResponse(String[] stdout, String[] stderr, int exitCode) {
            this.stdout = stdout;
            this.stderr = stderr;
            this.exitCode = exitCode;
        }
    }

    public static ProcessResponse exec(
        String[] cmd,
        File workingDirectory
    ) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(cmd, null, workingDirectory.getCanonicalFile());

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        ArrayList<String> stdout = new ArrayList<>();
        ArrayList<String> stderr = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            stdout.add(line);
        }
        while ((line = errorReader.readLine()) != null) {
            stderr.add(line);
        }

        int exitCode = p.waitFor();

        return new ProcessResponse(
            stdout.toArray(new String[0]),
            stderr.toArray(new String[0]),
            exitCode
        );
    }

    /**
     * Method called whenever the compiler is invoked. Supplies the
     * needed information for compiling the given files.
     *
     * @param args The String array containing the locations of the files
     *             to compile, as well as other compiler arguments.
     */
    public static void main(String[] args) {
        if (args.length > 1 && args[0].equalsIgnoreCase("airship")) {
            try {
                String airshipExectuable = System.getProperty("user.home") + "/.flat/bin/airship";

                if (OS == WINDOWS) {
                    airshipExectuable += ".cmd";
                }

                ArrayList<String> execArgs = new ArrayList<>();

                execArgs.add(airshipExectuable);

                execArgs.addAll(Arrays.asList(args).subList(2, args.length));

                execArgs.add("--debug");
                execArgs.add("-q");

                ProcessResponse response = exec(execArgs.toArray(new String[0]), new File(args[1]));

                if (response.exitCode != 0) {
                    stream(response.stderr).forEach(System.err::println);
                    System.exit(response.exitCode);
                }

                String argsString = response.stdout[response.stdout.length - 1].trim();
                args = StringUtils.splitWhitespace(argsString);
                args = Arrays.copyOfRange(args, 3, args.length);
                args = Arrays.stream(args).map(StringUtils::removeSurroundingQuotes).toArray(String[]::new);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Flat flat = new Flat(args);

        int returnCode = 0;

        try {
            flat.compile(args, true);
            flat.compileEngine.compile();
        } catch (Exception e) {
            e.printStackTrace();
            returnCode = 1;
        }

        flat.outputMessages(false);

        System.exit(returnCode);
    }

    public static void unsupportedOs() {
        System.err.println("Unsupported operating system.");

        System.exit(1);
    }

    /**
     * Method used to initialize the compiler data and start the
     * compilation process.
     */
    public Flat(String[] args) {
        instance = this;

        if (BENCHMARK > 0) {
            try {
                System.out.println("Preparing Benchmark...");

                Thread.sleep(100);

                System.out.println("Starting...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            enableFlag(DRY_RUN);
        }

        inputFiles = new ArrayList<>();
        excludeFiles = new ArrayList<>();
        libraryFiles = new HashMap<>();
        externalImports = new ArrayList<>();
        externalIncludes = new ArrayList<>();
        libraries = new ArrayList<>();
        errors = new ArrayList<>();
        warnings = new ArrayList<>();
        messages = new ArrayList<>();
        flagsStack = new Stack<>();

//		cSourceFiles       = new ArrayList<>();
//		cHeaderFiles       = new ArrayList<>();
        includeDirectories = new HashSet<>();
        defaultImports = new HashSet<>();
        defaultStaticImports = new HashSet<>();
        outputDirectories = new HashMap<>();

        testClasses = false;//BENCHMARK <= 0;

        readCliArgs(args);

        if (installDirectoryArg != null) {
        } else if (OS == WINDOWS) {
            if (System.getenv("FLAT_HOME") == null) {
                System.err.println("FLAT_HOME environment variable is not set. Learn how to set them at http://flat-lang.org/docs/getting-started/configure-environment");

                System.exit(1);
            }

            installDirectory = new File(System.getenv("FLAT_HOME"));
        } else if (OS == MACOSX) {
            String workingPath = getWorkingDirectoryPath();

            installDirectory = new File("/Library/Application Support/Flat");
        } else if (OS == LINUX) {
            installDirectory = new File("/etc/flat");
        } else {
            unsupportedOs();
        }

        if (!installDirectory.isDirectory()) {
            System.err.println("Missing Flat install directory located at '" + installDirectory.getAbsolutePath() + "'");

            System.exit(1);
        }

        switch (target) {
            case "java":
                objectClassType = "trait";
                keepSuperCallInConstructor = true;
                break;
        }
    }

    private void startEngines() {
        try {
            String enginePath = null;

            if (!USE_INSTALLED_TARGET) {
                enginePath = "..";
            } else if (OS == WINDOWS) {
                enginePath = installDirectory.getAbsolutePath();//System.getenv("APPDATA") + "/Flat";
            } else if (OS == MACOSX) {
                enginePath = installDirectory.getAbsolutePath();
            } else if (OS == LINUX) {
                enginePath = installDirectory.getAbsolutePath();
            } else {
                unsupportedOs();
            }

            String folderName = "Flat-" + target;

            File engineDir = new File(enginePath);

            Optional<File> existingFile = Arrays.stream(engineDir.listFiles()).filter(x -> x.isDirectory() && x.getName().equalsIgnoreCase(folderName)).findFirst();

            if (!existingFile.isPresent()) {
                System.err.println("Could not find target directory for " + target + " compilation target in '" + new File(enginePath + "/" + folderName).getCanonicalPath() + "'");

                System.exit(1);
            }

            targetEngineWorkingDir = existingFile.get().getCanonicalFile();

            formattedTarget = targetEngineWorkingDir.getName().substring(targetEngineWorkingDir.getName().lastIndexOf('-') + 1);

            File engineJar = new File(enginePath + "/Flat-" + formattedTarget + "/target/flat-" + formattedTarget.toLowerCase() + ".jar");

            if (!engineJar.isFile()) {
                System.err.println("Could not find built target jar for " + target + " compilation target in '" + engineJar.getAbsolutePath() + "'");

                System.exit(1);
            }


            targetFileExtensions.add(target);

            if (target.equals("es6")) {
                targetFileExtensions.add("js");
            }

            try {
                URL url = engineJar.toURI().toURL();

                // Create a new class loader with the directory
                ClassLoader cl = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());

                Class codeGeneratorEngineClass = cl.loadClass("flat." + formattedTarget.toLowerCase() + ".engines." + formattedTarget + "CodeGeneratorEngine");
                Class compileEngineClass = cl.loadClass("flat." + formattedTarget.toLowerCase() + ".engines." + formattedTarget + "CompileEngine");

                java.lang.reflect.Constructor codeGeneratorEngineConstructor = codeGeneratorEngineClass.getConstructor(Flat.class);
                java.lang.reflect.Constructor compileEngineConstructor = compileEngineClass.getConstructor(Flat.class);

                codeGeneratorEngine = (CodeGeneratorEngine) codeGeneratorEngineConstructor.newInstance(this);
                compileEngine = (CompileEngine) compileEngineConstructor.newInstance(this);

                codeGeneratorEngine.init();
                compileEngine.init();
                compileEngine.addIncludeDirectories(includeDirectories);
            } catch (InvocationTargetException e) {
                System.exit(1);
            } catch (InstantiationException e) {
                System.exit(2);
            } catch (IllegalAccessException e) {
                System.exit(3);
            } catch (NoSuchMethodException e) {
                System.exit(4);
            } catch (ClassNotFoundException e) {
                System.exit(5);
            } catch (MalformedURLException e) {
                System.exit(6);
            }
        } catch (IOException io) {

        }

        //codeGeneratorEngine = new CCodeGeneratorEngine(this);
        //compileEngine = new CCompileEngine(this);
    }

    /**
     * Used to represent a debugging breakpoint...
     *
     * @param condition Whether or not to break.
     */
    public static boolean debuggingBreakpoint(boolean condition) {
        if (condition) {
            System.out.println("Breakpoint");
        }

        return condition;
    }

    public void readCliArgs(String[] args) {
        parseInitialArguments(args);

        if (target != null && (target.equals("js") || target.equals("es6"))) {
            targetRuntime = targetRuntime == null ? "node" : targetRuntime;
        } else {
            targetRuntime = targetRuntime == null ? "default" : targetRuntime;
        }

        String workingPath = getWorkingDirectoryPath();
        String directory = workingPath + "../Flat-Testing/example/";

        if (DEBUG) {
            testClasses();

            args = new String[]
                {
//				"../Flat.c",
//				"../plumber/plumbercalc",
//							"../Flat-Testing/example",
//							"../Flat-Testing/stabilitytest",
//							"-l", "../Test",
//							"-d", "../FlatCompilerOutput/" + target,
//				"-package-output-directory", "flat", "../StandardLibrary/" + target,
//				"-dir", formatPath(directory + "../example"),
//				"-dir", formatPath(directory + "../stabilitytest"),
//				"-run",
//				"-csource",
//				"-formatc",
//				testClasses ? "-v" : "",
//				"-v",
//				"-gcc",
//				"-tcc",
//				"-small",
//				"-cargs",
//				"-keepc",
//				"-qp",
//							"-main",
//				"example/Lab",
//							"stabilitytest/StabilityTest",
//				"plumbercalc/tests/AllTestsRunner",
//				"example/TestTest",
//				"example/SvgChart",
//				"example/SvgFractal",
//				"example/HashMapDemo",
//				"example/HashSetDemo",
//				"-nogc",
//				"-no-c-output",
//				"-dry",
//				"-force",
//				"-force-check",
//				"-no-notes",
//				"-no-warnings",
//				"-no-errors",
//				"-no-optimize",
//				"-target", target,
//				"-library",
//							"-o", formatPath(directory + "bin/Executable")
                };

//			args = new String[] {
//				"C:/Users/Braden/test.flat",
//				"-o",
//				"C:/Users/Braden/test"
//			};
        } else if (ANDROID_DEBUG) {
            enableFlag(DRY_RUN);
        } else {
            if (args.length == 0 || args[0].equals("-version")) {
                System.out.println("Flat " + VERSION);

                if (args.length == 0) {
                    System.err.println("Input files and/or directories must be specified to be compiled.");

                    System.exit(1);
                }

                System.exit(0);
            }
        }

        ArrayList<String> postArgsList = new ArrayList<>();

        enableFlag(SINGLE_THREAD);
        enableFlag(REPLACE_LOG_LINE);
        postArgsList.add("-single-file");
//		postArgsList.add("-line-numbers");
        postArgsList.add("-no-optimize");
        postArgsList.add("-v");
//		postArgsList.add("-target");
//		postArgsList.add(target);

        postArgs = postArgsList.toArray(new String[0]);

//		for (String location : standardFiles)
//		{
//			location = removeSurroundingQuotes(location);
//
//			inputFiles.add(new File(location));
//		}

        args = appendArguments(args, postArgs);

        parseArguments(args);

        inputFiles.removeAll(excludeFiles);
    }

    /**
     * Compile the input files given within the args.
     *
     * @param args The String array containing the locations of the files
     *             to compile, as well as other compiler arguments.
     */
    public void compile(String args[], boolean generateCode) {
        codeGeneratorEngine.initializeOutputDirectory();

//		log("Flat " + VERSION + " Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>\n" +
//				"This program comes with ABSOLUTELY NO WARRANTY\n" + //; for details type show w." +
//				"This is free software, and you are welcome to redistribute it\n" +
//				"under certain conditions");//; type show c for details.");

        workingDir = new File(getWorkingDirectoryPath());

        startTimer();

        createSyntaxTree(generateCode);
    }

    private String[] prependArguments(String args[], String... newData) {
        String temp[] = new String[args.length + newData.length];

        System.arraycopy(newData, 0, temp, 0, newData.length);
        System.arraycopy(args, 0, temp, newData.length, args.length);

        return temp;
    }

    private String[] appendArguments(String args[], String... newData) {
        String temp[] = new String[args.length + newData.length];

        System.arraycopy(args, 0, temp, 0, args.length);
        System.arraycopy(newData, 0, temp, args.length, newData.length);

        return temp;
    }

    private void createSyntaxTree(boolean generateCode) {
        // Try to create a Syntax Tree for the given file.
        try {
            int times = 1;

            if (BENCHMARK > 0) {
                times = BENCHMARK;
            }

            try {
                for (int i = 0; i < times; i++) {
                    long before = System.currentTimeMillis();

                    ArrayList<File> allFiles = new ArrayList<>(inputFiles);

                    for (Map.Entry<File, ArrayList<File>> entry : libraryFiles.entrySet()) {
                        entry.getValue().stream()
                            .filter(x -> allFiles.stream().noneMatch(f -> {
                                try {
                                    return f.getCanonicalPath().equals(x.getCanonicalPath());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }))
                            .forEach(allFiles::add);
                    }

                    allFiles.sort(Comparator.comparing(File::getName));

                    tree = new SyntaxTree(allFiles.toArray(new File[0]), this);
                    tree.generate();

                    codeGeneratorEngine.tree = tree;
                    compileEngine.tree = tree;

                    if (containsErrors()) {
                        enableFlag(DRY_RUN);
                        completed(false);
                    }

                    int lineCount = 0;

                    for (FileDeclaration file : tree.getFiles()) {
                        lineCount += file.getSource().split("\r\n|\r|\n").length;
                    }

                    log("Number of lines parsed: " + lineCount, true);

                    if (generateCode) {
                        long time = System.currentTimeMillis();
                        long newTime = time;

                        log("Generating output...");
                        codeGeneratorEngine.generateOutput();
                        log("Generating output took " + ((newTime = System.currentTimeMillis()) - time) + "ms", true);
                        time = newTime;

                        log("Inserting main method...");
                        codeGeneratorEngine.insertMainMethod();
                        log("Inserting main method took " + ((newTime = System.currentTimeMillis()) - time) + "ms", true);
                        time = newTime;

                        log("Formatting output...");
                        codeGeneratorEngine.formatOutput();
                        log("Formatting output took " + ((newTime = System.currentTimeMillis()) - time) + "ms", true);
                        time = newTime;

                        log("Writing files...");
                        codeGeneratorEngine.writeFiles();
                        log("Writing files took " + ((newTime = System.currentTimeMillis()) - time) + "ms", true);
                        time = newTime;

                    }

                    long time = System.currentTimeMillis() - before;

                    if (BENCHMARK > 1) {
                        System.out.println("Benchmark " + (i + 1) + ": " + time + "ms");
                    }
                }
            } catch (SyntaxErrorException e) {
                enableFlag(DRY_RUN);
                completed(false);
            }
        } catch (IOException e1) {
            error("Could not generate the syntax tree for the file '");// + file.getName() + "'.");

            e1.printStackTrace();

            completed(false);
        }

        long time = System.currentTimeMillis() - startTime;

        String str = LANGUAGE_NAME + " compile time: " + time + "ms";

        outputMessages(true);

        if (BENCHMARK > 0) {
            if (BENCHMARK > 1) {
                str += " (Average of " + String.format("%.3f", time / (float) BENCHMARK) + "ms)";
            }

            System.out.println(str);
        } else {
            log(str);
        }
    }

    /**
     * Output the log message from the compiler.
     *
     * @param message The message describing what happened.
     */
    public void log(String message) {
        log(flags, message, false);
    }

    /**
     * Output the log message from the compiler.
     *
     * @param message The message describing what happened.
     */
    public void log(String message, boolean persist) {
        log(flags, message, persist);
    }

    /**
     * Output the log message from the compiler.
     *
     * @param flags   The flags that verify whether the compiler is verbose.
     * @param message The message describing what happened.
     */
    public static void log(long flags, String message, boolean persist) {
        if (isFlagEnabled(flags, VERBOSE)) {
            String prefix = logged && isFlagEnabled(flags, REPLACE_LOG_LINE) ? "\033[2A\033[K" : "";
            System.out.println(prefix + message + "\n");
            logged = !persist;
        }

        if (lastProgress < 100) {
            instance.logProgress();
        }
    }

    public String etaTime(String defaultText) {
        if (lastProgress <= 1) {
            return defaultText;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedMs = currentTime - startTime;

        long remainingMs = Math.round(elapsedMs * (100.0 / lastProgress)) - elapsedMs;

        return (remainingMs / 1000) + "s";
    }

    private static String repeatSpaces(int count) {
        return Arrays.stream(new String[count]).map(x -> " ").collect(Collectors.joining(""));
    }

    public synchronized void logProgress() {
        if (stepsToProcess == 0) {
            return;
        }

        int targetSteps = Math.max(stepsToProcess, estimatedStepsToProcess);
        lastProgress = Math.max(lastProgress, processedSteps * 100 / targetSteps);

        String etaValue = etaTime("...");

        String prefix = "[";
        String percentage = (processedSteps * 100 / targetSteps) + "%";
        int percentagePosition = 0;
        String suffix = "] " +
            repeatSpaces(String.valueOf(targetSteps).length() - String.valueOf(processedSteps).length()) +
            processedSteps + "/" + targetSteps + " " +
            "eta " + repeatSpaces(Math.max(0, 3 - etaValue.length())) + etaValue;
        StringBuilder progress = new StringBuilder();
        int progressLength = 80 - prefix.length() - suffix.length();
        boolean printedTip = false;

        for (int i = 0; i < progressLength; i++) {
            int progressPosition = lastProgress * progressLength / 100;

            if (percentagePosition < percentage.length() && i >= progressLength / 2 - percentage.length() / 2) {
                progress.append(percentage.charAt(percentagePosition));
                percentagePosition++;
                if (i >= progressPosition) {
                    printedTip = true;
                }
            } else if (i < progressPosition) {
                progress.append("=");
            } else if (!printedTip) {
                progress.append(">");
                printedTip = true;
            } else {
                progress.append(" ");
            }
        }

        System.out.println("\033[1A\033[K" + prefix + progress + suffix);
    }

    public synchronized int getProcessedSteps() {
        return processedSteps;
    }

    public synchronized void processStep() {
        processStep(10);
    }

    public synchronized void processStep(int step) {
        processedSteps += step;
        logProgress();
    }

    public synchronized int getStepsToProcess() {
        return stepsToProcess;
    }

    public synchronized void addStepsToProcess(int count) {
        stepsToProcess += count;
        logProgress();
    }

    public synchronized void addEstimatedStepsToProcess(int count) {
        estimatedStepsToProcess += count;
        logProgress();
    }

    public synchronized int getEstimatedStepsToProcess() {
        return estimatedStepsToProcess;
    }

    public synchronized void setEstimatedStepsToProcess(int count) {
        estimatedStepsToProcess = count;
        logProgress();
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     */
    public void warning(String message) {
        warnings.add("Warning: " + message);
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     */
    public void error(String message) {
        if (!isFlagEnabled(DRY_RUN)) {
            enableFlag(DRY_RUN);
        }

        String error = "Error: " + message;

        errors.add(error);
    }

    /**
     * Get whether or not the compilation has accumulated any errors.
     *
     * @return Whether or not there are any errors currently.
     */
    public boolean containsErrors() {
        return errors.size() > 0;
    }

    /**
     * Add the given external import location to be added to the
     * compilation list.
     *
     * @param file     The File that is importing the location.
     * @param location The location that is being imported.
     */
    public void addExternalImport(FileDeclaration file, String location) {
        if (!StringUtils.containsString(location, FileDeclaration.DEFAULT_IMPORTS)) {
//			location = file.getFile().getParent() + "/" + location;
            String absoluteLocation = location.substring(0, location.length() - 1) + target;
            absoluteLocation = FileUtils.findFileLocation(absoluteLocation, includeDirectories.toArray(new String[0]));

            if (absoluteLocation != null && !StringUtils.containsString(externalImports, absoluteLocation)) {
                externalImports.add(absoluteLocation);
                externalIncludes.add(location);
            }
        }
    }

    private void parseInitialArguments(String args[]) {
        // Start off the lastInput index to -1 because it will start
        // checking for (index - 1).
        // (index starts at 0, therefore 0 - 1 = -1)
        int lastInput = -1;
        int skip = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("\"")) {
                args[i] = StringUtils.removeSurroundingQuotes(args[i]);
            }
            if (skip > 0) {
                skip--;

                continue;
            }

            // Lowercase the argument for easier non-case-sensitive String
            // matching.
            String arg = args[i].toLowerCase();

            if (arg.length() <= 0) {
                if (lastInput == i - 1) {
                    lastInput = i;
                }

                continue;
            }

            if (arg.equals("-install-dir")) {
                validateArgumentSize(args, i + 1, arg);

                installDirectoryArg = args[i + 1];
                installDirectory = new File(installDirectoryArg);

                skip = 1;
            } else if (arg.toLowerCase().equals("-target")) {
                validateArgumentSize(args, i + 1, args[i]);

                target = args[i + 1].toLowerCase();
                skip = 1;
            } else if (arg.toLowerCase().equals("-target-runtime")) {
                validateArgumentSize(args, i + 1, args[i]);

                targetRuntime = args[i + 1].toLowerCase();
                skip = 1;
            }
        }
    }

    /**
     * Parse the arguments passed to the compiler.
     *
     * @param args The list of arguments to parse.
     */
    private void parseArguments(String args[]) {
        int lastInput = -1;
        int skip = 0;

        startEngines();

        // Iterate through all of the arguments.
        for (int i = 0; i < args.length; i++) {
            if (skip > 0) {
                skip--;

                continue;
            }

            // Lowercase the argument for easier non-case-sensitive String
            // matching.
            String arg = args[i].toLowerCase();

            if (arg.length() <= 0) {
                if (lastInput == i - 1) {
                    lastInput = i;
                }

                continue;
            }

            if (compileEngine.checkArgument(arg, args, i)) {

            }
            // If the user is trying to set the output location.
            else if (arg.equals("-o")) {
                validateArgumentSize(args, postArgs.length + i + 1, "-o", "Expected output file name after argument '-o'");

                outputFile = new File(args[i + 1]);

                skip = 1;
            }
            // If the user is trying to set the source include directory.
            else if (arg.equals("-dir")) {
                validateArgumentSize(args, i + 1, arg);

                includeDirectories.add(FileUtils.formatPath(args[i + 1]));

                skip = 1;
            } else if (arg.equals("-default-import")) {
                validateArgumentSize(args, i + 1, arg);

                defaultImports.add(args[i + 1]);

                skip = 1;
            } else if (arg.equals("-default-static-import")) {
                validateArgumentSize(args, i + 1, arg);

                defaultStaticImports.add(args[i + 1]);

                skip = 1;
            } else if (arg.equals("-install-dir")) {
                validateArgumentSize(args, i + 1, arg);

                skip = 1;
            } else if (arg.equals("-std-path")) {
                validateArgumentSize(args, i + 1, arg);

                skip = 1;
            }
            // If the user wants to run the application after compilation.
            else if (arg.equals("-run")) {
                enableFlag(RUNTIME);
            }
            // Do not use bdw garbage collection.
            else if (arg.equals("-nogc")) {
                enableFlag(NO_GC);
            } else if (arg.equals("-no-c-output")) {
                enableFlag(NO_C_OUTPUT);
            } else if (arg.equals("-no-optimize")) {
                enableFlag(NO_OPTIMIZE);
            } else if (arg.equals("-target")) {
                skip = 1;
            } else if (arg.equals("-target-runtime")) {
                skip = 1;
            } else if (arg.equals("-main")) {
                validateArgumentSize(args, i + 1, arg);

                codeGeneratorEngine.mainClass = args[i + 1];

                skip = 1;
            }
            // If the user wants to view the c source output.
            else if (arg.equals("-csource")) {
                enableFlag(CSOURCE);
            }
            // If the user wants to format the c source output.
            else if (arg.equals("-formatc")) {
                enableFlag(FORMATC);
            }
            // If the user wants a more verbose compilation output,
            // explaining each step.
            else if (arg.equals("-verbose") || arg.equals("-v")) {
                if (BENCHMARK <= 0) {
                    enableFlag(VERBOSE);
                }
            }
            // If the user wants to generate the output to a single file
            else if (arg.equals("-single-file")) {
                enableFlag(SINGLE_FILE);
            }
            // If the user wants to run a single threaded compilation
            else if (arg.equals("-single-thread")) {
                enableFlag(SINGLE_THREAD);
            }
            // If the user wants to run a single threaded compilation
            else if (arg.equals("-parallel")) {
                disableFlag(SINGLE_THREAD);
            }
            // If the user wants to perform a dry run of the compilation
            // process.
            else if (arg.equals("-dry")) {
                enableFlag(DRY_RUN);
            } else if (arg.equals("-no-replace-log-line")) {
                disableFlag(REPLACE_LOG_LINE);
            }
            // If the user wants to format the makefile file paths with quotes
            // instead of backslashes for escaping spaces.
            else if (arg.equals("-quotes") || arg.equals("-qp")) {
                enableFlag(QUOTE_PATHS);
            }
            // If the user wants to obtain the c compiler arguments.
            else if (arg.equals("-cargs")) {
                enableFlag(C_ARGS);
            }
            // If the user wants to generate a smaller executable output.
            else if (arg.equals("-small")) {
                enableFlag(SMALL_BIN);
            }
            // If the user wants to output a library instead of an
            // executable.
            else if (arg.equals("-library")) {
                enableFlag(LIBRARY);
            }
            // If the user wants to output a library instead of an
            // executable.
            else if (arg.equals("-l")) {
                validateArgumentSize(args, i + 1, arg);

                libraries.add(args[i + 1]);

                try {
                    addIfNotExists(inputFiles, getFiles(args[i + 1]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                skip = 1;
            } else if (arg.equals("-x")) {
                validateArgumentSize(args, i + 1, arg);

                try {
                    addIfNotExists(excludeFiles, getFiles(args[i + 1]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                skip = 1;
            }
            // Specify a custom output directory.
            else if (arg.equals("-output-directory") || arg.equals("-d")) {
                validateArgumentSize(args, i + 1, arg);

                try {
                    outputDirectory = new File(args[i + 1]).getCanonicalFile();
                    outputDirectory.mkdirs();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                skip = 1;
            }
            // Specify a custom output directory for a specified package.
            else if (arg.equals("-package-output-directory")) {
                validateArgumentSize(args, i + 2, arg);

                outputDirectories.put(args[i + 1], args[i + 2]);

                skip = 2;
            }
            // If none of the arguments were matched, check these:
            else {
                // If the argument is one of the first arguments passed
                // (If it is one of the sources to compile)
                if (lastInput == i - 1) {
                    if (args[i].startsWith("glob:")) {
                        try {
                            addIfNotExists(inputFiles, getFiles(args[i].substring("glob:".length())));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            addIfNotExists(inputFiles, getFiles(args[i]));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    lastInput = i;
                } else if (i == args.length - 1) {
                    outputFile = new File(args[i]);
                } else {
                    error("Unknown argument '" + args[i] + "'");
                    completed(false);
                }
            }
        }

        if (outputFile == null && outputDirectory == null) {
            System.err.println("You must specify an output file using the -o argument. e.g. 'flatc Test.flat -o Test'");

            System.exit(1);
        }
    }

    private List<File> getFiles(String location) throws IOException {
        if (!location.contains("*")) {
            location = location + "/**/*.flat";
        }

        String[] directories = splitParentDirectory(location, true);
        return getFiles(Paths.get(directories[0]), directories[1]);
    }

    public static String[] splitParentDirectory(String path, boolean checkGlob) {
        if (new File(path).isDirectory()) {
            return new String[]{path, ""};
        }
        if (new File(path).isAbsolute()) {
            String suffix = "";

            if (checkGlob) {
                int index = path.indexOf("*");

                if (index > 0) {
                    int slash = path.lastIndexOf('/', index - 1);

                    if (slash > 0) {
                        suffix = path.substring(slash + 1);
                        path = path.substring(0, slash);
                    }
                }
            }

            return new String[]{path, suffix};
        }

        String suffix = "";
        String home = System.getProperty("user.dir");

        if (checkGlob) {
            int index = path.indexOf("*");

            if (index > 0) {
                int slash = path.lastIndexOf('/', index - 1);

                if (slash > 0) {
                    suffix = path.substring(slash + 1);
                    path = path.substring(0, slash);
                }
            }
        }

        return new String[]{Paths.get(home, path).normalize().toString(), suffix};
    }

    public static void addIfNotExists(ArrayList<File> list, List<File> toAdd) {
        list.addAll(
            toAdd.stream()
                .filter(file -> list.stream().noneMatch(f -> {
                    try {
                        return f.getCanonicalPath().equals(file.getCanonicalPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .collect(Collectors.toList())
        );
    }

    public static List<File> getFiles(final Path directory, final String glob) throws IOException {
        final PathMatcher matcher = FileSystems.getDefault()
            .getPathMatcher("glob:" + glob);

        try (Stream<Path> paths = Files.walk(directory)) {
            return paths
                .filter(matcher::matches)
                .map(path -> new File(path.toUri()))
                .collect(Collectors.toList());
        }
    }

    public File getLibrary(File file) {
        for (Map.Entry<File, ArrayList<File>> entry : libraryFiles.entrySet()) {
            if (entry.getValue().contains(file)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private void validateArgumentSize(String[] args, int size, String arg) {
        validateArgumentSize(args, size, arg, null);
    }

    private void validateArgumentSize(String[] args, int size, String arg, String message) {
        if (args.length <= size) {
            System.err.println(message == null ? "Invalid arguments passed" : message);

            System.exit(1);
        }
    }

    public boolean isExpectingCompileError() {
        return expectingCompileError;
    }

    public void setExpectingCompileError(boolean expectingCompileError) {
        this.expectingCompileError = expectingCompileError;
    }

    public void pushFlags() {
        flagsStack.push(flags);
    }

    public void popFlags() {
        flags = flagsStack.pop();
    }

    /**
     * Enable the specified flag.
     *
     * @param flag The flag to set enable.
     */
    public void enableFlag(long flag) {
        flags |= flag;
    }

    /**
     * Disable the specified flag.
     *
     * @param flag The flag to disable.
     */
    public void disableFlag(long flag) {
        flags = flags & (~flag);
    }

    /**
     * Check if the specific flag is enabled for the compiler.
     *
     * @param flag The flag to check if is enabled.
     * @return Whether or not the flag is enabled.
     */
    public boolean isFlagEnabled(long flag) {
        return isFlagEnabled(flags, flag);
    }

    /**
     * Check if the specific flag is enabled for the given set of flags.
     *
     * @param flags The flags to verify the flag with.
     * @param flag  The flag to check if is enabled.
     * @return Whether or not the flag is enabled.
     */
    public static boolean isFlagEnabled(long flags, long flag) {
        return (flags & flag) != 0;
    }

    /**
     * Get the working directory of the compiler.
     *
     * @return The working directory of the compiler.
     */
    private static String getWorkingDirectoryPath() {
        if (ANDROID_DEBUG) {
            return "/mnt/sdcard/AppProjects/Flat/";
        }

        return System.getProperty("user.dir").replace('\\', '/') + "/";
    }

    /**
     * Start the compilation timer.
     */
    private void startTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Stop the compilation timer.
     */
    private void stopTimer() {
        endTime = System.currentTimeMillis();
    }

    /**
     * Get the time the compiler took to compile the input files.
     *
     * @return The time in milliseconds it took to compile.
     */
    private long getCompileTime() {
        return endTime - startTime;
    }

    public void setTestClasses(boolean testClasses) {
        this.testClasses = testClasses;
    }

    public SyntaxTree getTree() {
        return tree;
    }

    private void outputMessages() {
        outputMessages(true);
    }

    /**
     * Output all of the stored errors, warnings, and other messages.
     */
    private void outputMessages(boolean outputResult) {
        outputMessages(true, outputResult);
    }

    private void outputMessages(boolean success, boolean outputResult) {
        outputMessages(success, warnings.size(), errors.size(), outputResult);
    }

    private void outputMessages(boolean success, int warningCount, int errorCount) {
        outputMessages(success, warningCount, errorCount, true);
    }

    private void outputMessages(boolean success, int warningCount, int errorCount, boolean outputResult) {
        for (String s : messages) {
            System.out.println(s);
        }
        for (String s : warnings) {
            System.out.println(s);
        }
        for (String s : errors) {
            System.err.println(s);
        }

        if (outputResult) {
            String status = success ? "succeeded" : "failed";
            String errorsText = "";
            String warningsText = "";

            if (warningCount > 0) {
                warningsText = " " + warningCount + " warning" + (warningCount > 1 ? "s" : "");
            }
            if (errorCount > 0) {
                status = "failed";

                errorsText = " " + errorCount + " error" + (errorCount > 1 ? "s" : "");
            }

            String with = errorsText.length() + warningsText.length() > 0 ? " with" : "";
            String message = "Compilation " + status + with + errorsText + warningsText;

            if (status.equals("succeeded")) {
                log(message);
            } else {
                System.err.println(message);
            }
        }

        messages = new ArrayList<>();
        warnings = new ArrayList<>();
        errors = new ArrayList<>();
    }

    /**
     * Method called whenever the compilation sequence has completed.
     */
    public void completed(boolean success) {
        completed(success, warnings.size(), errors.size());
    }

    public void completed(boolean success, int warningCount, int errorCount) {
        stopTimer();

        log("Compile time: " + getCompileTime() + "ms", true);

        outputMessages(success, warningCount, errorCount);

        if (isFlagEnabled(RUNTIME)) {
//			final Command c = new Command("start bin/Executa.exe", workingDir);
//
//			c.addCommandListener(new CommandListener()
//			{
//
//				@Override
//				public void resultReceived(int result)
//				{
//					if (result != 0)
//					{
//						System.err.println("error.");
//					}
//				}
//
//				@Override
//				public void messageReceived(String message)
//				{
//					System.out.println(message);
//				}
//
//				@Override
//				public void errorReceived(String message)
//				{
//					System.err.println(message);
//				}
//
//				@Override
//				public void commandExecuted()
//				{
//					try
//					{
//						c.terminate();
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			});
//			try
//			{
//				c.execute();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
        }

        if (!ANDROID_DEBUG) {
            System.exit(success ? 0 : 1);
        }
    }

    public static Flat generateTemporaryController(String[] args) {
        Flat controller = new Flat(args);
        controller.isTesting = true;
        controller.setTestClasses(false);
        controller.compile(new String[0], false);

        return controller;
    }

    /**
     * Call the test case methods for all of the classes to make sure they
     * are working correctly.
     */
    private void testClasses() {
        if (testClasses) {
            System.out.println("Testing classes");

            TestContext context = new TestContext();

            String error = null;

            error = ArgumentList.test(context);

            if (error == null) {
                error = Assignment.test(context);

                if (error == null) {
                    error = BinaryOperation.test(context);

                    if (error == null) {
                        error = Cast.test(context);

                        if (error == null) {
                            error = ClassDeclaration.test(context);

                            if (error == null) {
                                error = Closure.test(context);

                                if (error == null) {
                                    error = ClosureDeclaration.test(context);

                                    if (error == null) {
                                        error = Condition.test(context);

                                        if (error == null) {
                                            error = Constructor.test(context);

                                            if (error == null) {
                                                error = Destructor.test(context);

                                                if (error == null) {
                                                    error = Dimensions.test(context);

                                                    if (error == null) {
                                                        error = ElseStatement.test(context);

                                                        if (error == null) {
                                                            error = ExternalMethodDeclaration.test(context);

                                                            if (error == null) {
                                                                error = ExternalType.test(context);

                                                                if (error == null) {
                                                                    error = ExternalTypeList.test(context);

                                                                    if (error == null) {
                                                                        error = FileDeclaration.test(context);

                                                                        if (error == null) {
                                                                            error = ForLoop.test(context);

                                                                            if (error == null) {
                                                                                error = Identifier.test(context);

                                                                                if (error == null) {
                                                                                    error = IfStatement.test(context);

                                                                                    if (error == null) {
                                                                                        error = IIdentifier.test(context);

                                                                                        if (error == null) {
                                                                                            error = Import.test(context);

                                                                                            if (error == null) {
                                                                                                error = ImportList.test(context);

                                                                                                if (error == null) {
                                                                                                    error = InstanceDeclaration.test(context);

                                                                                                    if (error == null) {
                                                                                                        error = Instantiation.test(context);

                                                                                                        if (error == null) {
                                                                                                            error = IValue.test(context);

                                                                                                            if (error == null) {
                                                                                                                error = Literal.test(context);

                                                                                                                if (error == null) {
                                                                                                                    error = LocalDeclaration.test(context);

                                                                                                                    if (error == null) {
                                                                                                                        error = Loop.test(context);

                                                                                                                        if (error == null) {
                                                                                                                            error = LoopInitialization.test(context);

                                                                                                                            if (error == null) {
                                                                                                                                error = LoopUpdate.test(context);

                                                                                                                                if (error == null) {
                                                                                                                                    error = MethodCall.test(context);

                                                                                                                                    if (error == null) {
                                                                                                                                        error = MethodCallArgumentList.test(context);

                                                                                                                                        if (error == null) {
                                                                                                                                            error = MethodDeclaration.test(context);

                                                                                                                                            if (error == null) {
                                                                                                                                                error = MethodList.test(context);

                                                                                                                                                if (error == null) {
                                                                                                                                                    error = Node.test(context);

                                                                                                                                                    if (error == null) {
                                                                                                                                                        error = Operator.test(context);

                                                                                                                                                        if (error == null) {
                                                                                                                                                            error = Parameter.test(context);

                                                                                                                                                            if (error == null) {
                                                                                                                                                                error = ParameterList.test(context);

                                                                                                                                                                if (error == null) {
                                                                                                                                                                    error = Priority.test(context);

                                                                                                                                                                    if (error == null) {
                                                                                                                                                                        error = Program.test(context);

                                                                                                                                                                        if (error == null) {
                                                                                                                                                                            error = Return.test(context);

                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                error = Scope.test(context);

                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                    error = SyntaxTree.test(context);

                                                                                                                                                                                    if (error == null) {
                                                                                                                                                                                        error = TreeGenerator.test(context);

                                                                                                                                                                                        if (error == null) {
                                                                                                                                                                                            error = UnaryOperation.test(context);

                                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                                error = Until.test(context);

                                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                                    error = Value.test(context);

                                                                                                                                                                                                    if (error == null) {
                                                                                                                                                                                                        error = VTable.test(context);

                                                                                                                                                                                                        if (error == null) {
                                                                                                                                                                                                            error = GenericCompatible.test(context);

                                                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                                                error = WhileLoop.test(context);

                                                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                                                    error = Match.test(context);
                                                                                                                                                                                                                }
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (error != null) {
                System.err.println("Pre-compilation class tests failed:");
                System.err.println(error);

                completed(true);
            }

            System.out.println("Done testing classes");
        }
    }
}
