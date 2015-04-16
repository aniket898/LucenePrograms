import java.util.HashSet;
import java.util.Set;


public class HotelDatabase {
	private static Set<Hotel> hotels = new HashSet<Hotel>();

	public static Set<Hotel> getHotels() {
		return hotels;
	}

	public void addHotel(Hotel hotel){
		this.hotels.add(hotel);
	}
	
	
}
