package flat.validator.annotations;

import flat.ValidationResult;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.validator.ValidationResult;

public class NamedArgumentModifier extends AnnotationValidator
{
	public ValidationResult validate(NamedArgumentModifier node, int phase)
	{
		ValidationResult result = super.validate(node, phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (parent instanceof Parameter)
		{
			Parameter param = (Parameter)parent;
			
			param.requireNamed = true;
		}
		else
		{
			invalidApplication(this, true);
		}
		
		return result;
	}
	
}