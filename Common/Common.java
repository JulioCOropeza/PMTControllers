package Common;

import java.io.IOException;

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
	
}
