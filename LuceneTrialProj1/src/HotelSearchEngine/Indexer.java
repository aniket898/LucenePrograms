package HotelSearchEngine;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

 
public class Indexer{
	HotelDatabase database = new HotelDatabase();
	
	/** Creates a new instance of Indexer */
    public Indexer() {
    }

    private IndexWriter indexWriter = null;

    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            Directory indexDir = FSDirectory.open(new File("D:\\ADAPTERANDPLUGINS\\work\\LuceneIndexes\\HotelIndexes"));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, new StandardAnalyzer(Version.LUCENE_48));
            indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
   }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
   }

    public void indexHotel(Hotel hotel) throws IOException {

        System.out.println("Indexing hotel: " + hotel);
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new StringField("id", hotel.getId(), Field.Store.YES));
        doc.add(new StringField("name", hotel.getName(), Field.Store.YES));
        doc.add(new TextField("city", hotel.getCity(), Field.Store.YES));
        String fullSearchableText = hotel.getName() + " " + hotel.getCity() + " " + hotel.getDescription();
        doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
        writer.addDocument(doc);
    }

    public void rebuildIndexes() throws IOException {  
    	//
          // Erase existing index
          //
          getIndexWriter(true);
          //
          // Index all Accommodation entries
          //
          addHotels();	
          Set<Hotel> hotels = HotelDatabase.getHotels();
          System.out.println(hotels);
          for(Hotel hotel : hotels) {
              indexHotel(hotel);
          }
          //
          // Don't forget to close the index writer when done
          //
          closeIndexWriter();
     }

	private void addHotels() {
		String description = "";
		String city = "";
		
		for(int i =0;i<200;i++){
			if(i%5==0){
				description = "5 star hotel";
				city = "city5";
			} else if(i%4==0){
				description = "4 star hotel";
				city = "city4";
			} else if(i%3==0){
				description = "3 star hotel";
				city = "city3";
			} else if(i%2==0){
				description = "2 star hotel";
				city = "city2";
			} else{
				description = "1 star hotel";
				city = "city1";
			}
			if(i%15==0){
				city = "Pune";
			}
			Hotel hotel = new Hotel(String.valueOf(i), "hotel"+i, city, description);
			database.addHotel(hotel);
		}
		
	}
 }