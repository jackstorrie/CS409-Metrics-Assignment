import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class LackOfCohesionOfMethodsMetric {
	
	public void printMetricForSystem(SystemUnderTest system) {
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			LackOfCohesionVisitor visitor = new LackOfCohesionVisitor();
			cu.accept(visitor, null);
			Map<String, Set<String>> methodNameAndVars = visitor.getMethodToFieldsMapping();
//			System.out.println(methodNameAndVars.keySet());
			System.out.println("Class: " + visitor.getClassName());
			removeNonFields(methodNameAndVars, visitor.getFields());
		}
		
	}
	
	private static Map<String, Set<String>> removeNonFields(
			Map<String, Set<String>> methodNameAndVars, 
			Set<String> fields) {
		Set<String> allMethodNames = methodNameAndVars.keySet();
		Set<String> onlyFields = new HashSet<>();
		Map<String, Set<String>> result = new HashMap<>();
		for (String key: allMethodNames) {
			for (String field: fields) {
				if(methodNameAndVars.get(key).contains(field)) {
					//System.out.println("key: " + key + " all the simple names: "
							//+ methodNameAndVars.get(key) + " they contain the field: "
							//+ field);
					onlyFields.add(field);
				}
			result.put(key, onlyFields);
			}
			onlyFields = new HashSet<>();
		}

		for (String method: result.keySet()) {
			System.out.println("method: " + method + " all the fields: " + result.get(method));
		}
		return null;
		
	}

}
