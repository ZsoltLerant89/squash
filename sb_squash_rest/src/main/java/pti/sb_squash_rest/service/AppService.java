package pti.sb_squash_rest.service;



import java.io.IOException;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pti.sb_squash_rest.dto.CurrencyDTO;
import pti.sb_squash_rest.model.Currency;
import pti.sb_squash_rest.parser.XMLParser;

@Service
public class AppService {
	
	private XMLParser parser;
	
	@Autowired
	public AppService(XMLParser parser) {
		super();
		this.parser = parser;
	}


	public CurrencyDTO getCurrency() throws JDOMException, IOException {
		CurrencyDTO currencyDTO = null;
		
		RestTemplate restTemplate = new RestTemplate();
		String currencyXML = restTemplate.getForObject("http://api.napiarfolyam.hu/?valuta=eur&bank=erste&valutanem=deviza", String.class);
		
		Currency currency = parser.getInEuro(currencyXML);
		
		currencyDTO = new CurrencyDTO(currency.getName(),currency.getValue());
	
		
		return currencyDTO;
	}

}
