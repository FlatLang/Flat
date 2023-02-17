package flat.tree.exceptionhandling;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.ArrayList;

/**
 * ExceptionHandler extension that represents the declaration of a
 * catch node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 22, 2014 at 4:01:44 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Catch extends ExceptionHandler {
    public boolean soft = false;

    public static final String IDENTIFIER = "catch";

    /**
     * @see Node#Node(Node, Location)
     */
    public Catch(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#getNumDecodedChildren()
     */
    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 2;
    }

    /**
     * Get the declaration of the Exception variable that is being caught.
     *
     * @return The Exception variable declaration that is being caught.
     */
    public LocalDeclaration getExceptionDeclaration() {
        return (LocalDeclaration) getChild(1);
    }

    /**
     * Get the Exception that is being caught by this node.
     *
     * @return The Exception instance.
     */
    public Exception getException() {
        return (Exception) getChild(2);
    }

    /**
     * Get the Try that this Catch is referring to.
     *
     * @param parent The parent node of this Catch.
     * @return The Try instance that this Catch is referring to.
     */
    private Try getCurrentTry(Node parent) {
        if (parent.containsScope()) {
            parent = parent.getScope();
        }

        for (int i = parent.getNumChildren() - 1; i >= 0; i--) {
            Node child = parent.getChild(i);

            if (child instanceof Try) {
                return (Try) child;
            } else if (!(child instanceof Catch)) {
                return null;
            }
        }

        return null;
    }

    /**
     * Decode the given statement into a Catch instance, if
     * possible. If it is not possible, this method returns null.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>catch (Exception e)</li>
     * 	<li>catch (DivideByZeroException e)</li>
     * 	<li>catch (IOException e)</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Catch instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a Catch.
     */
    public static Catch decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (StringUtils.startsWithWord(statement, IDENTIFIER)) {
            Catch n = new Catch(parent, location);
            Location newLoc = location.asNew();

            statement = statement.substring(IDENTIFIER.length()).trim();

            if (StringUtils.startsWithWord(statement, "all")) {
                n.soft = true;

                statement = statement.substring("all".length()).trim();
            }

            Bounds bounds = n.calculateCatchContents(statement);
            String contents = statement.substring(bounds.getStart(), bounds.getEnd());

            newLoc.addBounds(bounds.getStart(), bounds.getEnd());

            if (n.decodeExceptionDeclaration(contents, location, require) && n.decodeException(newLoc, require)) {
                return n;
            }
        }

        return null;
    }

    /**
     * Calculate the Bounds that contain the contents that the Catch is
     * catching.
     *
     * @param statement The statement containing the data.
     * @return The Bounds of the contents of the Catch.
     */
    private Bounds calculateCatchContents(String statement) {
        Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(this, statement);

        if (!bounds.isValid()) {
            SyntaxMessage.error("Catch declaration missing Exception type", this);
        }

        return bounds;
    }

    /**
     * Decode the parameter type declaration of the Exception that
     * is being caught by the Catch.
     *
     * @param contents The String to decode as a parameter type
     *                 declaration.
     * @param location The location that the declaration is located at.
     * @param require  Whether or not to throw an error if anything goes
     *                 wrong.
     * @return Whether or not the declaration decoded successfully.
     */
    private boolean decodeExceptionDeclaration(String contents, Location location, boolean require) {
        boolean targetC = getProgram().getController().target.equals("c");

        LocalDeclaration declaration = LocalDeclaration.decodeStatement(this, contents, location, require);

        if (declaration == null) {
            SyntaxMessage.error("Incorrect Exception declaration", this, location);
        }

        ExceptionDeclaration exceptionDeclaration = (ExceptionDeclaration) declaration.cloneTo(new ExceptionDeclaration(declaration.getParent(), declaration.getLocationIn()));

        getScope().addChild(exceptionDeclaration.clone(this, exceptionDeclaration.getLocationIn()));

        if (targetC) {
//			Assignment assign = Assignment.decodeStatement(this, exceptionDeclaration.getName() + " = null", Location.INVALID, true);
//			
//			assign.setProperty("userMade", false);
//			assign.getAssignedNode().setProperty("userMade", false);
//			
//			Variable exceptionData = getExceptionDataVariable(this, Location.INVALID);
//			Variable thrownException = SyntaxTree.getUsableExistingNode(exceptionData, "thrownException", Location.INVALID);
//			exceptionData.addChild(thrownException);
//			
//			Cast c = new Cast(assign, assign.getLocationIn());
//			c.setType(exceptionDeclaration.getTypeStringValue());
//			c.addChild(exceptionData);
//			
//			assign.replace(assign.getAssignmentNode(), c);
//			assign.getAssignedNode().setDataType(c.getDataType());
//			
//			addChild(assign);

            ExternalCodeBlock block = ExternalCodeBlock.decodeStatement(this, "external c", location, true);
            block.bounds = new String[]{""};
            block.expressions = new ArrayList<>();
            block.expressions.add(true);
            block.addChild(SyntaxTree.getUsableExistingNode(this, exceptionDeclaration.getName(), Location.INVALID, false));
            block.ending = " = (void*)thrownData->flat_exception_Flat_ExceptionData_Flat_thrownException;";

            addChild(block);

//			ExternalCodeBlock block = ExternalCodeBlock.decodeStatement(this, "external c", location, true);

//			block.ending = "exceptionData = exceptionData->flat_exception_Flat_ExceptionData_Flat_parent;";

//			addChild(block);
        }

        addChild(exceptionDeclaration, this);

        return true;
    }

    /**
     * Decode the Exception that was declared. This is used to find out
     * the Exception id.
     *
     * @param location The location that the declaration was located at.
     * @param require  Whether or not to throw an error if anything goes
     *                 wrong.
     * @return Whether or not the exception was found successfully.
     */
    private boolean decodeException(Location location, boolean require) {
        Exception exception = new Exception(this, location);
        exception.soft = soft;
        exception.setType(getExceptionDeclaration().getTypeClass());

        if (exception.type == null) {
            SyntaxMessage.error("Unknown exception type", exception);
        }

        addChild(exception, this);
        addExceptionCode(location);

        return true;
    }

    /**
     * Add the code that was found when decoding the Exception to the
     * parent Try Node.
     *
     * @param location The location that the declaration was located at.
     */
    private void addExceptionCode(Location location) {
        Try tryNode = getCurrentTry(getParent());

        if (tryNode == null) {
            SyntaxMessage.error("Parent try block not found", this, location);
        }

        ClassDeclaration exception = getExceptionDeclaration().getTypeClass();

//		ClassDeclaration object = getProgram().getClassDeclaration("flat/Object");
//		
//		while (exception != null && exception.doesExtendClass(object))
//		{
        tryNode.addCaughtException(getException());
//			
//			exception = exception.getTypeClass().getExtendedClassDeclaration();
//		}
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        builder.append("catch ");

        if (soft) {
            builder.append("all ");
        }

        builder.append("(");

        getExceptionDeclaration().generateFlatInput(builder);

        builder.append(")");

        if (outputChildren) {
            builder.append(" ");
            getScope().generateFlatInput(builder, true);
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Catch clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Catch node = new Catch(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Catch cloneTo(Catch node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Catch} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Catch cloneTo(Catch node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Catch class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}