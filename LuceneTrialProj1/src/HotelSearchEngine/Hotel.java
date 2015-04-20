package HotelSearchEngine;

public class Hotel {
	private String id;
	private String name;
	private String city;
	private String description;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCity() {
		return city;
	}
	
	public Hotel(String id, String name,String city,String description){
		this.city = city;
		this.id = id;
		this.name = name;
		this.description = description;
		System.out.println(city);
	}

	public String getDescription() {
		return description;
	}
	
}
