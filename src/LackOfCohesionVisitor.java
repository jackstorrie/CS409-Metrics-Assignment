import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LackOfCohesionVisitor extends VoidVisitorAdapter<Object> {
	
	Map<String, Set<String>> methodNameAndVars = new HashMap<>();
	private Set<String> fields = new HashSet<>();
	private String currentMethodName;
	private Set<String> currentVars = new HashSet<>();
	private String className;
	
	public Map<String, Set<String>> getMethodToFieldsMapping(){
		return methodNameAndVars;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		className = n.getNameAsString();
		
		super.visit(n, arg);
	}
	
	@Override
	public void visit(VariableDeclarator n, Object arg) {
		fields.add(n.getNameAsString());
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		currentMethodName = n.getNameAsString();
		currentVars = new HashSet<>();
		methodNameAndVars.put(currentMethodName, currentVars);
		super.visit(n, arg);
	}
	
	@Override
	public void visit(SimpleName n, Object arg) {
		String name = n.toString();
		currentVars.add(name);
		super.visit(n, arg);
	}
	
	
	
	public String getClassName() {
		return className;
	}
	
	public Set<String> getFields() {
		return fields;
	}
	
}
	


