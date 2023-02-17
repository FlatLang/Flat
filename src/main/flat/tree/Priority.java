package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * Value extension that represents an operation within parentheses.
 *
 * @author Braden Steffaniak
 * @since v0.2.10 May 29, 2014 at 1:50:25 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Priority extends Value implements Accessible {
    public boolean safeNavigation;
    public boolean chainNavigation;

    /**
     * @see Node#Node(Node, Location)
     */
    public Priority(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public boolean isSafeNavigation() {
        return safeNavigation;
    }

    @Override
    public void setSafeNavigation(boolean safeNavigation) {
        this.safeNavigation = safeNavigation;
    }

    public GenericCompatible getContext() {
        return this;
    }

    @Override
    public boolean isChainNavigation() {
        return chainNavigation;
    }

    @Override
    public void setChainNavigation(boolean chainNavigation) {
        this.chainNavigation = chainNavigation;
    }

    @Override
    public boolean isInstance() {
        return false; // FIXME
    }


    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 1;
    }

    @Override
    public Type getTypeObject() {
        return getReturnedContents().getTypeObject();
    }

    @Override
    public boolean isConstant() {
        return getContents().isConstant();
    }

    @Override
    public boolean isConsistent() {
        return getContents().isConsistent();
    }

    @Override
    public Accessible getAccessingNode() {
        return null;
    }

    @Override
    public Identifier getAccessedNode() {
        return getNumChildren() > 1 ? (Identifier) super.getChild(1) : null;
    }

    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        return getContents().getReturnedNode().getGenericTypeArgumentList();
    }

    @Override
    public GenericTypeParameter getGenericTypeParameter(boolean checkArray) {
        return getReturnedContents().getGenericTypeParameter(checkArray);
    }

    //	@Override
//	public GenericTypeParameter getGenericTypeParameter(boolean checkArray)
//	{
//		if (!doesAccess())
//		{
//			return getReturnedContents().getGenericTypeParameter(checkArray);
//		}
//		
//		return super.getGenericTypeParameter(checkArray);
//	}

    @Override
    public String getGenericReturnType(Value context, boolean checkCast) {
        if (!doesAccess()) {
            return getReturnedContents().getGenericReturnType(context, checkCast);
        }

        return super.getGenericReturnType(context, checkCast);
    }
	
	/*public GenericTypeArgument getGenericTypeArgumentFromParameter(String type)
	{
		return ((Accessible)getReturnedContents()).getGenericTypeArgumentFromParameter(type);
	}*/

    /**
     * Get the Value that represents the contents inside the
     * parentheses.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * int j = (32 + 3 * 3);</pre></blockquote>
     * In the statement above, The binary operation "<u><code>32 + 3 * 3</code></u>"
     * is the contents of the Priority node.
     *
     * @return The Value that represents the contents inside
     * the parentheses.
     */
    public Value getContents() {
        return (Value) getChild(0);
    }

    public Value getReturnedContents() {
        if (getContents() instanceof Assignment) {
            return ((Assignment) getContents()).getAssignedNode();
        }

        return getContents().getReturnedNode();
    }

    @Override
    public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType) {
        return getContents().getReturnedNode().getTypeClass(checkCast, defaultGenericType);
    }

    @Override
    public FileDeclaration getReferenceFile(boolean checkCast) {
        return getReturnedContents().getReferenceFile(checkCast);
    }

    @Override
    public String getType(boolean checkCast) {
        return getReturnedContents().getType(checkCast);
    }

    @Override
    public Value getFlatTypeValue(Value context) {
        if (getContents() instanceof Cast) {
            return getContents().getFlatTypeValue(context);
        } else {
            return getContents().getReturnedNode().getFlatTypeValue(getContents().getReturnedNode());
        }
    }

    @Override
    public String getTypeStringValue() {
        return getReturnedContents().getTypeStringValue();
    }

    @Override
    public void setTypeValue(String type) {
        getReturnedContents().setTypeValue(type);
    }

    @Override
    public int getArrayDimensions() {
        return getReturnedContents().getArrayDimensions() - getArrayAccessDimensions();
    }

    @Override
    public void setArrayDimensions(int arrayDimensions) {
        getReturnedContents().setArrayDimensions(arrayDimensions);
    }

    @Override
    public byte getDataType(boolean checkGeneric) {
        return getDataType(checkGeneric, true);
    }

    public byte getDataType(boolean checkGeneric, boolean checkCast) {
        return getReturnedContents().getDataType(checkGeneric, checkCast);
    }

    @Override
    public void setDataType(byte type) {
        getReturnedContents().setDataType(type);
    }

    @Override
    public Value getReturnedNode() {
        return Accessible.super.getReturnedNode();
    }

    public boolean isPrimitiveUnboxing() {
        if (doesAccess() && getContents() instanceof Cast) {
            Cast c = (Cast) getContents();

            if (c.isPrimitiveType()) {
                return getAccessedNode().getName().equals("value");
            }
        }

        return false;
    }

    @Override
    public StringBuilder generateAccessedNode(StringBuilder builder, boolean safeNavigation) {
        if (isPrimitiveUnboxing()) {
            return builder;
        }

        return Accessible.super.generateAccessedNode(builder, safeNavigation);
    }

    /**
     * @see Value#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        builder.append('(').append(getContents().generateFlatInput(outputChildren)).append(')').append(generateFlatArrayAccess());

        if (outputChildren) {
            generateAccessedNode(builder, safeNavigation);
        }

        return builder;
    }

    public static Priority generateFrom(Value contents) {
        Priority n = new Priority(contents.getParent(), contents.getLocationIn());

        if (contents.getParent().containsChild(contents)) {
            contents.replaceWith(n);
        }
        n.addChild(contents);

        return n;
    }

    /**
     * Decode the given statement into a Priority instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>(5 + 4)</li>
     * 	<li>(getName() + " is here")</li>
     * 	<li>( 54 * (2 + 4) )</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Priority instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes
     *                  wrong.
     * @return The generated node, if it was possible to translated it
     * into a Priority.
     */
    public static Priority decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.charAt(0) == '(') {
            Priority n = new Priority(parent, location);

            if (!n.validatePriority(statement, require)) {
                return null;
            }

            statement = statement.substring(1, statement.length() - 1);
            statement = StringUtils.trimSurroundingWhitespace(statement);

            Location contentsLoc = location.asNew();
            contentsLoc.addOffset(1);
            contentsLoc.moveBounds(1, -1);

            if (n.decodeContents(statement, contentsLoc, require)) {
                return n;
            }
        }

        return null;
    }

    /**
     * Validate that the given statement is a Priority. To see what a
     * Priority looks like see {@link #decodeStatement(Node, String, Location, boolean)}
     *
     * @param statement The statement to validate.
     * @param require   Whether or not to throw an error if anything goes
     *                  wrong.
     * @return Whether or not the given statement is a valid Priority.
     */
    private boolean validatePriority(String statement, boolean require) {
        int endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')');

        if (endingIndex < 0) {
            return SyntaxMessage.queryError("Missing ending parenthesis", this, require);
        }

        return endingIndex == statement.length() - 1;
    }

    /**
     * Decode the contents of the Priority. The contents is the data
     * within the parentheses.
     *
     * @param contents The contents within the parentheses of the
     *                 statement.
     * @param location The location of the contents within the source
     *                 file.
     * @param require  Whether or not to throw an error if anything goes
     *                 wrong.
     * @return Whether or not the contents decoded successfully.
     */
    private boolean decodeContents(String contents, Location location, boolean require) {
        Value node = null;

//		Value node = null
//		
//		until (node != null)
//		{
//			node = Literal.decodeStatement(this, contents, location, true, true),
//			node = UnaryOperation.decodeStatement(this, contents, location, require),
//			node = BinaryOperation.decodeStatement(this, contents, location, require),
//			node = SyntaxTree.getUsableExistingNode(this, contents, location),
//			
//			SyntaxMessage.queryError("Could not decode contents '" + contents + "'", this, require)
//		}
//		
//		addChild(node)
//		
//		return true

        node = SyntaxTree.decodeValue(getAncestorWithScope(), contents, location, require);

//		node = Literal.decodeStatement(this, contents, location, true, true);
//		
//		if (node == null)
//		{
//			node = UnaryOperation.decodeStatement(this, contents, location, require);
//			
//			if (node == null)
//			{
//				node = BinaryOperation.decodeStatement(this, contents, location, require);
//				
//				if (node == null)
//				{
//					node = SyntaxTree.getUsableExistingNode(this, contents, location);

        if (node == null) {
            return SyntaxMessage.queryError("Could not decode contents '" + contents + "'", this, require);
        }
//				}
//			}
//		}

        addChild(node);

        return true;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        result.returnedNode = (Node) checkSafeNavigation();

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Priority clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Priority node = new Priority(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Priority cloneTo(Priority node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Priority} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Priority cloneTo(Priority node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo((Node) node, cloneChildren);

        node.safeNavigation = safeNavigation;
        node.chainNavigation = chainNavigation;

        return node;
    }

    /**
     * Test the Priority class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
