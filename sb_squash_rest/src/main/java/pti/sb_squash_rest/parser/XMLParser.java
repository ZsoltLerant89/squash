package pti.sb_squash_rest.parser;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Repository;

import pti.sb_squash_rest.model.Currency;

@Repository
public class XMLParser {

	public Currency getInEuro(String currencyXML) throws JDOMException, IOException
	{
		Currency currency = null;
		
		SAXBuilder sb = new SAXBuilder();
		Document document = sb.build(new StringReader(currencyXML));
		
		Element rootElement = document.getRootElement();
		
		Element devizaElement = rootElement.getChild("deviza");
		Element itemElement = devizaElement.getChild("item");
		
		Element penznemElement = itemElement.getChild("penznem");
		String	penznem = penznemElement.getValue();
		
		Element eladasElement = itemElement.getChild("eladas");
		String 	eladas = eladasElement.getValue();
		Double doubleEladas = Double.parseDouble(eladas);
		
		currency = new Currency(penznem,doubleEladas);
		
		return currency;
	}
}
