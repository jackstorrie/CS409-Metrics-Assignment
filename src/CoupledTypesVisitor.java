

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CoupledTypesVisitor extends VoidVisitorAdapter<Object> {
	
	private Set<String> coupledClasses = new HashSet<>();
	private String className;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		className = n.getNameAsString();
		
		super.visit(n, arg);
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
		if (!n.getNameAsString().equals(className)) {
			coupledClasses.add(n.getNameAsString());
		}
		
		super.visit(n, arg);
	}
	
	public Set<String> getCoupledClasses() {
		return coupledClasses;
	}
	
	public String getClassName() {
		return className;
	}
}
