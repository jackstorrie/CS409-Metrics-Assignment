import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;


public class CouplingBetweenObjectsMetric {
	
	public static void main(String[] args) throws Exception {
			
		CouplingBetweenObjectsMetric couplingBetweenObjectsMetric = new CouplingBetweenObjectsMetric();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			couplingBetweenObjectsMetric.printMetricForSystem(system);
		}
	}
	

	
	public void printMetricForSystem(SystemUnderTest system) {
		Map<String, Set<String>> classAndCouplings = new HashMap<>();
		System.out.println(system.getName());
		for (String fileContents: system.getSourceFileContents()) {
			CompilationUnit cu = StaticJavaParser.parse(fileContents);
			CoupledTypesVisitor visitor = new CoupledTypesVisitor();
			cu.accept(visitor, null);
			classAndCouplings.put(visitor.getClassName(), visitor.getCoupledClasses());
		}
		classAndCouplings = removeLibraryCouplings(classAndCouplings);
	}
	
	private static Map<String, Set<String>> removeLibraryCouplings(Map<String, Set<String>> classAndCouplings) {
		Set<String> nonLibraryClasses = classAndCouplings.keySet();
		Map<String, Set<String>> result = new HashMap<>();
		classAndCouplings.forEach((className, couplings) -> {
			if (!result.containsKey(className))
				result.put(className, new HashSet<String>());
			Set<String> newCouplings = result.get(className);
			
			newCouplings.addAll(couplings
					.stream()
					.filter(coupling -> nonLibraryClasses.contains(coupling))
					.collect(Collectors.toList()));
		});
		
		for (String className: result.keySet()) {
			Set<String> couplings = result.get(className);
			for (String coupling: couplings) {
				result.get(coupling).add(className);
			}
		}
		
		for (Map.Entry<String, Set<String>> entry : result.entrySet()) {
		    System.out.println("Class = " + entry.getKey() + " " +"Coupling between objects value: "+ entry.getValue().size());
		}
		return result;
	}

}
