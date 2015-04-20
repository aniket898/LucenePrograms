package TextFilesSearchEngine;

import java.io.File;

public class TextFileFilter {
	public boolean accept(File path) {
		return path.getName().toLowerCase().endsWith(".pdf");	
	}
}
