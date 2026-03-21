package flat.java.nodewriters;

import flat.tree.exceptionhandling.Catch;
import flat.tree.exceptionhandling.ExceptionHandler;

public abstract class CatchWriter extends ExceptionHandlerWriter {
    public abstract Catch node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        return builder.append("catch (")
            .append(getWriter(node().getExceptionDeclaration()).writeExpression()).append(") ")
            .append(getWriter(node().getScope()).write(true, false));
    }
}

