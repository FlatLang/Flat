package flat.js.engines;

import flat.CodeGeneratorEngine;
import flat.Flat;
import flat.error.SyntaxMessage;
import flat.tree.FileDeclaration;
import flat.tree.MethodDeclaration;
import flat.util.FileUtils;
import flat.js.nodewriters.Writer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static flat.Flat.LIBRARY;
import static flat.js.nodewriters.Writer.getWriter;

public class JSCodeGeneratorEngine extends CodeGeneratorEngine {
    public JSCodeGeneratorEngine(Flat controller) {
        super(controller);


    }

    /**
     * Generate the C Source and Header output from the data contained within the syntax tree.
     */
    public void generateOutput() {
        if (controller.isFlagEnabled(Flat.SINGLE_FILE)) {
            if (controller.outputFile == null) {
                controller.error(
                    "You must specify the output file name either using the -o argument, or as the last argument.");

                return;
            }

            String extension = FileUtils.getFileExtension(controller.outputFile.getName());

            if (extension != null && !(extension.equalsIgnoreCase("js")
                || extension.equalsIgnoreCase("mjs") || extension.equalsIgnoreCase("ts"))) {
                controller.error("Invalid output file extension '" + extension
                    + "'. The extension must be js, mjs, or ts");

                return;
            }

            try {
                String filename =
                    FileUtils.removeFileExtension(controller.outputFile.getName()) + "."
                        + (extension != null ? extension : "js");
                File file = new File(controller.outputFile.getParent() + "/" + filename);
                controller.outputFile.getParentFile().mkdirs();
                controller.log("Writing to file " + file.getCanonicalPath(), true);
                writeFile(filename, controller.outputFile.getParentFile(),
                    formatText(Writer.getWriter(tree.getRoot()).write().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (((JSCompileEngine) controller.compileEngine).localScope) {
                controller.warning(
                    "Ignoring -local-scope argument because not compiling using -single-file argument.");
            }

            tree.getRoot().forEachVisibleListChild(file -> {
                try {
                    File outputDir = getOutputDirectory(file);

                    new File(outputDir, file.getPackage().getLocation()).mkdirs();

                    StringBuilder prototypeAssignments = new StringBuilder();

                    Arrays.stream(file.getClassDeclarations())
                        .forEach((c) -> {
                            Writer.getWriter(c)
                                .writeExtensionPrototypeAssignments(prototypeAssignments);

                            prototypeAssignments.append("\n\n");
                        });

                    writeFile(file.getPackage().getLocation() + "/" + file.getName() + ".js",
                        outputDir, formatText(Writer.getWriter(file).write().toString() + "\n\n"
                            + prototypeAssignments));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void formatOutput() {

    }

    public void writeFiles() {

    }

    /**
     * Insert the main method into the correct file. Also set up the initialization for the program
     * within the main method.
     */
    public void insertMainMethod() {
        MethodDeclaration mainMethod = tree.getMainMethod(mainClass);

        if (mainMethod == null) {
            if (!controller.isFlagEnabled(LIBRARY)) {
                if (mainClass != null) {
                    SyntaxMessage.error("No main method found in class '" + mainClass + "'",
                        controller);
                } else {
                    SyntaxMessage.error("No main method found in program", controller);
                }

                controller.completed(true);
            }

            return;
        }

        FileDeclaration fileDeclaration = mainMethod.getFileDeclaration();

        if (mainMethod != null) {

        }
    }
}
