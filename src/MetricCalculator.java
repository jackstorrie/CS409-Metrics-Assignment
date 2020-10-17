import java.util.Collection;

public class MetricCalculator {

	public static void main(String[] args) throws Exception {
		CouplingBetweenObjectsMetric couplingMetric = new CouplingBetweenObjectsMetric();
		LackOfCohesionOfMethodsMetric lackOfCohesionMetric = new LackOfCohesionOfMethodsMetric();
		Collection<SystemUnderTest> systems = SystemsUnderTestReader.GetSystemsUnderTest();
		for (SystemUnderTest system: systems) {
			lackOfCohesionMetric.printMetricForSystem(system);
		}
	}

}
