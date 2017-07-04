package Actions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Common.Initial;
import Enums.commonEnum;
import objectRepository.MicrosoftLoginPage;



public class logActions{
	objectRepository.MicrosoftLoginPage login;
	WebDriver driver;
	
	public logActions(WebDriver driver) {
		this.driver=driver;
		login = new MicrosoftLoginPage();
		
	}

	public void login() throws Exception{
			
		//this pass and user should be set in a xml with usernames and passwords
		driver.findElement(login.username).sendKeys("julio.oropeza@intertecintl.com");
		driver.findElement(login.password).sendKeys("Roller.3!");
		driver.findElement(login.password).sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, 10);
	
		//this need to be set in a specific function in commons 
		wait.until(ExpectedConditions.elementToBeClickable(login.signIn));
			   
		driver.findElement(login.signIn).click();		
		/*
		WebElement element = driver.findElement(By.xpath("//*[@id='cred_sign_in_button']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);*/
		
		String pageTitle = driver.getTitle();		
		if(!pageTitle.equals(commonEnum.pageTitles.PerformanceEvaluations.toString()))
			throw new Exception("Page was not loaded");
	}
}
