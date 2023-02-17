package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * MethodDeclaration extension that represents the declaration of an
 * external method node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ExternalMethodDeclaration extends MethodDeclaration {
    public String alias;

    public static final String PREFIX = "external";

    @Override
    public ObjectReference getObjectReference() {
        return null;
    }

    @Override
    public FlatMethodDeclaration getRootDeclaration() {
        return null;
    }

    /**
     * @see Node#Node(Node, Location)
     */
    public ExternalMethodDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see MethodDeclaration#isVirtual()
     */
    @Override
    public boolean isVirtual() {
        return false;
    }

    /**
     * @see MethodDeclaration#isExternal()
     */
    @Override
    public boolean isExternal() {
        return true;
    }

    /**
     * @see Node#isWithinExternalContext()
     */
    @Override
    public boolean isWithinExternalContext() {
        return true;
    }

    /**
     * @see MethodDeclaration#containsBody()
     */
    @Override
    public boolean containsBody() {
        return false;
    }

    /**
     * Decode the given statement into a ExternalMethodDeclaration instance, if
     * possible. If it is not possible, this method returns null.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>external int getAge(String name, int age)</li>
     * 	<li>external int calculateArea(int width, int height)</li>
     * 	<li>external void doNothing() as pointlessFunction</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  ExternalMethodDeclaration instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes
     *                  wrong.
     * @return The generated node, if it was possible to translated it
     * into a Method.
     */
    public static ExternalMethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        Bounds bounds = StringUtils.findWordBounds(statement, ExternalMethodDeclaration.PREFIX);

        if (bounds.isValid() && bounds.getStart() < statement.indexOf('(')) {
            String methodSignature = findMethodSignature(statement, bounds);

            if (methodSignature != null && methodSignature.length() > 0) {
                ExternalMethodDeclaration n = new ExternalMethodDeclaration(parent, location);

                int start = 0;
                int end = StringUtils.findNextNonWhitespaceIndex(methodSignature, SyntaxUtils.findCharInBaseScope(methodSignature, '(') - 1, -1) + 1;

                boolean quotes = methodSignature.charAt(end - 1) == '"';

                if (quotes) {
                    start = StringUtils.findEndingQuote(methodSignature, end - 1, -1);
                } else {
                    start = StringUtils.findNextWhitespaceIndex(methodSignature, end - 2, -1) + 1;
                }

                String name = methodSignature.substring(start, end);

                if (quotes) {
                    name = StringUtils.removeSurroundingQuotes(name);
                }

                // TODO: 10/7/2016 make this name foolproof
                methodSignature = methodSignature.substring(0, start) + "__extMethod" + methodSignature.substring(end);

                String withAlias = methodSignature;
                methodSignature = trimAlias(methodSignature);

                statement = methodSignature;
                methodSignature = n.formMethodSignature(methodSignature);

                MethodDeclaration method = FlatMethodDeclaration.decodeStatement(n, methodSignature, location.asNew(), require);

                if (method != null) {
                    method.cloneTo(n);

                    n.setExternal(true);
                    n.setName(name);
                    n.alias = n.getName();
                    n.setLocationIn(location);

                    if (n.decodeAlias(withAlias, statement, require)) {
                        return n;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Form the Flat-style method signature.<br>
     * For example:
     * <blockquote><pre>
     * String in  = "int getValue(String type)";
     * String out = formMethodSignature(in);</pre></blockquote>
     * The 'out' String would contain the value of "getValue(String type) -> int"
     *
     * @param methodSignature The external type method signature.
     * @return The Flat-style method signature.
     */
    private String formMethodSignature(String methodSignature) {
        int paren, prev, end, start;

        paren = methodSignature.indexOf('(');

        prev = StringUtils.findNextLetterIndex(methodSignature, paren - 1, -1);
        end = StringUtils.findNextLetterIndex(methodSignature, prev - 1, -1, true);
        end = StringUtils.findNextLetterIndex(methodSignature, end - 1, -1);
        start = StringUtils.findNextLetterIndex(methodSignature, end - 1, -1, true);

        if (end < 0) {
            SyntaxMessage.error("External method definition missing return type", this);
        }

        Bounds symbols = StringUtils.findGroupedCharsBounds(methodSignature, end + 1, StringUtils.SYMBOLS_CHARS, StringUtils.WHITESPACE);

        if (symbols.isValid()) {
            end = symbols.getEnd();
        }

        String type = methodSignature.substring(start + 1, end + 1);

        methodSignature = methodSignature.substring(0, start + 1) + methodSignature.substring(end + 1);

        if (!type.equals("void")) {
            methodSignature += " -> " + type;
        }

        return StringUtils.trimSurroundingWhitespace(methodSignature);
    }

    /**
     * Trim the alias signature off of the external method declaration.<br>
     * For example:
     * <blockquote><pre>
     * String in  = "int getValue(String type) as ext_getValue";
     * String out = trimAlias(in);</pre></blockquote>
     * The 'out' String would contain "int getValue(String type)"
     *
     * @param methodSignature The external method declaration signature to
     *                        trim the alias from.
     * @return The trimmed method signature.
     */
    private static String trimAlias(String methodSignature) {
        int paren = methodSignature.lastIndexOf(')');
        int index = StringUtils.findLastWordIndex(methodSignature, "as");

        if (index <= paren) {
            return methodSignature;
        }

        return methodSignature.substring(0, StringUtils.findNextNonWhitespaceIndex(methodSignature, index - 1, -1) + 1);
    }

    /**
     * Decode the alias name for the external method.<br>
     * For example:
     * <blockquote><pre>
     * external int externalMethod() as myAlias</pre></blockquote>
     * The above declaration declares an external method that can be
     * called as "myAlias()" and returns an integer result.
     *
     * @param statement       The statement containing the method declaration.
     * @param methodSignature The signature of the external method.
     * @param require         Whether or not to throw an error if anything goes
     *                        wrong.
     * @return Whether or not the alias was successfully decoded.
     */
    private boolean decodeAlias(String statement, String methodSignature, boolean require) {
        String nameChange = statement.substring(methodSignature.length());

        nameChange = StringUtils.trimSurroundingWhitespace(nameChange);

        if (nameChange.length() <= 0) {
            return true;
        }

        iterateWords(nameChange, require);

        return true;
    }

    /**
     * @see Node#interactWord(java.lang.String, Bounds, java.lang.String, java.lang.String, Node.ExtraData)
     */
    @Override
    public boolean interactWord(String word, Bounds bounds, String leftDelimiter, String rightDelimiter, ExtraData extra) {
        if (extra.getWordNumber() == 0) {
            if (!word.equals("as")) {
                fail(word, bounds);
            }
        } else if (extra.getWordNumber() == 1 && extra.isLastWord()) {
            if (SyntaxUtils.isValidIdentifier(word)) {
                setName(word);
            }
        } else {
            fail(word, bounds);
        }

        return true;
    }

    /**
     * Output an error because the word iteration failed.
     *
     * @param word   The word that failed to be decoded.
     * @param bounds The Bounds of the word.
     */
    private void fail(String word, Bounds bounds) {
        Location newLoc = new Location(getLocationIn());
        newLoc.setBounds(bounds.getStart(), bounds.getEnd());

        SyntaxMessage.error("Unknown text '" + word + "'", this, newLoc);
    }

    /**
     * @see VariableDeclaration#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        MethodDeclaration methods[] = getParentClass().getMethods(getName());

        if (methods.length > 1) {
            getParentClass().getMethods(getName());
            SyntaxMessage.error("Non-external method with name '" + alias + "' already exists", this, false);

            return result.errorOccurred();
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExternalMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExternalMethodDeclaration node = new ExternalMethodDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExternalMethodDeclaration cloneTo(ExternalMethodDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExternalMethodDeclaration} with the data that is in
     * the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExternalMethodDeclaration cloneTo(ExternalMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.alias = alias;

        return node;
    }

    /**
     * Test the {@link ExternalMethodDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}