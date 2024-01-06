package ankit.qa.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage{
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
	private WebElement productTitle;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\"Sauce Labs Backpack\"]")
	private WebElement slbTitle;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Price\" and @text=\"$29.99\"]")
	private WebElement slbPrice;
	
	public String getTitle() {
		String title = getAttribute(productTitle, "text", "Product Page Title is = ");
		return title;
	}
	
	public String getSLBTitle() {
		String productTitle = getAttribute(slbTitle, "text", "product title = ");
		return productTitle;
	}
	
	public String getSLBPrice() {
		String productPrice = getAttribute(slbPrice, "text", "product price = ");
		return productPrice;
	}
	
	public ProductDetailsPage pressSLBTitle() {
		click(slbTitle, "Navigate to the product details page");
		return new ProductDetailsPage();
	}

}
