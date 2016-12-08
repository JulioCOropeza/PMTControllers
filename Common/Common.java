package Common;

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

}
