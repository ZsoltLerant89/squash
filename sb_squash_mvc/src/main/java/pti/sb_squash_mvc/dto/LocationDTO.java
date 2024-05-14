package pti.sb_squash_mvc.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LocationDTO {

	private int locationID;
	private String locationName;
	private String locationAddress;
	private int rentFeePerHour;
	private double rentFeePerHourInEur;
	
	
	
	public LocationDTO(	String locationName,
						int rentFeePerHour,
						double rentFeePerHourInEur
						) 
	{
		super();
		this.locationID = 0;
		this.locationName = locationName;
		this.locationAddress = null;
		this.rentFeePerHour = rentFeePerHour;
		this.rentFeePerHourInEur = rentFeePerHourInEur;
	}


	public LocationDTO(	int locationID,
						String locationName, 
						String locationAddress, 
						int rentFeePerHour,
						double rentFeePerHourInEur
						) 
	{
		super();
		this.locationID = locationID;
		this.locationName = locationName;
		this.locationAddress = locationAddress;
		this.rentFeePerHour = rentFeePerHour;
		this.rentFeePerHourInEur = rentFeePerHourInEur;
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

	public double getRentFeePerHourInEur() {
		
		BigDecimal bd = BigDecimal.valueOf(rentFeePerHourInEur);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		
		return bd.doubleValue();
	}

	public void setRentFeePerHourInEur(double rentFeePerHourInEur) {
		this.rentFeePerHourInEur = rentFeePerHourInEur;
	}

	

}
