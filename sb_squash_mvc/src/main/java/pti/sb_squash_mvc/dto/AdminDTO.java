package pti.sb_squash_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class AdminDTO {

	private List<UserDTO> userDTOList;
	private List<LocationDTO> locationDTOList;
	private UserDTO admin;
	
	
	public AdminDTO(UserDTO admin) {
		super();
		this.userDTOList = new ArrayList<>();
		this.locationDTOList = new ArrayList<>();
		this.admin = admin;
	}


	public List<UserDTO> getUserDTOList() {
		return userDTOList;
	}

	public void setUserDTOList(List<UserDTO> userDTOList) {
		this.userDTOList = userDTOList;
	}

	public List<LocationDTO> getLocationDTOList() {
		return locationDTOList;
	}

	public void setLocationDTOList(List<LocationDTO> locationDTOList) {
		this.locationDTOList = locationDTOList;
	}

	public UserDTO getAdmin() {
		return admin;
	}

	public void setAdmin(UserDTO admin) {
		this.admin = admin;
	}
	
	
	public void addToUserDTOList(UserDTO userDTO)
	{
		this.userDTOList.add(userDTO);
	}
	
	public void addToLocationDTOList(LocationDTO locationDTO)
	{
		this.locationDTOList.add(locationDTO);
	}


	@Override
	public String toString() {
		return "AdminDTO [userDTOList=" + userDTOList + ", locationDTOList=" + locationDTOList + ", admin=" + admin
				+ "]";
	}
	
	
	
	
}
