package pti.sb_squash_mvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locationid")
	private int locationID;
	
	@Column(name = "locationname")
	private String locationName;
	
	@Column(name = "locationaddress")
	private String locationAddress;
	
	@Column(name = "rentfeeperhour")
	private int rentFeePerHour;
	
	
	public Location()
	{
		
	}
	

	public Location(String locationName,
					String locationAddress,
					int rentFeePerHour
					) 
	{
		super();
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
