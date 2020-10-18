import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ResponseForClassMetric{

	public void printMetricForSystem(SystemUnderTest system) {
		System.out.println(system.getName());
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			ResponseForClassVisitor visitor = new ResponseForClassVisitor();
			cu.accept(visitor, null);
			
			System.out.println(visitor.getClassName() + " responseForClassScore: " + visitor.allPotentialMethods().size());
			
		}
	}
}
