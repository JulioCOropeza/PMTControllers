package Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Enums.XMlEnum;
import net.sourceforge.htmlunit.corejs.javascript.ast.ThrowStatement;

public class Initial {
	WebDriver driver;

	public Initial() {
	}

	public WebDriver getDriver() {
		try {

			// for firefox
			// System.setProperty("webdriver.firefox.bin","C:\\Program Files
			// (x86)\\Mozilla Firefox\\firefox.exe");
			// System.setProperty("webdriver.gecko.driver",
			// "C:\\Users\\Ariel\\workspace\\geckodriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setBinary(getValueFromConfig(XMlEnum.GoogleExe));
			System.setProperty("webdriver.chrome.driver", getValueFromConfig(XMlEnum.GoogleBinary));

			options.addArguments(getValueFromConfig(XMlEnum.ChromeLoadExtensionModHead));

			// add parameter which will disable the extension
			// options.addArguments("--disable-extensions-file-access-check");
			// options.addArguments("chrome.switches","--disable-extensions");

			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.get(getValueFromConfig(XMlEnum.ChromeExtensionModHead));
			// set the context on the extension so the localStorage can be
			// accessed

			String userFlag = "1"; // flag to look for into the .xls file
			Object[] modHeader = readModHeaderFile(userFlag, "Profiles");
			String fileHeader = modHeader[0].toString();
			String fileEmail = modHeader[1].toString();
			setConfigModHeader(fileHeader, fileEmail);

			return driver;
		} catch (Exception e) {

			return null; // need to change for a exception

		}

	}

	public void setConfigModHeader(String fileHeader, String fileEmail) throws Exception {
		// setup ModHeader with two headers (token1 and token2)
		((JavascriptExecutor) driver).executeScript("localStorage.setItem('profiles', JSON.stringify([{                "
				+ "  title: 'Selenium', hideComment: true, appendMode: '',           "
				+ "  headers: [                                                      " + "    {enabled: true, name: '"
				+ fileHeader + "', value: '" + fileEmail + "', comment: ''} "
				+ "  ],                                                              "
				+ "  respHeaders: [],                                                "
				+ "  filters: []                                                     "
				+ "}]));                                                             ");

		// return driver;
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

	public Object[] readModHeaderFile(String userFlag, String sSheetName) throws IOException {
		// userFlag = value to look for in the first column into the xlsx file

		Object[] tempHeader = null;
		try {
			tempHeader = readExcel(getValueFromConfig(XMlEnum.FileProfileModHead), sSheetName, userFlag);
		} catch (IOException e) {
			throw new IOException("Cannot find the Profile Header Configuration File");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempHeader;
	}

	// read an excel file looking for a row with a number defined by idRowGet in
	// the first cell
	// return an array
	public Object[] readExcel(String fileName, String sheetName, String idRowGet) throws IOException {

		// Create an object of File class to open xlsx file
		File file = new File(fileName);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook myWorkbook = null;

		// Find the file extension by splitting file name in substring and
		// getting only extension name
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class
			myWorkbook = new XSSFWorkbook(inputStream);
		}

		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class
			myWorkbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		org.apache.poi.ss.usermodel.Sheet mySheet = myWorkbook.getSheet(sheetName);

		// Find number of rows in excel file
		int rowCount = mySheet.getLastRowNum() - mySheet.getFirstRowNum();
		
		Object[] tempHeader = new Object[rowCount+1];

		// Create a loop over all the rows of excel file to read it
		for (int i = 0; i < rowCount + 1; i++) {

			Row row = mySheet.getRow(i);

			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {

				// Print Excel data in console
				System.out.print(row.getCell(j).getStringCellValue());

				if (row.getCell(0).getStringCellValue().compareTo(idRowGet) == 0 && j > 0) {
					tempHeader[j - 1] = row.getCell(j).getStringCellValue();
				}
			}
			System.out.println();
		}
		return tempHeader;
	}
}
