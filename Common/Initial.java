package Common;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Enums.XMlEnum;

public class Initial{
	WebDriver driver;
	public Initial(){}
	public WebDriver getDriver(){
	try{
		
		// for firefox
		// System.setProperty("webdriver.firefox.bin","C:\\Program Files
		// (x86)\\Mozilla Firefox\\firefox.exe");
		// System.setProperty("webdriver.gecko.driver",
		// "C:\\Users\\Ariel\\workspace\\geckodriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setBinary(getValueFromConfig(XMlEnum.GoogleExe));
		System.setProperty("webdriver.chrome.driver", getValueFromConfig(XMlEnum.GoogleBinary));

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	catch(Exception e){
		return null; //need to change for a exception
		
	}
	
	}
	public String getValueFromConfig(XMlEnum Value) throws Exception {
		File fXmlFile = new File("../PMTControllers/Resource/Config.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("config");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String content = eElement.getElementsByTagName(Value.toString()).item(0).getTextContent();
		if (content.isEmpty())
			throw new Exception("Cannot find the value in the resource file Config");

		return content;

	}

}
