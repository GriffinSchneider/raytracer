import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileParser {
	private File file;
	
	public FileParser(String filePath) {
		this.file = new File( filePath );
	}
	
	public ArrayList<String> returnLines() throws FileNotFoundException {
		Scanner scanner = new Scanner( this.file );
		ArrayList<String> lines = new ArrayList<String>();
		while ( scanner.hasNextLine() ) {
			lines.add( scanner.nextLine() );
		}
		scanner.close();
		return lines;
	}
	
	
}
