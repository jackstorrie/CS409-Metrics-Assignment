import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class WeightedMethodsVisitor  extends VoidVisitorAdapter<Object>{
	
	private String className;
	private int weightCount = 0;
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		weightCount = 0;
		className = n.getNameAsString();
		
		super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		weightCount ++;
		super.visit(n, arg);
	}
	
	public String getClassName() {
		return className;
	}
	
	public int getClassWeight() {
		return weightCount;
	}


	

}
