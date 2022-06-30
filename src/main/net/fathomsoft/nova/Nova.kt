package net.fathomsoft.nova

import net.fathomsoft.nova.error.SyntaxErrorException
import net.fathomsoft.nova.tree.*
import net.fathomsoft.nova.tree.match.Match
import net.fathomsoft.nova.util.FileUtils
import net.fathomsoft.nova.util.StringUtils
import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.net.MalformedURLException
import java.net.URLClassLoader
import java.util.*

/**
 * Class used for the compilation process.
 *
 * @author    Braden Steffaniak
 * @since    v0.1 Jan 5, 2014 at 9:00:04 PM
 * @version    v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
class Nova(args: Array<String>) {
    private var testClasses: Boolean
    @JvmField
	var deleteOutputDirectory = false
    private var flags: Long = 0
    private var startTime: Long = 0
    private var endTime: Long = 0
    @JvmField
    var outputFile: File? = null
    @JvmField
    var workingDir: File? = null
    @JvmField
    var targetEngineWorkingDir: File? = null
    @JvmField
	var target: String? = null
    var formattedTarget: String? = null
    @JvmField
	var targetFileExtension: String? = null
    var tree: SyntaxTree? = null
        private set
    var externalImports: ArrayList<String>
    var externalIncludes: ArrayList<String>
    var libraries: ArrayList<String?>
    @JvmField
	var errors: ArrayList<String>
    var warnings: ArrayList<String>
    var messages: ArrayList<String>
    private lateinit var postArgs: Array<String>
    var installDirectoryArg: String? = null
    var standardLibraryPath: String? = null
    @JvmField
	var outputDirectory: File? = null
    var installDirectory: File? = null
    @JvmField
	var outputDirectories: HashMap<String?, String?>
    var includeDirectories: HashSet<String>
    var inputFiles //, includeDirectories;
            : ArrayList<File>
    @JvmField
    var flagsStack: Stack<Long>
    @JvmField
    var libraryFiles: HashMap<File, ArrayList<File>>
    @JvmField
    var codeGeneratorEngine: CodeGeneratorEngine? = null
    @JvmField
    var compileEngine: CompileEngine? = null
    @JvmField
	var isTesting = false

    /**
     * Method used to initialize the compiler data and start the
     * compilation process.
     */
    init {
        if (BENCHMARK > 0) {
            try {
                println("Preparing Benchmark...")
                Thread.sleep(100)
                println("Starting...")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            enableFlag(DRY_RUN)
        }
        inputFiles = ArrayList()
        libraryFiles = HashMap()
        externalImports = ArrayList()
        externalIncludes = ArrayList()
        libraries = ArrayList()
        errors = ArrayList()
        warnings = ArrayList()
        messages = ArrayList()
        flagsStack = Stack()

//		cSourceFiles       = new ArrayList<>();
//		cHeaderFiles       = new ArrayList<>();
        includeDirectories = HashSet()
        outputDirectories = HashMap()
        testClasses = false //BENCHMARK <= 0;
        readCliArgs(args)
        if (installDirectoryArg != null) {
        } else if (OS == WINDOWS) {
            if (System.getenv("NOVA_HOME") == null) {
                System.err.println("NOVA_HOME environment variable is not set. Learn how to set them at http://nova-lang.org/docs/getting-started/configure-environment")
                System.exit(1)
            }
            installDirectory = File(System.getenv("NOVA_HOME"))
        } else if (OS == MACOSX) {
            val workingPath = workingDirectoryPath
            installDirectory = File("/Library/Application Support/Nova")
        } else if (OS == LINUX) {
            installDirectory = File("/etc/nova")
        } else {
            unsupportedOs()
        }
        if (!installDirectory!!.isDirectory) {
            System.err.println("Missing Nova install directory located at '" + installDirectory!!.absolutePath + "'")
            System.exit(1)
        }
    }

    private fun startEngines() {
        try {
            var enginePath: String? = null
            if (!USE_INSTALLED_TARGET) {
                enginePath = ".."
            } else if (OS == WINDOWS) {
                enginePath = installDirectory!!.absolutePath //System.getenv("APPDATA") + "/Nova";
            } else if (OS == MACOSX) {
                enginePath = installDirectory!!.absolutePath
            } else if (OS == LINUX) {
                enginePath = installDirectory!!.absolutePath
            } else {
                unsupportedOs()
            }
            val folderName = "Nova-$target"
            val engineDir = File(enginePath)
            val existingFile = Arrays.stream(engineDir.listFiles()).filter { x: File -> x.isDirectory && x.name.equals(folderName, ignoreCase = true) }.findFirst()
            if (!existingFile.isPresent) {
                System.err.println("Could not find target directory for " + target + " compilation target in '" + File("$enginePath/$folderName").canonicalPath + "'")
                System.exit(1)
            }
            targetEngineWorkingDir = existingFile.get().canonicalFile
            formattedTarget = targetEngineWorkingDir!!.getName().substring(targetEngineWorkingDir!!.getName().lastIndexOf('-') + 1)
            val engineJar = File(enginePath + "/Nova-" + formattedTarget + "/target/nova-" + formattedTarget!!.lowercase(Locale.getDefault()) + "-1.0-SNAPSHOT.jar")
            if (!engineJar.isFile) {
                System.err.println("Could not find built target jar for " + target + " compilation target in '" + engineJar.absolutePath + "'")
                System.exit(1)
            }
            targetFileExtension = formattedTarget!!.lowercase(Locale.getDefault())
            try {
                val url = engineJar.toURL()

                // Create a new class loader with the directory
                val cl: ClassLoader = URLClassLoader(arrayOf(url), this.javaClass.classLoader)
                val codeGeneratorEngineClass = cl.loadClass("nova." + targetFileExtension + ".engines." + formattedTarget + "CodeGeneratorEngine")
                val compileEngineClass = cl.loadClass("nova." + targetFileExtension + ".engines." + formattedTarget + "CompileEngine")
                val codeGeneratorEngineConstructor = codeGeneratorEngineClass.getConstructor(Nova::class.java)
                val compileEngineConstructor = compileEngineClass.getConstructor(Nova::class.java)
                codeGeneratorEngine = codeGeneratorEngineConstructor.newInstance(this) as CodeGeneratorEngine
                compileEngine = compileEngineConstructor.newInstance(this) as CompileEngine
                codeGeneratorEngine!!.init()
                compileEngine!!.init()
                compileEngine!!.addIncludeDirectories(includeDirectories)
            } catch (e: InvocationTargetException) {
                System.exit(1)
            } catch (e: InstantiationException) {
                System.exit(2)
            } catch (e: IllegalAccessException) {
                System.exit(3)
            } catch (e: NoSuchMethodException) {
                System.exit(4)
            } catch (e: ClassNotFoundException) {
                System.exit(5)
            } catch (e: MalformedURLException) {
                System.exit(6)
            }
        } catch (io: IOException) {
        }

        //codeGeneratorEngine = new CCodeGeneratorEngine(this);
        //compileEngine = new CCompileEngine(this);
    }

    fun readCliArgs(args: Array<String>) {
        var args = args
        parseInitialArguments(args)
        val workingPath = workingDirectoryPath
        val directory = "$workingPath../Nova-Testing/example/"
        if (standardLibraryPath == null) {
            standardLibraryPath = "../Standard-Library"
            if (USE_INSTALLED_STDLIB) {
                if (OS == WINDOWS) {
//				standardLibraryPath = System.getenv("APPDATA") + "/Nova/Standard-Library";
                    standardLibraryPath = installDirectory!!.absolutePath + "/Standard-Library"
                } else if (OS == MACOSX) {
                    standardLibraryPath = installDirectory!!.absolutePath + "/Standard-Library"
                } else if (OS == LINUX) {
                    standardLibraryPath = installDirectory!!.absolutePath + "/Standard-Library"
                }
            }
        }
        if (DEBUG) {
            testClasses()
            args = arrayOf()

//			args = new String[] {
//				"C:/Users/Braden/test.nova",
//				"-o",
//				"C:/Users/Braden/test"
//			};
        } else if (ANDROID_DEBUG) {
            enableFlag(DRY_RUN)
        } else {
            if (args.size == 0 || args[0] == "-version") {
                println("Nova " + VERSION)
                if (args.size == 0) {
                    System.err.println("Input files and/or directories must be specified to be compiled.")
                    System.exit(1)
                }
                System.exit(0)
            }
        }
        val postArgsList = ArrayList<String>()
        postArgsList.add("-single-thread")
        postArgsList.add("-single-file")
        //		postArgsList.add("-line-numbers");
        postArgsList.add("-no-optimize")
        postArgsList.add("-v")
        //		postArgsList.add("-target");
//		postArgsList.add(target);
        postArgsList.add("-l")
        postArgsList.add(standardLibraryPath!!)
        postArgs = postArgsList.toTypedArray()

//		for (String location : standardFiles)
//		{
//			location = removeSurroundingQuotes(location);
//
//			inputFiles.add(new File(location));
//		}

//		args = prependArguments(args, new String[] { standardLibraryPath + "/nova" });
        args = appendArguments(args, *postArgs)
        parseArguments(args)
    }

    /**
     * Compile the input files given within the args.
     *
     * @param args The String array containing the locations of the files
     * to compile, as well as other compiler arguments.
     */
    fun compile(args: Array<String>?, generateCode: Boolean) {
        codeGeneratorEngine!!.initializeOutputDirectory()

//		log("Nova " + VERSION + " Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>\n" +
//				"This program comes with ABSOLUTELY NO WARRANTY\n" + //; for details type show w." +
//				"This is free software, and you are welcome to redistribute it\n" +
//				"under certain conditions");//; type show c for details.");
        workingDir = File(workingDirectoryPath)
        startTimer()
        createSyntaxTree(generateCode)
    }

    private fun prependArguments(args: Array<String>, vararg newData: String): Array<String> {
        val temp = Array(args.size + newData.size) { "" }
        System.arraycopy(newData, 0, temp, 0, newData.size)
        System.arraycopy(args, 0, temp, newData.size, args.size)
        return temp
    }

    private fun appendArguments(args: Array<String>, vararg newData: String): Array<String> {
        val temp = Array(args.size + newData.size) { "" }
        System.arraycopy(args, 0, temp, 0, args.size)
        System.arraycopy(newData, 0, temp, args.size, newData.size)
        return temp
    }

    private fun createSyntaxTree(generateCode: Boolean) {
        // Try to create a Syntax Tree for the given file.
        try {
            var times = 1
            if (BENCHMARK > 0) {
                times = BENCHMARK
            }
            try {
                for (i in 0 until times) {
                    val before = System.currentTimeMillis()
                    val allFiles = ArrayList(inputFiles)
                    for ((_, value) in libraryFiles) {
                        value.stream()
                                .filter { x: File ->
                                    allFiles.stream().noneMatch { f: File ->
                                        try {
                                            return@noneMatch f.canonicalPath == x.canonicalPath
                                        } catch (e: IOException) {
                                            throw RuntimeException(e)
                                        }
                                    }
                                }
                                .forEach { e: File -> allFiles.add(e) }
                    }
                    allFiles.sortWith(Comparator.comparing { obj: File -> obj.name })
                    tree = SyntaxTree(allFiles.toTypedArray(), this)
                    codeGeneratorEngine!!.tree = tree
                    compileEngine!!.tree = tree
                    if (containsErrors()) {
                        enableFlag(DRY_RUN)
                        completed(false)
                    }
                    if (generateCode) {
                        var time = System.currentTimeMillis()
                        var newTime = time
                        log("Generating output... ", false)
                        codeGeneratorEngine!!.generateOutput()
                        log("took " + (System.currentTimeMillis().also { newTime = it } - time) + "ms")
                        time = newTime
                        log("Inserting main method... ", false)
                        codeGeneratorEngine!!.insertMainMethod()
                        log("took " + (System.currentTimeMillis().also { newTime = it } - time) + "ms")
                        time = newTime
                        log("Formatting output... ", false)
                        codeGeneratorEngine!!.formatOutput()
                        log("took " + (System.currentTimeMillis().also { newTime = it } - time) + "ms")
                        time = newTime
                        log("Writing files... ")
                        codeGeneratorEngine!!.writeFiles()
                        log("took " + (System.currentTimeMillis().also { newTime = it } - time) + "ms")
                        time = newTime
                    }
                    val time = System.currentTimeMillis() - before
                    if (BENCHMARK > 1) {
                        println("Benchmark " + (i + 1) + ": " + time + "ms")
                    }
                }
            } catch (e: SyntaxErrorException) {
                enableFlag(DRY_RUN)
                completed(false)
            }
        } catch (e1: IOException) {
            error("Could not generate the syntax tree for the file '") // + file.getName() + "'.");
            e1.printStackTrace()
            completed(false)
        }
        val time = System.currentTimeMillis() - startTime
        var str = LANGUAGE_NAME + " compile time: " + time + "ms"
        outputMessages(true)
        if (BENCHMARK > 0) {
            if (BENCHMARK > 1) {
                str += " (Average of " + String.format("%.3f", time / BENCHMARK.toFloat()) + "ms)"
            }
            println(str)
        } else {
            log(str)
        }
    }

    /**
     * Output the log message from the compiler.
     *
     * @param message The message describing what happened.
     */
    @JvmOverloads
    fun log(message: String, newLine: Boolean = true) {
        log(flags, message, newLine)
    }

    /**
     * Output a warning message from the compiler.
     *
     * @param message The message describing the warning.
     */
    fun warning(message: String) {
        warnings.add("Warning: $message")
    }

    /**
     * Output an error message from the compiler.
     *
     * @param message The message describing the error.
     */
    fun error(message: String) {
        if (!isFlagEnabled(DRY_RUN)) {
            enableFlag(DRY_RUN)
        }
        val error = "Error: $message"
        errors.add(error)
    }

    /**
     * Get whether or not the compilation has accumulated any errors.
     *
     * @return Whether or not there are any errors currently.
     */
    fun containsErrors(): Boolean {
        return errors.size > 0
    }

    /**
     * Add the given external import location to be added to the
     * compilation list.
     *
     * @param file The File that is importing the location.
     * @param location The location that is being imported.
     */
    fun addExternalImport(file: FileDeclaration?, location: String) {
        if (!StringUtils.containsString(location, *FileDeclaration.DEFAULT_IMPORTS)) {
//			location = file.getFile().getParent() + "/" + location;
            var absoluteLocation: String? = location.substring(0, location.length - 1) + target
            absoluteLocation = FileUtils.findFileLocation(absoluteLocation, includeDirectories.toTypedArray())
            if (absoluteLocation != null && !StringUtils.containsString(externalImports, absoluteLocation)) {
                externalImports.add(absoluteLocation)
                externalIncludes.add(location)
            }
        }
    }

    private fun parseInitialArguments(args: Array<String>) {
        // Start off the lastInput index to -1 because it will start
        // checking for (index - 1).
        // (index starts at 0, therefore 0 - 1 = -1)
        var lastInput = -1
        var skip = 0
        for (i in args.indices) {
            if (args[i]!!.startsWith("\"")) {
                args[i] = StringUtils.removeSurroundingQuotes(args[i])
            }
            if (skip > 0) {
                skip--
                continue
            }

            // Lowercase the argument for easier non-case-sensitive String
            // matching.
            val arg = args[i]!!.lowercase(Locale.getDefault())
            if (arg.length <= 0) {
                if (lastInput == i - 1) {
                    lastInput = i
                }
                continue
            }
            if (arg == "-install-dir") {
                validateArgumentSize(args, i + 1, arg)
                installDirectoryArg = args[i + 1]
                installDirectory = File(installDirectoryArg)
                skip = 1
            } else if (arg.lowercase(Locale.getDefault()) == "-target") {
                validateArgumentSize(args, i + 1, args[i])
                target = args[i + 1]!!.lowercase(Locale.getDefault())
                skip = 1
            } else if (arg.lowercase(Locale.getDefault()) == "-std-path") {
                validateArgumentSize(args, i + 1, args[i])
                standardLibraryPath = args[i + 1]
                skip = 1
            }
        }
    }

    /**
     * Parse the arguments passed to the compiler.
     *
     * @param args The list of arguments to parse.
     */
    private fun parseArguments(args: Array<String>) {
        var lastInput = -1
        var skip = 0
        startEngines()

        // Iterate through all of the arguments.
        for (i in args.indices) {
            if (skip > 0) {
                skip--
                continue
            }

            // Lowercase the argument for easier non-case-sensitive String
            // matching.
            val arg = args[i]!!.lowercase(Locale.getDefault())
            if (arg.length <= 0) {
                if (lastInput == i - 1) {
                    lastInput = i
                }
                continue
            }
            if (compileEngine!!.checkArgument(arg, args, i)) {
            } else if (arg == "-o") {
                validateArgumentSize(args, postArgs.size + i + 1, "-o", "Expected output file name after argument '-o'")
                outputFile = File(args[i + 1])
                skip = 1
            } else if (arg == "-dir") {
                validateArgumentSize(args, i + 1, arg)
                includeDirectories.add(FileUtils.formatPath(args[i + 1]))
                skip = 1
            } else if (arg == "-install-dir") {
                validateArgumentSize(args, i + 1, arg)
                skip = 1
            } else if (arg == "-std-path") {
                validateArgumentSize(args, i + 1, arg)
                skip = 1
            } else if (arg == "-run") {
                enableFlag(RUNTIME)
            } else if (arg == "-nogc") {
                enableFlag(NO_GC)
            } else if (arg == "-no-c-output") {
                enableFlag(NO_C_OUTPUT)
            } else if (arg == "-no-optimize") {
                enableFlag(NO_OPTIMIZE)
            } else if (arg == "-target") {
                skip = 1
            } else if (arg == "-main") {
                validateArgumentSize(args, i + 1, arg)
                codeGeneratorEngine!!.mainClass = args[i + 1]
                skip = 1
            } else if (arg == "-csource") {
                enableFlag(CSOURCE)
            } else if (arg == "-formatc") {
                enableFlag(FORMATC)
            } else if (arg == "-verbose" || arg == "-v") {
                if (BENCHMARK <= 0) {
                    enableFlag(VERBOSE)
                }
            } else if (arg == "-single-file") {
                enableFlag(SINGLE_FILE)
            } else if (arg == "-single-thread") {
                enableFlag(SINGLE_THREAD)
            } else if (arg == "-dry") {
                enableFlag(DRY_RUN)
            } else if (arg == "-quotes" || arg == "-qp") {
                enableFlag(QUOTE_PATHS)
            } else if (arg == "-cargs") {
                enableFlag(C_ARGS)
            } else if (arg == "-small") {
                enableFlag(SMALL_BIN)
            } else if (arg == "-library") {
                enableFlag(LIBRARY)
            } else if (arg == "-l") {
                validateArgumentSize(args, i + 1, arg)
                libraries.add(args[i + 1])
                val file = File(args[i + 1]).absoluteFile
                val list = ArrayList<File>()
                list.add(file)
                libraryFiles[file] = list
                skip = 1
            } else if (arg == "-output-directory" || arg == "-d") {
                validateArgumentSize(args, i + 1, arg)
                try {
                    outputDirectory = File(args[i + 1]).canonicalFile
                    outputDirectory!!.mkdirs()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
                skip = 1
            } else if (arg == "-package-output-directory") {
                validateArgumentSize(args, i + 2, arg)
                outputDirectories[args[i + 1]] = args[i + 2]
                skip = 2
            } else {
                // If the argument is one of the first arguments passed
                // (If it is one of the sources to compile)
                if (lastInput == i - 1) {
                    val file = File(args[i]).absoluteFile
                    inputFiles.add(file)
                    lastInput = i
                } else if (i == args.size - 1) {
                    outputFile = File(args[i])
                } else {
                    error("Unknown argument '" + args[i] + "'")
                    completed(false)
                }
            }
        }
        if (outputFile == null) {
            System.err.println("You must specify an output file using the -o argument. e.g. 'novac Test.nova -o Test'")
            System.exit(1)
        }
        validateFiles(inputFiles)
        for ((_, value) in libraryFiles) {
            validateFiles(value)
        }
    }

    fun getLibrary(file: File): File? {
        for ((key, value) in libraryFiles) {
            if (value.contains(file)) {
                return key
            }
        }
        return null
    }

    private fun validateArgumentSize(args: Array<String>, size: Int, arg: String?, message: String? = null) {
        if (args.size <= size) {
            System.err.println(message ?: "Invalid arguments passed")
            System.exit(1)
        }
    }

    private fun addFilesFromDirectory(files: ArrayList<File>, directory: File) {
        if (!directory.name.equals(".novalib", ignoreCase = true)) {
            Arrays.stream(directory.listFiles()).filter { x: File -> x.name.lowercase(Locale.getDefault()).endsWith(".nova") }
                    .filter { x: File ->
                        files.stream().noneMatch { f: File ->
                            try {
                                return@noneMatch f.canonicalPath == x.canonicalPath
                            } catch (e: IOException) {
                                throw RuntimeException(e)
                            }
                        }
                    }
                    .forEach { x: File -> files.add(x) }
            Arrays.stream(directory.listFiles())
                    .filter { x: File -> x.isDirectory }
                    .forEach { x: File -> addFilesFromDirectory(files, x) }
        }
    }

    /**
     * Validate that the input files end with .fat. If any of them
     * do not, an error will be output. Also outputs an error if the
     * input file does not exist or is a directory.
     */
    private fun validateFiles(files: ArrayList<File>) {
        var working = true
        for (i in files.indices) {
            val f = files[i]
            if (!f.isFile) {
                if (f.isDirectory) {
                    addFilesFromDirectory(files, f)
                    includeDirectories.add(f.parentFile.absolutePath)
                } else {
                    working = false
                    error("Input file '" + f.absolutePath + "' is not a file.")
                }
            } else if (!f.name.lowercase(Locale.getDefault()).endsWith(".nova")) {
                working = false
                error("Input file '" + f.name + "' must have an extension of .nova")
            } else if (!f.exists()) {
                working = false
                error("Input file '" + f.absolutePath + "' does not exist.")
            }
        }
        for (i in files.indices.reversed()) {
            if (files[i].isDirectory) {
                files.removeAt(i)
            }
        }
        if (!working) {
            startTimer()
            stopTimer()
            outputMessages(true)
            completed(false)
        }
    }

    fun pushFlags() {
        flagsStack.push(flags)
    }

    fun popFlags() {
        flags = flagsStack.pop()
    }

    /**
     * Enable the specified flag.
     *
     * @param flag The flag to set enable.
     */
    fun enableFlag(flag: Long) {
        flags = flags or flag
    }

    /**
     * Disable the specified flag.
     *
     * @param flag The flag to disable.
     */
    fun disableFlag(flag: Long) {
        flags = flags and flag.inv()
    }

    /**
     * Check if the specific flag is enabled for the compiler.
     *
     * @param flag The flag to check if is enabled.
     * @return Whether or not the flag is enabled.
     */
    fun isFlagEnabled(flag: Long): Boolean {
        return isFlagEnabled(flags, flag)
    }

    /**
     * Start the compilation timer.
     */
    private fun startTimer() {
        startTime = System.currentTimeMillis()
    }

    /**
     * Stop the compilation timer.
     */
    private fun stopTimer() {
        endTime = System.currentTimeMillis()
    }

    /**
     * Get the time the compiler took to compile the input files.
     *
     * @return The time in milliseconds it took to compile.
     */
    private val compileTime: Long
        private get() = endTime - startTime

    fun setTestClasses(testClasses: Boolean) {
        this.testClasses = testClasses
    }

    /**
     * Output all of the stored errors, warnings, and other messages.
     */
    private fun outputMessages(outputResult: Boolean = true) {
        outputMessages(true, outputResult)
    }

    private fun outputMessages(success: Boolean, outputResult: Boolean) {
        outputMessages(success, warnings.size, errors.size, outputResult)
    }

    private fun outputMessages(success: Boolean, warningCount: Int, errorCount: Int, outputResult: Boolean = true) {
        for (s in messages) {
            println(s)
        }
        for (s in warnings) {
            println(s)
        }
        for (s in errors) {
            System.err.println(s)
        }
        if (outputResult) {
            var status = if (success) "succeeded" else "failed"
            var errorsText = ""
            var warningsText = ""
            if (warningCount > 0) {
                warningsText = " " + warningCount + " warning" + if (warningCount > 1) "s" else ""
            }
            if (errorCount > 0) {
                status = "failed"
                errorsText = " " + errorCount + " error" + if (errorCount > 1) "s" else ""
            }
            val with = if (errorsText.length + warningsText.length > 0) " with" else ""
            val message = "Compilation $status$with$errorsText$warningsText"
            if (status == "succeeded") {
                log(message)
            } else {
                System.err.println(message)
            }
        }
        messages = ArrayList()
        warnings = ArrayList()
        errors = ArrayList()
    }

    /**
     * Method called whenever the compilation sequence has completed.
     */
    @JvmOverloads
    fun completed(success: Boolean, warningCount: Int = warnings.size, errorCount: Int = errors.size) {
        stopTimer()
        log("Compile time: " + compileTime + "ms")
        outputMessages(success, warningCount, errorCount)
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
            System.exit(if (success) 0 else 1)
        }
    }

    /**
     * Call the test case methods for all of the classes to make sure they
     * are working correctly.
     */
    private fun testClasses() {
        if (testClasses) {
            println("Testing classes")
            val context = TestContext()
            var error: String? = null
            error = ArgumentList.test(context)
            if (error == null) {
                error = Assignment.test(context)
                if (error == null) {
                    error = BinaryOperation.test(context)
                    if (error == null) {
                        error = Cast.test(context)
                        if (error == null) {
                            error = ClassDeclaration.test(context)
                            if (error == null) {
                                error = Closure.test(context)
                                if (error == null) {
                                    error = ClosureDeclaration.test(context)
                                    if (error == null) {
                                        error = Condition.test(context)
                                        if (error == null) {
                                            error = Constructor.test(context)
                                            if (error == null) {
                                                error = Destructor.test(context)
                                                if (error == null) {
                                                    error = Dimensions.test(context)
                                                    if (error == null) {
                                                        error = ElseStatement.test(context)
                                                        if (error == null) {
                                                            error = ExternalMethodDeclaration.test(context)
                                                            if (error == null) {
                                                                error = ExternalType.test(context)
                                                                if (error == null) {
                                                                    error = ExternalTypeList.test(context)
                                                                    if (error == null) {
                                                                        error = FileDeclaration.test(context)
                                                                        if (error == null) {
                                                                            error = ForLoop.test(context)
                                                                            if (error == null) {
                                                                                error = Identifier.test(context)
                                                                                if (error == null) {
                                                                                    error = IfStatement.test(context)
                                                                                    if (error == null) {
                                                                                        error = IIdentifier.test(context)
                                                                                        if (error == null) {
                                                                                            error = Import.test(context)
                                                                                            if (error == null) {
                                                                                                error = ImportList.test(context)
                                                                                                if (error == null) {
                                                                                                    error = InstanceDeclaration.test(context)
                                                                                                    if (error == null) {
                                                                                                        error = Instantiation.test(context)
                                                                                                        if (error == null) {
                                                                                                            error = IValue.test(context)
                                                                                                            if (error == null) {
                                                                                                                error = Literal.test(context)
                                                                                                                if (error == null) {
                                                                                                                    error = LocalDeclaration.test(context)
                                                                                                                    if (error == null) {
                                                                                                                        error = Loop.test(context)
                                                                                                                        if (error == null) {
                                                                                                                            error = LoopInitialization.test(context)
                                                                                                                            if (error == null) {
                                                                                                                                error = LoopUpdate.test(context)
                                                                                                                                if (error == null) {
                                                                                                                                    error = MethodCall.test(context)
                                                                                                                                    if (error == null) {
                                                                                                                                        error = MethodCallArgumentList.test(context)
                                                                                                                                        if (error == null) {
                                                                                                                                            error = MethodDeclaration.test(context)
                                                                                                                                            if (error == null) {
                                                                                                                                                error = MethodList.test(context)
                                                                                                                                                if (error == null) {
                                                                                                                                                    error = Node.test(context)
                                                                                                                                                    if (error == null) {
                                                                                                                                                        error = Operator.test(context)
                                                                                                                                                        if (error == null) {
                                                                                                                                                            error = Parameter.test(context)
                                                                                                                                                            if (error == null) {
                                                                                                                                                                error = ParameterList.test(context)
                                                                                                                                                                if (error == null) {
                                                                                                                                                                    error = Priority.test(context)
                                                                                                                                                                    if (error == null) {
                                                                                                                                                                        error = Program.test(context)
                                                                                                                                                                        if (error == null) {
                                                                                                                                                                            error = Return.test(context)
                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                error = Scope.test(context)
                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                    error = SyntaxTree.test(context)
                                                                                                                                                                                    if (error == null) {
                                                                                                                                                                                        error = TreeGenerator.test(context)
                                                                                                                                                                                        if (error == null) {
                                                                                                                                                                                            error = UnaryOperation.test(context)
                                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                                error = Until.test(context)
                                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                                    error = Value.test(context)
                                                                                                                                                                                                    if (error == null) {
                                                                                                                                                                                                        error = VTable.test(context)
                                                                                                                                                                                                        if (error == null) {
                                                                                                                                                                                                            error = GenericCompatible.test(context)
                                                                                                                                                                                                            if (error == null) {
                                                                                                                                                                                                                error = WhileLoop.test(context)
                                                                                                                                                                                                                if (error == null) {
                                                                                                                                                                                                                    error = Match.test(context)
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
                System.err.println("Pre-compilation class tests failed:")
                System.err.println(error)
                completed(true)
            }
            println("Done testing classes")
        }
    }

    companion object {
        @JvmStatic
        var OS = 0
        @JvmStatic
        var EXECUTABLE_EXTENSION: String? = null
        @JvmStatic
        var DYNAMIC_LIB_EXT: String? = null
        const val ANDROID_DEBUG = false
        const val DEBUG = false

        //	public static final boolean	DEBUG         = true;
        //	public static final boolean USE_INSTALLED_TARGET = false;
        const val USE_INSTALLED_TARGET = true

        //	public static final boolean USE_INSTALLED_STDLIB = false;
        const val USE_INSTALLED_STDLIB = true

        // Set to 0 to not benchmark.
        const val BENCHMARK = 0
        const val SINGLE_THREAD = 0x1000000000000000L
        const val SMALL_BIN = 0x0100000000000000L
        const val NO_GC = 0x0010000000000000L
        const val LIBRARY = 0x0001000000000000L
        const val RUNTIME = 0x0000100000000000L
        const val C_ARGS = 0x0000010000000000L

        //////////////////////////////////////////////
        const val SINGLE_FILE = 0x0000001000000000L
        const val DRY_RUN = 0x0000000100000000L
        const val VERBOSE = 0x0000000010000000L
        const val FORMATC = 0x0000000001000000L
        const val CSOURCE = 0x0000000000100000L
        const val NO_C_OUTPUT = 0x0000000000010000L
        const val NO_OPTIMIZE = 0x0000000000001000L
        const val QUOTE_PATHS = 0x0000000000000100L
        const val WINDOWS = 1
        const val MACOSX = 2
        const val LINUX = 3
        const val LANGUAGE_NAME = "Nova"
        const val VERSION = "v0.3.8"

        /**
         * Find out which operating system the compiler is running on.
         */
        init {
            val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
            if (osName.startsWith("win")) {
                OS = WINDOWS
                EXECUTABLE_EXTENSION = ".exe"
                DYNAMIC_LIB_EXT = ".dll"
            } else if (osName.startsWith("mac")) {
                OS = MACOSX
                EXECUTABLE_EXTENSION = ""
                DYNAMIC_LIB_EXT = ".dylib"
            } else if (osName.startsWith("lin")) {
                OS = LINUX
                EXECUTABLE_EXTENSION = ""
                DYNAMIC_LIB_EXT = ".so"
            } else {
                OS = 0
                EXECUTABLE_EXTENSION = ""
                DYNAMIC_LIB_EXT = ""
            }
        }

        /**
         * Method called whenever the compiler is invoked. Supplies the
         * needed information for compiling the given files.
         *
         * @param args The String array containing the locations of the files
         * to compile, as well as other compiler arguments.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val nova = Nova(args)

//		if (args.length > 0)
//		{
//			args = Arrays.copyOfRange(args, 1, args.length);
//		}
            nova.compile(args, true)
            nova.compileEngine!!.compile()
            nova.outputMessages(false)
        }

        fun unsupportedOs() {
            System.err.println("Unsupported operating system.")
            System.exit(1)
        }

        /**
         * Used to represent a debugging breakpoint...
         *
         * @param condition Whether or not to break.
         */
		@JvmStatic
		fun debuggingBreakpoint(condition: Boolean): Boolean {
            if (condition) {
                println("Breakpoint")
            }
            return condition
        }

        /**
         * Output the log message from the compiler.
         *
         * @param flags The flags that verify whether the compiler is verbose.
         * @param message The message describing what happened.
         */
        @JvmOverloads
        fun log(flags: Long, message: String, newLine: Boolean = true) {
            if (isFlagEnabled(flags, VERBOSE)) {
                print(message + if (newLine) '\n' else "")
            }
        }

        /**
         * Check if the specific flag is enabled for the given set of flags.
         *
         * @param flags The flags to verify the flag with.
         * @param flag The flag to check if is enabled.
         * @return Whether or not the flag is enabled.
         */
        fun isFlagEnabled(flags: Long, flag: Long): Boolean {
            return flags and flag != 0L
        }

        /**
         * Get the working directory of the compiler.
         *
         * @return The working directory of the compiler.
         */
        private val workingDirectoryPath: String
            private get() = if (ANDROID_DEBUG) {
                "/mnt/sdcard/AppProjects/Nova/"
            } else System.getProperty("user.dir").replace('\\', '/') + "/"

        @JvmStatic
		fun generateTemporaryController(args: Array<String>): Nova {
            val controller = Nova(args)
            controller.isTesting = true
            controller.setTestClasses(false)
            controller.compile(arrayOf(), false)
            return controller
        }
    }
}