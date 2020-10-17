import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class SystemsUnderTestReader {
	private final static String SystemUnderTestPath = "CS409TestSystem2020";
	private final static String SourceCodeExtension = ".java";
	
	public static Collection<SystemUnderTest> GetSystemsUnderTest() {
		List<SystemUnderTest> result = new ArrayList<>();
		
		File projectsFolder = new File(SystemUnderTestPath);
		for (String projectName: projectsFolder.list()) {
			String projectPath = SystemUnderTestPath + "\\" + projectName;
			Collection<String> sourceFilesContents = getSourceFilesContents(projectPath);
			result.add(new SystemUnderTest(projectName, sourceFilesContents));
		}
		
		return result;
	}
	
	private static Collection<String> getSourceFilesContents(String projectPath) {
		List<String> result = new ArrayList<>();
		
		File projectFolder = new File(projectPath);
		for (String fileName: projectFolder.list()) {
			if (!fileName.endsWith(SourceCodeExtension))
				continue;
			String filePath = projectPath + "\\" + fileName;
			
			String fileContents;
			try {
				fileContents = readFileContents(filePath);	
			} catch (FileNotFoundException ex)  {
				continue;
			}
			
			result.add(fileContents);
		}
		
		return result;
	}
	
	private static String readFileContents(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine() + "\n");
		}
		scanner.close();
		String result = sb.toString();
		return sb.toString().substring(0, result.length() - 1);
	}
}
