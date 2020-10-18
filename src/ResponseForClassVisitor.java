import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ResponseForClassVisitor extends VoidVisitorAdapter<Object>{
	
	private String className;
	private Set<String> allPotentialMethods = new HashSet<>();
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		className = n.getNameAsString();
		
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		allPotentialMethods.add(n.getNameAsString());
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodCallExpr n, Object arg) {
		allPotentialMethods.add(n.getNameAsString());
		super.visit(n, arg);
	}
	
	public String getClassName() {
		return className;
	}
	
	public Set<String> allPotentialMethods() {
		return allPotentialMethods;
	}

}
