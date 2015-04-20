package TextFilesSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TextIndexer {
	private IndexWriter writer;
	
	public TextIndexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, new StandardAnalyzer(Version.LUCENE_48));
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		writer = new IndexWriter(dir, config);
	}
	
	public void close() throws IOException {
		writer.close();
	}
	
	public int index(String dataDir, TextFileFilter textFileFilter) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File f: files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && (textFileFilter == null || textFileFilter.accept(f))) {
				indexFile(f);
			}
		}
		return writer.numDocs();	
	}
	
	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    reader.close();
	    return stringBuilder.toString();
	}

	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		String fileContents = readFile(f.getAbsolutePath());
		doc.add(new TextField("contents", fileContents,Field.Store.YES));
		doc.add(new TextField("filename", f.getName(),Field.Store.YES));
		doc.add(new TextField("fullpath", f.getCanonicalPath(),Field.Store.YES));
		return doc;
	}

	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}
}