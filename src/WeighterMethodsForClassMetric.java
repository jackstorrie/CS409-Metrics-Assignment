import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class WeighterMethodsForClassMetric {
	
	public void printMetricForSystem(SystemUnderTest system) {
		System.out.println(system.getName());
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			WeightedMethodsForClassVisitor visitor = new WeightedMethodsForClassVisitor();
			cu.accept(visitor, null);
			System.out.println(visitor.getClassName());
			for (String method: visitor.getClassNamesAndWeights().keySet()) {
				System.out.println("method: " + method + " weight: "
						+ visitor.getClassNamesAndWeights().get(method));
			}
		}
	}
}
