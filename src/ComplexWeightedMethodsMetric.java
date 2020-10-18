import java.util.Collection;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ComplexWeightedMethodsMetric {
	
	public static void main(String[] args) throws Exception {
		
		ComplexWeightedMethodsMetric weightedMethodsForClassMetric = new ComplexWeightedMethodsMetric ();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			weightedMethodsForClassMetric.printMetricForSystem(system);
		}
	
	}
	
	public void printMetricForSystem(SystemUnderTest system) {
		System.out.println(system.getName());
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			ComplexWeightedMethodsVisitor visitor = new ComplexWeightedMethodsVisitor();
			cu.accept(visitor, null);
			System.out.println(visitor.getClassName());
			for (String method: visitor.getClassNamesAndWeights().keySet()) {
				System.out.println("method: " + method + " weight: "
						+ visitor.getClassNamesAndWeights().get(method));
			}
		}
	}
}
