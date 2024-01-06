package ankit.qa.pages;

import org.openqa.selenium.WebElement;

import ankit.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPage extends BaseTest{
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
	private WebElement setingBtn;
	
	public SettingsPage pressSettingBtn() {
		click(setingBtn, "Press Setting Btn");
		return new SettingsPage();
	}

}
