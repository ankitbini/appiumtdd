package ankit.qa.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ankit.qa.BaseTest;

public class TestUtils {
	public static final long WAIT = 10;
	
	public HashMap<String, String> parseStringXML(InputStream file) throws Exception
	{
		HashMap<String, String> stringMap = new HashMap<String, String>();
		//get document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		//Build document
		Document document = builder.parse(file);
		
		//Normalize the XML structure
		document.getDocumentElement().normalize();
		
		//Here comes the root nods
		Element root = document.getDocumentElement();
//		System.out.println(root.getNodeName());
		
		//get all elements
		NodeList nList = document.getElementsByTagName("string");
//		System.out.println("====================");
		
		for(int temp=0; temp<nList.getLength(); temp++) {
			Node node = nList.item(temp);
//			System.out.println("just a separator");
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element)node;
				//store each element key value in map
				stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
			}
		}
		return stringMap;
	}
	
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}
	
	public Logger log() {
		return LogManager.getLogger(BaseTest.class.getName());
	}

}
