import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;


public class LackOfCohesionOfMethodsMetric {
	
	public static void main(String[] args) throws Exception {
		
		LackOfCohesionOfMethodsMetric lackOfCohesionOfMethodsMetric = new LackOfCohesionOfMethodsMetric();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			lackOfCohesionOfMethodsMetric.printMetricForSystem(system);
		}
	}
	
	
	public void printMetricForSystem(SystemUnderTest system) {
		
		System.out.println(system.getName());
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			LackOfCohesionVisitor visitor = new LackOfCohesionVisitor();
			cu.accept(visitor, null);
			
			Map<String, Set<String>> methodNameAndVars = visitor.getMethodToFieldsMapping();

			Map<String, Set<String>> methodNameAndFields = 
				removeNonFields(methodNameAndVars, visitor.getFields());
		
			int lackOfCohesionScore = 0;
			String [] methodsAsArray = setToArray(methodNameAndFields.keySet());
			for(int i = 0; i < methodsAsArray.length - 1; i++) {
				for (int j = i + 1; j < methodsAsArray.length; j++) {
					for(String field: methodNameAndFields.get(methodsAsArray[i])) {
						if(methodNameAndFields.get(methodsAsArray[j]).contains(field)){
							lackOfCohesionScore++;
							continue;
						}
					}		
				}
			}
			System.out.println("Class: " + visitor.getClassName() + " Lack of cohesion score: " 
					+ lackOfCohesionScore);
			
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

		
		return result;
		
	}
	
	public static String[] setToArray(Set<String> setOfMethods) {
		String[] myArray = new String[setOfMethods.size()];
		setOfMethods.toArray(myArray);
		
		return myArray;
		
	}

}
