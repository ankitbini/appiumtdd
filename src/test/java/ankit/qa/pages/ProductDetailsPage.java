package ankit.qa.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends MenuPage{
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]")
	private WebElement slbTitle;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private WebElement slbTxt;
	
	@AndroidFindBy(accessibility = "test-Price")
	private WebElement sblPrice;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"BACK TO PRODUCTS\"]")
	private WebElement backToProductBtn;
	
	
	public String getSLBTitle() {
		String title = getAttribute(slbTitle, "text","Product title on description page = ");
		return title;
	}
	
	public String getSLTxt() {
		String description = getAttribute(slbTxt, "text", "Product description on description page = ");
		return description;
	}
	
	public String getSBLPrice() {
		String price = getAttribute(sblPrice, "text", "Product price on description page = ");
		return price;
	}
	
	public String scrollToSBLPrice() {
		return getAttribute(scrollToElement(), "text");
	}
	
	public ProductsPage pressbackToProductBtn() {
		click(backToProductBtn, "Navigate back to product page");
		return new ProductsPage();
	}

}
