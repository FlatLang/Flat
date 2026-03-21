package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

import static nova.c.nodewriters.NodeWriter.getWriter;

public interface AccessibleWriter
{
	public abstract Accessible node();
	
	/**
	 * If the Value accesses a method call, generate a specialized
	 * output.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return A specialized String generation.
	 */
	default StringBuilder generateSpecialFragment(StringBuilder builder)
	{
		Accessible current = node().getLastAccessedNode();
		
		while (!((Value)current).isSpecial())
		{
			current = current.getAccessingNode();
		}
		
		return getWriter((Value)current).generateSourceFragment(builder);
	}
	
	/**
	 * Generate the C output for when node() value node is being used
	 * as an argument for a method call.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param callingMethod The method that is being called by the
	 * 		specified Identifier.
	 * @return The C output for when node() value node is being used
	 * 		as an argument for a method call.
	 */
	default StringBuilder generateArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		Value n = (Value)node();
		
		if (n instanceof Identifier)
		{
			getWriter(((Identifier)n)).generateUseOutput(builder, false, true);
		}
		else
		{
			getWriter(n).generateUseOutput(builder);
		}
		
		generateChildrenSourceFragment(builder, true, callingMethod, false);
		
		return builder;
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @return The generated String.
	 */
	default StringBuilder generateChildrenSourceFragment()
	{
		return generateChildrenSourceFragment(new StringBuilder(), true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The StringBuilder with the appended generation output.
	 */
	default StringBuilder generateChildrenSourceFragment(StringBuilder builder)
	{
		return generateChildrenSourceFragment(builder, true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @return The generated String.
	 */
	default StringBuilder generateChildrenSourceFragment(boolean reference)
	{
		return generateChildrenSourceFragment(new StringBuilder(), reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @return The StringBuilder with the appended generation output.
	 */
	default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference)
	{
		return generateChildrenSourceFragment(builder, reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The generated String.
	 */
	default StringBuilder generateChildrenSourceFragment(boolean reference, Identifier stopBefore)
	{
		return generateChildrenSourceFragment(new StringBuilder(), reference, stopBefore);
	}
	
	// TODO: use stopAt instead of stopBefore.
	
	/**
	 * Generate a String representing the accessed nodes.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The StringBuilder with the appended generation output.
	 */
	default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore)
	{
		return generateChildrenSourceFragment(builder, reference, stopBefore, true);
	}
	
	default StringBuilder generateChildrenSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore, boolean checkAccesses)
	{
		Identifier child = node().getAccessedNode();
		
		if (child == null)
		{
			return builder;
		}
		
		StringBuilder output = getWriter(child).generateChildSourceFragment(reference, stopBefore, checkAccesses);
		
		if (output.length() > 0 && reference)
		{
			builder.append("->");
		}
		
		return builder.append(output);
	}
	
	/**
	 * Generate the source fragment for the specified node().
	 *
	 * @param reference Whether or not to prepend the "->" operator at
	 * 		the beginning of the generated output.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The StringBuilder with the appended generation output.
	 */
	default StringBuilder generateChildSourceFragment(boolean reference, Identifier stopBefore)
	{
		return generateChildSourceFragment(reference, stopBefore, true);
	}
	
	default StringBuilder generateChildSourceFragment(boolean reference, Identifier stopBefore, boolean checkAccesses)
	{
		Value n = (Value)node();
		
		StringBuilder builder = new StringBuilder();
		
		// If generating the output for the use of an argument.
		if (stopBefore != null)
		{
			if (node() == stopBefore)//instanceof MethodCall || node() instanceof Instantiation)
			{
				return builder;
			}
			
			StringBuilder use = null;
			
			if (n instanceof Identifier)
			{
				use = getWriter(((Identifier)n)).generateUseOutput(builder, false, checkAccesses);
			}
			else
			{
				use = getWriter(n).generateUseOutput(builder);
			}
			
			return use.append(generateChildrenSourceFragment(true, stopBefore));
		}
		
		if (n instanceof Identifier)
		{
			Identifier id = (Identifier)n;
			
			if (id.isSpecialFragment())
			{
				getWriter(id).generateSpecialFragment(builder);
			}
		}
		
		return getWriter(n).generateSourceFragment(builder);
	}
	
	/**
	 * Generate the C Source for the Identifier and the Identifiers that
	 * it accesses until the given 'stopAt' Identifier is reached.
	 *
	 * @param delimiter The String to append in between each Identifier
	 * 		that is accessed.
	 * @param stopAt The Identifier to stop the generation before.
	 * @return The StrignBuilder with the appended data.
	 */
	default StringBuilder generateSourceUntil(String delimiter, Identifier stopAt)
	{
		return generateSourceUntil(new StringBuilder(), delimiter, stopAt);
	}
	
	/**
	 * Generate the C Source for the Identifier and the Identifiers that
	 * it accesses until the given 'stopAt' Identifier is reached.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param delimiter The String to append in between each Identifier
	 * 		that is accessed.
	 * @param stopAt The Identifier to stop the generation before.
	 * @return The StrignBuilder with the appended data.
	 */
	default StringBuilder generateSourceUntil(StringBuilder builder, String delimiter, Identifier stopAt)
	{
		Accessible current = node();
		
		while (current != null && current != stopAt)
		{
			getWriter(((Value)current)).generateUseOutput(builder).append(delimiter);
			
			current = current.getAccessedNode();
		}
		
		return builder;
	}
}