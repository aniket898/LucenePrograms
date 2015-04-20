package TextFilesSearchEngine;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;




public class Driver {

	public static void main(String[] args) throws Exception {
		
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: java " +TextIndexer.class.getName()+ " <index dir> <data dir>");
		}
		String indexDir = args[0];
		String dataDir = args[1];
		long start = System.currentTimeMillis();
		TextIndexer indexer = new TextIndexer(indexDir);
		int numIndexed;
		try {
			numIndexed = indexer.index(dataDir, new TextFileFilter());
		} finally {
			indexer.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds");
		
		
		
		// instantiate the search engine
		TextSearch se = new TextSearch();

		// retrieve top 100 matching document list for the query "Notre Dame museum"
		TopDocs topDocs = se.performSearch("Version,", 100); 

		// obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println(topDocs.totalHits);
		System.out.println("Hits = "+hits.length);
		// retrieve each matching document from the ScoreDoc arry
		for (int i = 0; i < hits.length; i++) {
		    Document doc = se.getDocument(hits[i].doc);
		    String hotelName = doc.get("filename");
		  System.out.println(hotelName); 
		}
	}

}
