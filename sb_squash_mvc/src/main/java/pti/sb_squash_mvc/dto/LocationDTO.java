package pti.sb_squash_mvc.dto;

public class LocationDTO {

	private int locationID;
	private String locationName;
	private String locationAddress;
	private int rentFeePerHour;
	
	
	public LocationDTO(	int locationID,
						String locationName, 
						String locationAddress, 
						int rentFeePerHour
						) 
	{
		super();
		this.locationID = locationID;
		this.locationName = locationName;
		this.locationAddress = locationAddress;
		this.rentFeePerHour = rentFeePerHour;
	}


	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public int getRentFeePerHour() {
		return rentFeePerHour;
	}

	public void setRentFeePerHour(int rentFeePerHour) {
		this.rentFeePerHour = rentFeePerHour;
	}
	
	
	
	
}
