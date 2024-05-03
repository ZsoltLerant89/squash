package pti.sb_squash_mvc.dto;

public class GameDTO {

	private int gameID;
	private UserDTO firstUserDTO;
	private int firstUserScore;
	private UserDTO secondUserDTO;
	private int secondUserScore;
	private LocationDTO locationDTO;
	
	
	public GameDTO(	int gameID,
					UserDTO firstUserDTO, 
					int firstUserScore, 
					UserDTO secondUserDTO, 
					int secondUserScore,
					LocationDTO locationDTO
					) 
	{
		super();
		this.gameID = gameID;
		this.firstUserDTO = firstUserDTO;
		this.firstUserScore = firstUserScore;
		this.secondUserDTO = secondUserDTO;
		this.secondUserScore = secondUserScore;
		this.locationDTO = locationDTO;
	}


	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public UserDTO getFirstUserDTO() {
		return firstUserDTO;
	}

	public void setFirstUserDTO(UserDTO firstUserDTO) {
		this.firstUserDTO = firstUserDTO;
	}

	public int getFirstUserScore() {
		return firstUserScore;
	}

	public void setFirstUserScore(int firstUserScore) {
		this.firstUserScore = firstUserScore;
	}

	public UserDTO getSecondUserDTO() {
		return secondUserDTO;
	}

	public void setSecondUserDTO(UserDTO secondUserDTO) {
		this.secondUserDTO = secondUserDTO;
	}

	public int getSecondUserScore() {
		return secondUserScore;
	}

	public void setSecondUserScore(int secondUserScore) {
		this.secondUserScore = secondUserScore;
	}

	public LocationDTO getLocationDTO() {
		return locationDTO;
	}

	public void setLocationDTO(LocationDTO locationDTO) {
		this.locationDTO = locationDTO;
	}


	@Override
	public String toString() {
		return "GameDTO [gameID=" + gameID + ", firstUserDTO=" + firstUserDTO + ", firstUserScore=" + firstUserScore
				+ ", secondUserDTO=" + secondUserDTO + ", secondUserScore=" + secondUserScore + ", locationDTO="
				+ locationDTO + "]";
	}
	
	
	
	
	
	
}
