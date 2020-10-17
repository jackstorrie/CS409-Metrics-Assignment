import java.util.Collection;

public class SystemUnderTest {
	private String name;
	private Collection<String> sourceFilesContents;
	
	public SystemUnderTest(String name, Collection<String> sourceFilesContents) {
		this.name = name;
		this.sourceFilesContents = sourceFilesContents;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<String> getSourceFileContents() {
		return sourceFilesContents;
	}
	
	@Override
	public String toString() {
		return name + " - " + sourceFilesContents.size() + " files read";
	}
}
