package gr.uom.java.xmi.decomposition;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.VariableDeclarationContainer;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

public class TernaryOperatorExpression extends LeafExpression {

	private AbstractExpression condition;
	private AbstractExpression thenExpression;
	private AbstractExpression elseExpression;

	public TernaryOperatorExpression(CompilationUnit cu, String filePath, ConditionalExpression expression, VariableDeclarationContainer container) {
		super(cu, filePath, expression, CodeElementType.TERNARY_OPERATOR, container);
		this.condition = new AbstractExpression(cu, filePath, expression.getExpression(), CodeElementType.TERNARY_OPERATOR_CONDITION, container);
		this.thenExpression = new AbstractExpression(cu, filePath, expression.getThenExpression(), CodeElementType.TERNARY_OPERATOR_THEN_EXPRESSION, container);
		this.elseExpression = new AbstractExpression(cu, filePath, expression.getElseExpression(), CodeElementType.TERNARY_OPERATOR_ELSE_EXPRESSION, container);
	}

	public AbstractExpression getCondition() {
		return condition;
	}

	public AbstractExpression getThenExpression() {
		return thenExpression;
	}

	public AbstractExpression getElseExpression() {
		return elseExpression;
	}

	public String getExpression() {
		return getString();
	}

	public Replacement makeReplacementWithTernaryOnTheRight(String statement) {
		if(getElseExpression().getString().equals(statement)) {
			return new Replacement(statement, getExpression(), ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_ELSE);
		}
		if(getThenExpression().getString().equals(statement)) {
			return new Replacement(statement, getExpression(), ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_THEN);
		}
		return null;
	}

	public Replacement makeReplacementWithTernaryOnTheLeft(String statement) {
		if(getElseExpression().getString().equals(statement)) {
			return new Replacement(getExpression(), statement, ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_ELSE);
		}
		if(getThenExpression().getString().equals(statement)) {
			return new Replacement(getExpression(), statement, ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_THEN);
		}
		return null;
	}
}
