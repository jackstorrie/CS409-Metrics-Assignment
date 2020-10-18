import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ComplexWeightedMethodsVisitor extends VoidVisitorAdapter<Object> {
	private Map<String, Integer> methodNamesAndWeights = new HashMap<>();
	private String className;
	private String methodName;
	private int weightCount = 0;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		className = n.getNameAsString();
		
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		if (methodName != null) {
			methodNamesAndWeights.put(methodName, weightCount);
		}
		methodName = n.getNameAsString();
		weightCount = 0;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(IfStmt n, Object arg) {
		weightCount++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(ForEachStmt n, Object arg) {
		weightCount++;
		super.visit(n, arg);
	}
	
	@Override
	public void visit(ForStmt n, Object arg) {
		weightCount++;
		super.visit(n, arg);
	}
	
	public String getClassName() {
		return className;
	}
	
	public Map<String, Integer> getClassNamesAndWeights() {
		return methodNamesAndWeights;
	}

}
