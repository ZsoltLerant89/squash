package pti.sb_squash_mvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gameid")
	private int gameID;
	
	@Column(name = "firstuserid")
	private int firstUserID;
	
	@Column(name = "seconduserid")
	private int secondUserID;
	
	@Column(name = "gamelocationid")
	private int gameLocationID;
	
	@Column(name = "firstuserscore")
	private int firstUserScore;
	
	@Column(name = "seconduserscore")
	private int secondUserScore;
	
	
	public Game()
	{
		
	}
	

	public Game(int firstUserID, int secondUserID, int gameLocationID, int firstUserScore, int secondUserScore) {
		super();
		this.firstUserID = firstUserID;
		this.secondUserID = secondUserID;
		this.gameLocationID = gameLocationID;
		this.firstUserScore = firstUserScore;
		this.secondUserScore = secondUserScore;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getFirstUserID() {
		return firstUserID;
	}

	public void setFirstUserID(int firstUserID) {
		this.firstUserID = firstUserID;
	}

	public int getSecondUserID() {
		return secondUserID;
	}

	public void setSecondUserID(int secondUserID) {
		this.secondUserID = secondUserID;
	}

	public int getGameLocationID() {
		return gameLocationID;
	}

	public void setGameLocationID(int gameLocationID) {
		this.gameLocationID = gameLocationID;
	}

	public int getFirstUserScore() {
		return firstUserScore;
	}

	public void setFirstUserScore(int firstUserScore) {
		this.firstUserScore = firstUserScore;
	}

	public int getSecondUserScore() {
		return secondUserScore;
	}

	public void setSecondUserScore(int secondUserScore) {
		this.secondUserScore = secondUserScore;
	}
	
	
	

}
