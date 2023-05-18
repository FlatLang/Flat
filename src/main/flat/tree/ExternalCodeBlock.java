package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.annotations.TargetAnnotation;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExternalCodeBlock extends Node implements ScopeAncestor {
    public String ending = "";
    public String[] bounds;

    public ArrayList<Boolean> expressions;

    public String target;

    private int uniqueID;

    private static HashMap<Integer, Scope> scopes = new HashMap<>();

    public static final String IDENTIFIER = "external";

    /**
     * @see Node#Node(Node, Location)
     */
    public ExternalCodeBlock(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public int getUniqueID() {
        return ++uniqueID;
    }

    @Override
    public HashMap<Integer, Scope> getScopes() {
        return scopes;
    }

    /*
     * private String replaceExternalInterpolations(String contents) { StringBuilder builder = new
     * StringBuilder();
     * 
     * int index = SyntaxUtils.findStringOutsideOfQuotes(contents, "${", 0); int end = 0;
     * 
     * while (index >= 0) { builder.append(contents.substring(end, index));
     * 
     * end = SyntaxUtils.findEndingBrace(contents, index + 2) + 1;
     * 
     * String value = contents.substring(index + 2, end - 1).trim();
     * 
     * builder.append(value);
     * 
     * index = SyntaxUtils.findStringOutsideOfQuotes(contents, "${", end); }
     * 
     * return builder.append(contents.substring(end)).toString(); }
     */

    public void setContents(String contents) {
        if (target != null
            && !TargetAnnotation.targetMatches(getProgram().getController().target, target)) {
            return;
        }

        ArrayList<String> bounds = new ArrayList<>();
        expressions = new ArrayList<>();

        int index = SyntaxUtils.findStringOutsideOfQuotes(contents, "#{", 0);
        int end = 0;

        while (index >= 0) {
            bounds.add(contents.substring(end, index));

            end = SyntaxUtils.findEndingBrace(contents, index + 2) + 1;

            String value = contents.substring(index + 2, end - 1);

            expressions.add(!value.contains("\n"));

            // value = replaceExternalInterpolations(value);

            Node content =
                SyntaxTree.decodeScopeContents(getParent(), value.trim(), getLocationIn());

            addChild(content, this);

            content.onAfterDecoded();

            index = SyntaxUtils.findStringOutsideOfQuotes(contents, "#{", end);
        }

        ending = contents.substring(end);

        this.bounds = bounds.toArray(new String[0]);
    }

    public String joinContents(BiFunction<Node, Boolean, String> func) {
        String output = "";

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            output += bounds[i] + func.apply(getVisibleChild(i), expressions.get(i));
        }

        return output + ending;
    }

    /**
     * Decode the given statement into a {@link ExternalCodeBlock} instance, if possible. If it is
     * not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link ExternalCodeBlock} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link ExternalCodeBlock}.
     */
    public static ExternalCodeBlock decodeStatement(Node parent, String statement,
        Location location, boolean require) {
        String word = StringUtils.findNextWord(statement);

        if (word != null && word.equals(IDENTIFIER)) {
            ExternalCodeBlock n = new ExternalCodeBlock(parent, location);

            String target = StringUtils.findNextWord(statement, word.length() + 1);

            if (target != null) {
                n.target = target.toLowerCase();
            }

            return n;
        }

        return null;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            if (target != null
                && !TargetAnnotation.targetMatches(getProgram().getController().target, target)) {
                detach();

                result.skipCycle = true;
            }
        }

        return result;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        builder.append("external ").append(target);

        if (outputChildren) {
            builder.append(" {\n").append(joinContents(
                (x, e) -> "#{" + (e ? "" : "\n") + x.generateFlatInput() + (e ? "" : "\n") + "}"))
                .append("\n}");
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExternalCodeBlock clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        ExternalCodeBlock node = new ExternalCodeBlock(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExternalCodeBlock cloneTo(ExternalCodeBlock node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExternalCodeBlock} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExternalCodeBlock cloneTo(ExternalCodeBlock node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.ending = ending;
        node.bounds = bounds;
        node.expressions = expressions;
        node.target = target;
        node.uniqueID = uniqueID;

        return node;
    }

    /**
     * Test the {@link ExternalCodeBlock} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

