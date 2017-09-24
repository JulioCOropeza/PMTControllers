package Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import Enums.XMlEnum;

public class Common extends Initial {
	WebDriver driver;

	public Common(WebDriver driver) {
		this.driver = driver;

	}

	public void OpenURL() throws Exception {
		driver.get(getValueFromConfig(XMlEnum.URL));
	}

	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

	public void closeChromeExtension (){
/*		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		try {
			Runtime.getRuntime().exec("C:\\uti\\Dev\\automation\\AutoIt3\\mySource\\focusBrowserTest.exe");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public void closeSavePasswordPopUp (){

		try {
			Runtime.getRuntime().exec("C:\\uti\\Dev\\automation\\AutoIt3\\mySource\\closeSavePasswordPopUp.exe");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


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
