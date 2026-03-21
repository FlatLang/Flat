package flat.java.nodewriters;

import flat.tree.Scope;
import flat.tree.Value;
import flat.tree.match.Case;
import flat.tree.variables.Variable;

public abstract class CaseWriter extends MatchCaseWriter {
    public abstract Case node();

    @Override
    public StringBuilder write(StringBuilder builder) {
        Scope scope = node().getScope();

        if (node().getParentMatch().isConventionalSwitch()) {
            Value value = node().getValue();

            builder.append("case ").append(getWriter(value).writeExpression()).append(":\n");

            getWriter(scope).write(builder, false);

            if (node().requiresBreak()) {
                builder.append("break;\n");
            }
        } else {
            Value controlValue = node().getParentMatch().getControlValue();

            String control = getWriter(controlValue).writeExpression().toString();

            Case before = null;
            String fall = "";

            if (node().getParent().getChildBefore(node()) instanceof Case) {
                before = (Case) node().getParent().getChildBefore(node());
            }

            if (before != null) {
                if (before.containsFallthrough()) {
                    Variable fallthrough = node().getParentMatch().getLocalFallthrough();

                    fall = getWriter(fallthrough).writeExpression() + " || ";
                } else {
                    builder.append("else ");
                }
            }

            Value value = node().getValue();

            builder.append("if (").append(fall).append(control).append(" == ")
                .append(getWriter(value).writeExpression()).append(") {\n");

            getWriter(scope).write(builder, false);

            if (node().getParentMatch().requiresLoopFacade() && node().requiresBreak()) {
                builder.append("break;\n");
            }

            builder.append("}\n");
        }

        return builder;
    }
}

