import java.util.Collection;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;


public class WeightedMethodsMetric {
	
public static void main(String[] args) throws Exception {
		
		WeightedMethodsMetric weightedMethodsForClassMetric = new WeightedMethodsMetric ();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			weightedMethodsForClassMetric.printMetricForSystem(system);
		}
	
	}

	public void printMetricForSystem(SystemUnderTest system) {
		System.out.println(system.getName());
		
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			WeightedMethodsVisitor visitor = new WeightedMethodsVisitor();
			cu.accept(visitor, null);
			System.out.println("class: " + visitor.getClassName() + " weight: " + visitor.getClassWeight());
		}
	}

}
