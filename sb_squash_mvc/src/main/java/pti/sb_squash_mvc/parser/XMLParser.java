package pti.sb_squash_mvc.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.dto.GameDTO;
import pti.sb_squash_mvc.dto.LocationDTO;
import pti.sb_squash_mvc.dto.UserDTO;

@Repository
public class XMLParser {

	public void exportGameDTOsToXML(List<GameDTO> gameDTOs) throws IOException
	{
		/**
		 * <games>
		 * 	<game>
		 * 		<gid></gid>
		 * 		<date></date>
		 * 
		 * 		<firstuser>
		 * 			<name></name>
		 * 			<score></score>
		 * 		</firstuser>
		 * 
		 * 		<seconduser>
		 * 			<name></name>
		 * 			<score></score>
		 * 		</seconduser>
		 * 
		 * 		<location>
		 * 			<name></name>
		 * 			<fee></fee>
		 * 		</location>
		 * 	</game>
		 * </games	
		 *  */
		
		Document document = new Document();
		Element rootElement = new Element("games");
		
		for(int index = 0; index < gameDTOs.size(); index++)
		{
			GameDTO currentGameDTO = gameDTOs.get(index);
			
			Element gameElement = new Element("game");
			
			/** Game id */
			Element gameidElement = new Element("gid");
			gameidElement.setText("" + currentGameDTO.getGameID());
			
			gameElement.addContent(gameidElement);
			
			/** Date */
			Element dateElement = new Element("date");
			dateElement.setText("" + currentGameDTO.getDate());
			
			gameElement.addContent(dateElement);
			
			/** First user */
			Element firstUserElement = new Element("firstuser");
			
			
			/** First user name */
			Element firstUserNameElement = new Element("name");
			firstUserNameElement.setText(currentGameDTO.getFirstUserDTO().getUserName());
			firstUserElement.addContent(firstUserNameElement);
			
			/** First user score */
			Element firstUserScoreElement = new Element("score");
			
			firstUserScoreElement.setText("" + currentGameDTO.getFirstUserScore());
			firstUserElement.addContent(firstUserScoreElement);
			
			gameElement.addContent(firstUserElement);
			
			/** Second user */
			Element secondUserElement = new Element("seconduser");
			
			/** Second user name */
			Element secondUserNameElement = new Element("name");
			
			secondUserNameElement.setText(currentGameDTO.getSecondUserDTO().getUserName());
			secondUserElement.addContent(secondUserNameElement);
			
			/** Second user score */
			Element secondUserScoreElement = new Element("score");
			
			secondUserScoreElement.setText("" + currentGameDTO.getSecondUserScore());
			secondUserElement.addContent(secondUserScoreElement);
			
			gameElement.addContent(secondUserElement);
	
			/** Location */
			Element locationElement = new Element("location");
			
			/** Location name */
			Element locationNameElement = new Element("name");
			locationNameElement.setText(currentGameDTO.getLocationDTO().getLocationName());
			
			locationElement.addContent(locationNameElement);
			
			/** Location feeHuf */
			Element locationFeeElementHuf = new Element("feehuf");
			
			locationFeeElementHuf.setText("" + currentGameDTO.getLocationDTO().getRentFeePerHour());
			locationElement.addContent(locationFeeElementHuf);
			
			/** Location feeEUR */
			Element locationFeeElementEUR = new Element("feeeur");
			
			locationFeeElementEUR.setText("" + currentGameDTO.getLocationDTO().getRentFeePerHourInEur());
			locationElement.addContent(locationFeeElementEUR);
			
			gameElement.addContent(locationElement);
			
			rootElement.addContent(gameElement);
			
		}
		
		document.setRootElement(rootElement);
		
		FileWriter writer = new FileWriter("squash.xml");
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		
		outputter.output(document, writer);
		
		writer.close();
		
		
	}

	public List<GameDTO> getGameDTOListFromXML(String path) throws JDOMException, IOException {
		List<GameDTO> gameDTOList = new ArrayList<>();
		
		SAXBuilder sb = new SAXBuilder();
		Document document = sb.build(new File(path));
		
		if (document != null)
		{
			Element rootElement = document.getRootElement();
			List<Element> gameElementList = rootElement.getChildren("game");
		
			for(int index = 0; index < gameElementList.size(); index++)
			{
				Element currentGameElement = gameElementList.get(index);
				
				/** Game id  */
				Element gidElement = currentGameElement.getChild("gid");
				String gameID = gidElement.getValue();
				Integer gameIDInteger = Integer.parseInt(gameID);
				
				/** Date */
				Element dateElement = currentGameElement.getChild("date");
				String date = dateElement.getValue();
				LocalDate dateInDate = LocalDate.parse(date);
				
				/** First user */
				Element firstUserElement = currentGameElement.getChild("firstuser");
				
				/** Name */
				Element firstUserNameElement = firstUserElement.getChild("name");
				String firstUserName = firstUserNameElement.getValue();
				
				/** Score */
				Element firstUserScoreElement = firstUserElement.getChild("score");
				String firstUserScore = firstUserScoreElement.getValue();
				Integer firstUserScoreInInteger = Integer.parseInt(firstUserScore);
				
				
				/** Second user */
				Element secondUserElement = currentGameElement.getChild("seconduser");
				
				/** Name */
				Element secondUserNameElement = secondUserElement.getChild("name");
				String secondUserName = secondUserNameElement.getValue();
				
				/** Score */
				Element secondUserScoreElement = secondUserElement.getChild("score");
				String secondUserScore = secondUserScoreElement.getValue();
				Integer secondUserScoreInInteger = Integer.parseInt(secondUserScore);
				
				/** Location */
				Element locationElement = currentGameElement.getChild("location");
				
				/** Name */
				Element locationNameElement = locationElement.getChild("name");
				String locationName = locationNameElement.getValue();
				
				/** Fee huf */
				Element locationFeeHufElement = locationElement.getChild("feehuf");
				String locationFeeHuf = locationFeeHufElement.getValue();
				Integer locationFeeHufInInteger = Integer.parseInt(locationFeeHuf);
				
				/** Fee EUR */
				Element locationFeeEurElement = locationElement.getChild("feeeur");
				String locationFeeEur = locationFeeEurElement.getValue();
				Double locationFeeEurInDouble = Double.parseDouble(locationFeeEur);
				
				
				UserDTO firstUserDTO = new UserDTO(firstUserName);
				UserDTO secondUserDTO = new UserDTO(secondUserName);
				LocationDTO locationDTO = new LocationDTO(locationName,locationFeeHufInInteger,locationFeeEurInDouble);
				
				GameDTO gameDTO = new GameDTO(gameIDInteger,firstUserDTO,firstUserScoreInInteger,secondUserDTO,secondUserScoreInInteger,locationDTO,dateInDate);
				
				gameDTOList.add(gameDTO);
			}
		}
		
		return gameDTOList;
	}
}
