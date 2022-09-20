package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.annotations.AbstractAnnotation;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class AbstractMethodDeclarationValidator extends FlatMethodDeclarationValidator
{
	/**
	 * @see FlatMethodDeclarationValidator#validate(int)
	 */
	public ValidationResult validate(AbstractMethodDeclarationValidator node, int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (!getParentClass().isAbstract())
			{
				result.errorOccurred();
				
				SyntaxMessage.error("The class '" + getParentClass().getName() + "' must be " + IDENTIFIER + " to contain the " + IDENTIFIER + " method " + getName(), this, false);
			}
			
			//searchVirtualMethodDeclaration();
		}
		
		return result;
	}
}