package flat.validator;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.UnimplementedOperationException;
import flat.tree.exceptionhandling.Try;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public abstract class NodeValidator
{
	/**
	 * Validate the node to make last minute changes or error checking.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @return The Node to continue the validation off of.
	 */
	public ValidationResult validate(Node node, int phase)
	{
		if (getNumAnnotations() > 0)
		{
			for (int i = annotations.size() - 1; i >= 0; i--)
			{
				ValidationResult result = annotations.get(i).validate(phase);
				
				if (result.skipValidation())
				{
					return result;
				}
			}
		}
		
		return new ValidationResult(this);
	}
}