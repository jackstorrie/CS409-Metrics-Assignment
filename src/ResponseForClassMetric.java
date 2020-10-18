import java.util.Collection;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ResponseForClassMetric{
	

	public static void main(String[] args) throws Exception {
		
		ResponseForClassMetric responseForClassMetric = new ResponseForClassMetric();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			responseForClassMetric.printMetricForSystem(system);
		}
	
	}

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
