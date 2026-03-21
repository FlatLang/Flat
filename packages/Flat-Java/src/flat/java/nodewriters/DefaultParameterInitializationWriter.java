package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.Variable;

public abstract class DefaultParameterInitializationWriter extends NodeWriter {
    public abstract DefaultParameterInitialization node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
        Parameter param = node().parameter;

        String optionalName = getWriter(param).writeOptionalName().toString();

        getWriter(param).writeType(builder).append(param.getName()).append(" = ")
            .append(optionalName).append(" == null ? ");

        if (param.getDefaultValue() instanceof Variable
            && ((Variable) param.getDefaultValue()).getName().equals(param.getName())) {
            builder.append("this.");
        }

        return getWriter(param.getDefaultValue()).writeExpression(builder, stopAt).append(" : ")
            .append(optionalName).append(".get()");
    }
}

