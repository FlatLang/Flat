package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.annotations.*;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.lambda.LambdaExpression;
import flat.tree.match.Match;
import flat.tree.variables.FieldDeclaration;
import flat.util.*;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Class that is used to generate syntax tree data for a given file and source data.
 *
 * @author Braden Steffaniak
 * @since v0.2.1 Apr 29, 2014 at 8:04:48 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class TreeGenerator implements Runnable {
    private boolean skipNextStatement;

    private ExpectCompileErrorAnnotation expectCompileError;

    private int statementStartIndex, statementEndIndex, oldStatementStartIndex;
    private int lineNumber;

    private Matcher statementStartMatcher;

    private String source;

    private File file;
    private FileDeclaration fileDeclaration;

    private SyntaxTree tree;

    private Flat controller;

    private Stack<Node> parentStack;
    private Stack<Node> pendingScopeFragment;

    private static final char EITHER_STATEMENT_END_CHARS[] = new char[] {'\n', ';', '{', '}'};

    /**
     * Create a tree generator instance with the given filename and source data.
     *
     * @param file The file to generate the tree for.
     * @param source The source within the file to generate the tree for.
     */
    public TreeGenerator(File file, String source, SyntaxTree tree) {
        this.file = file;
        this.source = source;
        this.tree = tree;

        this.controller = tree.getRoot().getController();

        statementStartMatcher = Patterns.STATEMENT_START.matcher(source);

        statementStartIndex = 0;
        statementEndIndex = 0;
    }

    /**
     * Initialize the data back to default values before any traversing of the code is done.
     *
     * @param node
     * @param offset
     */
    private void init(Node node, int offset) {
        parentStack = new Stack<Node>();
        pendingScopeFragment = new Stack<Node>();

        parentStack.push(node);

        statementStartMatcher.reset();
        statementStartMatcher.find();

        statementStartIndex = offset;
        statementEndIndex = offset;
        oldStatementStartIndex = 0;
        lineNumber = 0;

        updateLineNumber(node.getLocationIn().getEnd(), offset);
    }

    /**
     * Calculate the horizontal offset from which the statement starts at.
     *
     * @param statementStart The index of the first character of the statement within the source
     *        code.
     * @return The horizontal offset of the statement.
     */
    private int calculateOffset(int statementStart) {
        for (int i = statementStart - 1; i >= 0; i--) {
            if (source.charAt(i) == '\n') {
                return statementStart - i - 1;
            }
        }

        return 0;
    }

    /**
     * The method that is used to actually do the act of generating the tree data.
     */
    public void run() {
        int phase = tree.getPhase();

        if (phase == SyntaxTree.PHASE_CLASS_DECLARATION) {
            phase1(file);
        } else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            phase2(file);
        } else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            phase3(file);
        }

        if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
            controller.processStep(10);
        } else if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            controller.processStep(tree.phaseInstanceDeclarationsWeight);
        } else {
            controller.processStep(1);
        }
    }

    /**
     * Get the File instance that this generator is generating from.
     *
     * @return The File instance.
     */
    public File getFile() {
        return file;
    }

    /**
     * Generate the syntax tree nodes for all of the class nodes and import nodes.
     *
     * @param file The file that is generating the syntax tree.
     */
    private void phase1(File file) {
        Location location = new Location(1, 0, 0, 0);

        fileDeclaration = new FileDeclaration(tree.getRoot(), location, file);
        fileDeclaration.setSource(source);

        if (fileDeclaration.isExcludedExternalFile(controller.targetFileExtensions)) {
            return;
        }

        controller.log("Phase one for '" + fileDeclaration.getName() + "'...");

        if (source.trim().length() > 0) {
            tree.addFile(fileDeclaration);

            traverseCode(fileDeclaration, 0, SyntaxTree.FIRST_PASS_CLASSES, true);
        }
    }

    /**
     * Generate the syntax tree nodes for all of the field nodes and method nodes.
     *
     * @param file The file that is generating the syntax tree.
     */
    private void phase2(File file) {
        ClassDeclaration[] classes = fileDeclaration.getClassDeclarations();

        for (ClassDeclaration node : classes) {
            if (node == null || !node.isUserMade()) {
                continue;
            }

            controller.log("Phase two for '" + node.getClassLocation() + "'...");

            // Finds the starting scope '{'
            int startingIndex =
                StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
            // Finds the ending scope '}'
            int endingIndex = StringUtils.findEndingMatch(source, startingIndex, '{', '}');

            int contentStart = StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
            int contentEnd =
                StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;

            // If there is no content to decode.
            if (contentStart >= contentEnd) {
                continue;
            }

            traverseCode(node, contentStart, SyntaxTree.SECOND_PASS_CLASSES, true);

            decodeFields(node);
            decodeInnerClasses(node);

            if (node.arrayBracketOverload != null) {
                decodeScopeContentsNode(node.arrayBracketOverload, false,
                    SyntaxTree.ARRAY_BRACKET_OVERLOAD_DECODE, true);
            }
        }
    }

    private void decodeFields(ClassDeclaration node) {
        decodeScopeContents(node.getFieldList().getPrivateFieldList(), false,
            SyntaxTree.FIELD_SCOPE_CHILD_DECODE, true);
        decodeScopeContents(node.getFieldList().getPrivateStaticFieldList(), false,
            SyntaxTree.FIELD_SCOPE_CHILD_DECODE, true);
        decodeScopeContents(node.getFieldList().getPublicFieldList(), false,
            SyntaxTree.FIELD_SCOPE_CHILD_DECODE, true);
        decodeScopeContents(node.getFieldList().getPublicStaticFieldList(), false,
            SyntaxTree.FIELD_SCOPE_CHILD_DECODE, true);
    }

    private void decodeInnerClasses(ClassDeclaration node) {
        decodeScopeContents(node.getInnerClasses(), false, SyntaxTree.SECOND_PASS_CLASSES, true);

        for (ClassDeclaration inner : node.getInnerClasses()) {
            decodeFields(inner);
            decodeInnerClasses(inner);
        }
    }

    /**
     * Generate the syntax tree nodes for all of the scope contents.
     *
     * @param file The file that is generating the syntax tree.
     */
    private void phase3(File file) {
        for (ClassDeclaration classDeclaration : fileDeclaration.getClassDeclarations()) {
            if (classDeclaration == null || !classDeclaration.isUserMade()) {
                continue;
            }

            controller.log("Phase three for '" + classDeclaration.getClassLocation() + "'...");

            boolean requireScope = !(classDeclaration instanceof Trait);

            decodeScopeContents(classDeclaration.getPropertyMethodList(), requireScope);
            decodeScopeContents(classDeclaration.getMethodList(), requireScope);
            decodeScopeContents(classDeclaration.getConstructorList(), requireScope);
            decodeScopeContents(classDeclaration.getDestructorList(), requireScope);
            decodeScopeContents(classDeclaration.getStaticBlockList(), requireScope);
        }
    }

    /**
     * Decode all of the Scope's contents.
     */
    private void decodeScopeContents(List scopeAncestors, boolean requiresScope) {
        decodeScopeContents(scopeAncestors, requiresScope, null, false);
    }

    /**
     * Decode all of the Scope's contents.
     */
    private void decodeScopeContents(List scopeAncestors, boolean requiresScope,
        Class<?>[] searchTypes, boolean skipScopes) {
        for (int i = 0; i < scopeAncestors.getNumChildren(); i++) {
            decodeScopeContentsNode(scopeAncestors.getChild(i), requiresScope, searchTypes,
                skipScopes);
        }

        popParents();
    }

    private void decodeScopeContentsNode(Node node, boolean requiresScope, Class<?>[] searchTypes,
        boolean skipScopes) {
        if (node.getLocationIn().getBounds().isValid() && node.isUserMade()) {
            if ((!requiresScope || node.containsScope())) {
                String oldSource = source;
                Matcher oldMatcher = statementStartMatcher;
                source =
                    node.getOriginalFile() != null ? node.getOriginalFile().getSource() : source;
                statementStartMatcher =
                    node.getOriginalFile() != null ? Patterns.STATEMENT_START.matcher(source)
                        : statementStartMatcher;

                int startingIndex =
                    StringUtils.findNextNonWhitespaceIndex(source, node.getLocationIn().getEnd());
                int endingIndex = StringUtils.findEndingMatch(source, startingIndex, '{', '}');

                int contentStart =
                    StringUtils.findNextNonWhitespaceIndex(source, startingIndex + 1);
                int contentEnd =
                    StringUtils.findNextNonWhitespaceIndex(source, endingIndex - 1, -1) + 1;

                if (startingIndex >= 0 && source.charAt(startingIndex) != '{') {
                    if (requiresScope && node instanceof AbstractMethodDeclaration == false) {
                        if ((node instanceof PropertyMethod == false
                            || !((PropertyMethod) node).isDisabled()) &&
                            (node instanceof ArrayOverloadMethod == false
                                || !((ArrayOverloadMethod) node).isDisabled())) {
                            if (node.getScope() == null
                                || node.getScope().getNumVisibleChildren() <= 0) {
                                SyntaxMessage.error("Scope expected after this statement", node,
                                    false);
                            }
                        }
                    }

                    statementStartMatcher = oldMatcher;
                    source = oldSource;
                    return;
                }

                if (contentStart < contentEnd) {
                    traverseCode(node, contentStart, searchTypes, skipScopes);
                }

                statementStartMatcher = oldMatcher;
                source = oldSource;
            }
        }
    }

    /**
     * Traverse the given source code and generate the tree along with the data and parameters that
     * are given.
     *
     * @param parent The Node to stem all of the following decoded data from.
     * @param offset The character offset within the file's source text overall.
     * @param searchTypes The type of Nodes to try to decode.
     * @param skipScopes Whether or not to skip the scopes of anything that contains a scope. If
     *        true, only decode the header.
     */
    public void traverseCode(Node parent, int offset, Class<?> searchTypes[], boolean skipScopes) {
        init(parent, offset);

        Node currentNode = getNextStatement(null, offset, searchTypes, skipScopes);

        // Decode all of the statements in the source text.
        while (currentNode != null && currentNode.onAfterDecoded()) {
            Node previous = currentNode;

            updateTree(currentNode, skipScopes);

            if (parentStack.isEmpty()) {
                break;
            }

            if (statementEndIndex < source.length()) {
                currentNode = getNextStatement(currentNode, offset, searchTypes, skipScopes);
            } else {
                currentNode = null;
            }

            if (previous instanceof Annotation == false) {
                previous.onNextStatementDecoded(currentNode);
            } else if (currentNode != null) {
                currentNode.addAnnotation((Annotation) previous);
            }
        }
    }

    /**
     * Search for the next statement. If a statement is found, return it in a Node format, if not
     * return null.
     *
     * @param previous The previously decoded node.
     * @param offset The offset in the source file that the statement is.
     * @param searchTypes The type of Nodes to try to decode.
     * @param skipScopes Whether or not to skip the scopes of anything that contains a scope. If
     *        true, only decode the header.
     * @return The Node containing the information, or null if it is not found.
     */
    private Node getNextStatement(Node previous, int offset, Class<?> searchTypes[],
        boolean skipScopes) {
        if (parentStack.peek() instanceof ExternalCodeBlock) {
            ExternalCodeBlock block = (ExternalCodeBlock) parentStack.peek();

            int start = statementStartIndex;
            int brace = SyntaxUtils.findEndingBrace(source, statementStartIndex);

            statementStartIndex = StringUtils.findNextNonWhitespaceIndex(source, brace + 1);

            parentStack.pop();

            block.setContents(
                String.join("\n", source.substring(start, brace).trim().split("\\s*\\n\\s*")));

            if (updateParents(statementStartIndex, statementStartIndex + 1, previous)) {
                statementStartIndex =
                    StringUtils.findNextNonWhitespaceIndex(source, statementStartIndex + 1);

                if (statementStartIndex > 0 && source.charAt(statementStartIndex) == '}') {
                    if (!parentStack.isEmpty()) {
                        parentStack.pop();
                    }

                    statementStartIndex =
                        StringUtils.findNextNonWhitespaceIndex(source, statementStartIndex + 1);
                }
            }

            if (statementStartIndex < 0) {
                return null;
            }
        }

        while ((statementEndIndex = calculateStatementEnd(statementStartIndex)) >= 0
            && !statementStartMatcher.hitEnd() && !parentStack.isEmpty()) {
            char endChar = '\0';

            int newStatementStartIndex = statementStartIndex;

            if (statementEndIndex < source.length()) {
                endChar = source.charAt(statementEndIndex);

                if (statementStartMatcher.find(statementEndIndex + 1)) {
                    newStatementStartIndex = StringUtils.findNextNonWhitespaceIndex(source,
                        statementStartMatcher.start());
                }
            }

            boolean scope = endChar == '{';
            int endBound =
                StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex - 1, -1) + 1;
            int lineOffset = calculateOffset(endBound);
            String statement = source.substring(statementStartIndex, endBound);
            Location location = new Location(lineNumber, lineOffset, statementStartIndex, endBound);

            adjustLocation(previous, location);

            if (statement.length() == 0) {
                return null;
            }

            Node node = null;

            if (expectCompileError != null) {
                controller.setExpectingCompileError(true);
                boolean hitExpected = false;
                controller.pushFlags();

                try {
                    node = decodeStatementAndCheck(statement, location, scope, searchTypes,
                        skipScopes, false);

                    if (node != null) {
                        node.validate(tree.getPhase());
                    }
                } catch (SyntaxErrorException e) {
                    if (expectCompileError.types.stream()
                        .anyMatch(x -> x.isAssignableFrom(e.getClass()))) {
                        hitExpected = true;
                    }
                }

                if (!hitExpected) {
                    SyntaxMessage
                        .error(
                            "Expected '" + String.join(", ",
                                expectCompileError.types.stream().map(Class::getSimpleName)
                                    .collect(Collectors.toList()))
                                + "'",
                            expectCompileError, location);
                } else {
                    ArrayList errors = tree.getRoot().getProgram().getController().errors;

                    errors.remove(errors.size() - 1);

                    controller.popFlags();
                }

                expectCompileError = null;
                controller.setExpectingCompileError(false);
            } else {
                node = decodeStatementAndCheck(statement, location, scope, searchTypes, skipScopes);
            }

            ArrayList<ModifierAnnotation> modifiers = new ArrayList<>();

            while (node instanceof Annotation) {
                Annotation a = (Annotation) node;

                skipNextStatement = skipNextStatement ||
                    (a instanceof TargetAnnotation && !((TargetAnnotation) a).currentTarget()) ||
                    (a instanceof TargetRuntimeAnnotation
                        && !((TargetRuntimeAnnotation) a).currentTargetRuntime());

                statement = Annotation.getFragment(statement);


                if (a instanceof ExpectCompileErrorAnnotation) {
                    expectCompileError = (ExpectCompileErrorAnnotation) a;
                }

                if (statement.length() > 0) {
                    boolean skipped = skipNextStatement;

                    a.getFileDeclaration().addPendingAnnotation(a);

                    node = decodeStatementAndCheck(statement, location, scope, searchTypes,
                        skipScopes);

                    a.getFileDeclaration().removePendingAnnotation(a);

                    if (!skipped && node != null) {
                        if (a instanceof ModifierAnnotation) {
                            modifiers.add((ModifierAnnotation) a);
                        } else {
                            node.addAnnotation(a);
                        }
                        // a.onNextStatementDecoded(node);
                    }
                } else {
                    break;
                }
            }

            if (node != null) {
                for (ModifierAnnotation mod : modifiers) {
                    mod.apply(node, mod.getAliasUsed());
                }
            }

            checkPendingScopeFragment(node);
            checkScopeFragment(node, endChar, scope);

            updateLineNumber(statementStartIndex, newStatementStartIndex);

            oldStatementStartIndex = statementStartIndex;
            statementStartIndex =
                newStatementStartIndex > statementStartIndex ? newStatementStartIndex
                    : statementStartIndex;

            if (node != null) {
                return node.followedByScope(scope);
            } else if (skipScopes) {
                // statementStartIndex = oldStatementStartIndex;
            }

            updateParents(node);

            if (statementEndIndex >= source.length()) {
                return node;
            }
        }

        return null;
    }

    /**
     * Calculate the index in which the next statement end is located at, after the given
     * currentEnd.
     *
     * @param currentEnd The index to search after.
     * @return The new statement end index.
     */
    private int calculateStatementEnd(int currentEnd) {
        currentEnd =
            SyntaxUtils.findCharInBaseScope(source, EITHER_STATEMENT_END_CHARS, currentEnd);

        if (currentEnd < 0) {
            // if (StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex + 1) < 0)
            // {
            // return -1;
            // }

            return source.length();
        } else if (source.charAt(currentEnd) == ';') {
            return currentEnd;
        }

        int prevCharIndex = StringUtils.findNextNonWhitespaceIndex(source, currentEnd - 1, -1);
        int nextCharIndex = StringUtils.findNextNonWhitespaceIndex(source, currentEnd + 1);

        if (fileDeclaration != null
            && fileDeclaration.getProgram().getPhase() == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            if (prevCharIndex > 3 && source.charAt(currentEnd) == '{'
                && source.substring(prevCharIndex - 1, prevCharIndex + 1).equals("=>")) {
                int end = SyntaxUtils.findEndingBrace(source, currentEnd + 1) + 1;

                if (end > currentEnd) {
                    end = SyntaxUtils.findCharInBaseScope(source, EITHER_STATEMENT_END_CHARS, end);

                    return end < 0 ? source.length() : end;
                }
            }
        }

        return calculateReturnValue(currentEnd, nextCharIndex, prevCharIndex);
    }

    /**
     * Calculate the statement end's return value.
     *
     * @param currentEnd The current end value of the statement.
     * @param nextCharIndex The next char to the right of the current end.
     * @param prevCharIndex The next char to the left of the current end.
     * @return The return value of the statement end.
     */
    private int calculateReturnValue(int currentEnd, int nextCharIndex, int prevCharIndex) {
        if (nextCharIndex < 0) {
            return -1;
        }

        if (source.charAt(nextCharIndex) == '{') {
            return nextCharIndex;
        } else if (checkStatementContinuation(prevCharIndex, nextCharIndex)) {
            return calculateStatementEnd(currentEnd + 1);
        }

        return currentEnd;
    }

    /**
     * Check whether or not to continue the search for the end of the statement.
     *
     * @param nextCharIndex The next char to the right of the current end.
     * @param prevCharIndex The next char to the left of the current end.
     * @return Whether or not to continue the search for the end of the statement.
     */
    private boolean checkStatementContinuation(int prevCharIndex, int nextCharIndex) {
        Bounds prevWordBounds = StringUtils.findNextWordBounds(source, prevCharIndex, -1);

        int nextNextCharIndex = StringUtils.findNextNonWhitespaceIndex(source,
            StringUtils.findNextWhitespaceIndex(source, nextCharIndex + 1));

        // Whether or not the current statement needs the next line to complete the statement
        boolean pendingCompletion =
            StringUtils.containsChar(source, StringUtils.STMT_PRE_CONT_CHARS, prevCharIndex)
                && !UnaryOperation.containsUnaryOperator(source, prevCharIndex,
                    prevWordBounds.getEnd(), -1);

        if (pendingCompletion
            && (source.charAt(prevCharIndex) + "").equals(GenericTypeArgument.GENERIC_END)) {
            if (source.substring(prevCharIndex - 1, prevCharIndex + 1).equals("=>")) {
                if (!parentStack.isEmpty() && parentStack.peek() instanceof Match) {
                    return false;
                }
            } else if (GenericTypeArgument.searchGenericType(source, prevCharIndex, true) != null) {
                pendingCompletion = false;
            }
        }

        // Whether or not the next statement needs a fragment before it to complete the statement
        boolean requiresCompletion =
            StringUtils.containsChar(source, StringUtils.STMT_POST_CONT_CHARS, nextCharIndex)
                && (!UnaryOperation.containsUnaryOperator(source, nextCharIndex, nextNextCharIndex)
                    || StringUtils.findGroupedSymbols(source, nextCharIndex).equals("-"));

        String prevWord = prevWordBounds.extractString(source);

        if (source.substring(statementStartIndex).startsWith("external")) {
            return false;
        }

        return pendingCompletion ^ requiresCompletion || (prevWord.equals("return")
            && pendingCompletion && parentStack.check().getParentMethod().getType() != null);
    }

    /**
     * Check the given Node to see if it is pending a scope fragment or not. For more information on
     * what a scope fragment is, see {@link Node#decodeScopeFragment(String, Bounds)}
     *
     * @param node The Node to check.
     * @param endChar The character that ends the statement of the given Node.
     * @param scope Whether or not the Node starts a scope.
     */
    private void checkScopeFragment(Node node, char endChar, boolean scope) {
        if (node == null || scope || pendingScopeFragment.check() == node || endChar == ';') {
            return;
        }

        if (node.pendingScopeFragment(null)) {
            pendingScopeFragment.push(node);
        }
    }

    /**
     * Try to decode the statement. If there is a syntax error within the given statement, then the
     * method will try to compensate for error.
     *
     * @param statement The statement to try to decode.
     * @param location The location of the statement in the source file.
     * @param scope Whether or not the statement starts off a scope.
     * @param searchTypes The types of nodes that the statement could possibly be.
     * @param skipScopes Whether or not to skip the scopes of anything that contains a scope. If
     *        true, only decode the header.
     */
    private Node decodeStatementAndCheck(String statement, Location location, boolean scope,
        Class<?> searchTypes[], boolean skipScopes) {
        return decodeStatementAndCheck(statement, location, scope, searchTypes, skipScopes, true);
    }

    private Node decodeStatementAndCheck(String statement, Location location, boolean scope,
        Class<?> searchTypes[], boolean skipScopes, boolean catchException) {
        if (skipNextStatement) {
            skipNextStatement = false;

            if (skipScopes) {
                skipScope();
            }

            return null;
        }

        if (!catchException) {
            return decodeStatement(statement, location, searchTypes);
        }

        try {
            return decodeStatement(statement, location, searchTypes);
        } catch (SyntaxErrorException e) {
            if (!skipScopes && scope && !parentStack.isEmpty()) {
                Node parent = parentStack.peek();

                if (parent.getParentClass(true) == null) {
                    parentStack.push(ClassDeclaration.generateTemporaryClass(parent, location));
                } else if (parent.getParentMethod(true) == null) {
                    parentStack
                        .push(BodyMethodDeclaration.generateTemporaryMethod(parent, location));
                } else {
                    parentStack.push(Scope.generateEmptyScope(parent, location));
                }
            } else if (skipScopes) {
                updateTree(null, skipScopes);
            }
        }

        return null;
    }

    /**
     * Update the tree's parent stack, indices, and line numbers, if necessary.
     *
     * @param node The previously decoded node.
     * @param skipScopes Whether or not to skip the scopes of anything that contains a scope. If
     *        true, only decode the header.
     */
    private void updateTree(Node node, boolean skipScopes) {
        if (node == null) {
            skipScope();

            return;
        }

        if (skipScopes && (node.containsScope() || node instanceof ArrayBracketOverload
            || node instanceof FieldDeclaration || node instanceof ClassDeclaration)) {
            skipScope();
        }

        if (!parentStack.isEmpty() && node instanceof Annotation == false) {
            parentStack.peek().addChild(node);
        }

        if (((statementEndIndex >= 0 && statementEndIndex < source.length() && !skipScopes
            && source.charAt(statementEndIndex) == '{')
            || (pendingScopeFragment.check() == node && node.pendingScopeFragment(null))) ||
            (!skipScopes && node.getDecodedParent() instanceof LambdaExpression)) {
            parentStack.push(node.getDecodedParent());
        }

        updateParents(node);
    }

    /**
     * Skip the current scope that the source has encountered.
     */
    private void skipScope() {
        if (statementEndIndex >= source.length()) {
            return;
        }

        int contentIndex = StringUtils.findNextNonWhitespaceIndex(source, statementEndIndex + 1);

        if (source.charAt(statementEndIndex) != '{') {
            return;
        }

        int endingIndex = StringUtils.findNextNonWhitespaceIndex(source,
            StringUtils.findEndingMatch(source, statementEndIndex, '{', '}') + 1);
        int end = endingIndex;
        int temp = end;

        while (end >= 0 && source.charAt(end) == '}') {
            end = StringUtils.findNextNonWhitespaceIndex(source, end + 1);

            if (end < 0) {
                break;
            }

            temp = end;
        }

        end = temp;

        updateLineNumber(contentIndex, end);

        statementStartIndex = end;

        if (statementStartIndex < 0) {
            statementStartIndex = source.length();
            end = statementStartIndex;
            endingIndex = end;
        }

        oldStatementStartIndex = endingIndex;
        statementEndIndex = end;
    }

    public boolean skipPoppingParent(String arrowBinding) {
        if (arrowBinding != null) {
            int start = SyntaxUtils.findCharInBaseScope(arrowBinding, '{');

            if (start >= 0 && StringUtils.findEndingMatch(arrowBinding, start, '{', '}') > start) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check to see if an ending curly brace has been parsed. If so, pop the last parent off the
     * stack.
     */
    private void updateParents(Node current) {
        updateParents(oldStatementStartIndex, statementStartIndex, current);
    }

    /**
     * Check to see if an ending curly brace has been parsed. If so, pop the last parent off the
     * stack.
     *
     * @param start The index to start the search at.
     * @param statementStart The index to end the search at.
     */
    private boolean updateParents(int start, int statementStart, Node current) {
        if (parentStack.isEmpty()) {
            return false;
        }

        boolean popped = false;

        checkPendingScopeFragment(current);

        if (start >= 0 && start < statementStart
            && (current instanceof BodyMethodDeclaration == false
                || !skipPoppingParent(((BodyMethodDeclaration) current).shorthandAction))) {
            String sub = source.substring(start, statementStart);

            int index = -1;

            while (true) {
                index = SyntaxUtils.findCharInBaseScope(sub, '}', index + 1);

                if (index < 0) {
                    break;
                }

                popped = popParents();

                checkPendingScopeFragment(current);
            }
        }

        return popped;
    }

    public boolean popParents() {
        boolean popped = false;

        if (!parentStack.isEmpty()) {
            Node parent = parentStack.pop();

            while (parent.equals(pendingScopeFragment.check())) {
                pendingScopeFragment.pop();

                parent.onStackPopped(parent);

                parent = parentStack.pop();
            }

            parent.onStackPopped(parent);

            popped = true;
        }

        return popped;
    }

    /**
     * Check whether or not a pending scope fragment has been completed.
     *
     * @return Whether or not a pending scope fragment has been completed.
     */
    private void checkPendingScopeFragment(Node current) {
        if (!pendingScopeFragment.isEmpty()) {
            Node node = pendingScopeFragment.peek();

            if (node == current) {
                current = null;
            }

            if (!node.pendingScopeFragment(current))// && node == parentStack.peek())
            {
                parentStack.pop();
                pendingScopeFragment.pop();

                checkPendingScopeFragment(current);
            }
        }
    }

    /**
     * Decode the specified statement String at the given Location into a Node instance.
     *
     * @param statement The statement String to decode into a Node.
     * @param location The location of the statement in the source text.
     * @param searchTypes The type of Nodes to try to decode.
     * @return The result Node of decoding the statement String.
     */
    private Node decodeStatement(String statement, Location location, Class<?> searchTypes[]) {
        Node parent = parentStack.check();

        if (searchTypes != null) {
            return SyntaxTree.decodeStatement(parent, statement, location, searchTypes);
        } else {
            return SyntaxTree.decodeScopeContents(parent, statement, location, true);
        }
    }

    /**
     * Update the number of new lines that exist between (inclusive) the bounds of [start, end].
     *
     * @param start The starting index to begin the search for new lines at.
     * @param end The ending index to end the search for new lines at.
     */
    private void updateLineNumber(int start, int end) {
        lineNumber += StringUtils.numNewLines(start, end, source);
    }

    /**
     * Adjust the given location to be relative within its parent scope.
     *
     * @param node The previously decoded node.
     * @param location The Location to adjust.
     */
    private void adjustLocation(Node node, Location location) {
        if (node == null) {
            return;
        }

        int phase = tree.getPhase();
        Node scope = null;

        for (int i = 0; i < parentStack.size(); i++) {
            scope = parentStack.peek(i);

            if ((phase == SyntaxTree.PHASE_METHOD_CONTENTS && scope instanceof MethodDeclaration) ||
                (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS && node instanceof PropertyMethod)
                ||
                (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS
                    && scope instanceof ClassDeclaration)
                ||
                (phase == SyntaxTree.PHASE_CLASS_DECLARATION && scope instanceof FileDeclaration)) {
                break;
            }

            Location scopeLoc = scope.getLocationIn();

            location.setLineNumber(location.getLineNumber() - scopeLoc.getLineNumber());
        }
    }

    /**
     * Test the TreeGenerator class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
