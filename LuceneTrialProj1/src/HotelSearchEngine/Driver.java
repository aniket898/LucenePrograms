package HotelSearchEngine;
import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;


public class Driver {

	public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		
		
		Indexer indexHotels = new Indexer();
		indexHotels.rebuildIndexes();
		
		// instantiate the search engine
		SearchEngine se = new SearchEngine();

		// retrieve top 100 matching document list for the query "Notre Dame museum"
		TopDocs topDocs = se.performSearch("pune", 100); 

		// obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println(topDocs.totalHits);
		System.out.println("Hits = "+hits.length);
		// retrieve each matching document from the ScoreDoc arry
		for (int i = 0; i < hits.length; i++) {
		    Document doc = se.getDocument(hits[i].doc);
		    String hotelName = doc.get("name");
		  System.out.println(hotelName); 
		}
	}

}
