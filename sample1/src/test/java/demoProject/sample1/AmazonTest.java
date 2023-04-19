package demoProject.sample1;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AmazonTest {
	public static void main(String[] args) throws InterruptedException {

		// Set the Chrome browser driver path
		System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

		// Open the Chrome browser
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Navigate to the Amazon India website
		driver.get("https://www.amazon.in/");

		// Click on the Cart icon and verify the empty cart message
		WebElement cartIcon = driver.findElement(By.id("nav-cart"));
		cartIcon.click();
		WebElement emptyCartMessage = driver
				.findElement(By.xpath("//*[@id=\"sc-active-cart\"]/div/div/div[2]/div[1]/h2"));
		WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"a-autoid-0-announce\"]/span"));
		WebElement signUpButton = driver.findElement(By.xpath("//*[contains(text(),'Sign up now')]"));
		if (emptyCartMessage.isDisplayed() && signInButton.isDisplayed() && signUpButton.isDisplayed()) {
			System.out.println("Cart is empty with sign in and sign up buttons");
		}

		// Click on Electronics from dropdown menu and Search Iphone 13/14
		WebElement dropdown = driver.findElement(By.id("searchDropdownBox"));
		Select select = new Select(dropdown);

		// select.click();
		select.selectByVisibleText("Electronics");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Iphone 14");
		WebElement searchButton = driver.findElement(By.xpath("//input[@value='Go']"));
		searchButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 20);

		// Add to Cart above product and verify Added to Cart displayed with correct
		// Cart subtotal with item count.
		WebElement iphoneProduct = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2/a/span[contains(text(),'Phone')]")));

		Actions actions = new Actions(driver);

		actions.keyDown(Keys.CONTROL).click(iphoneProduct).keyUp(Keys.CONTROL).build().perform();

		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
		addToCartButton.click();
		WebElement addedToCartMessage = driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']"));
		if (addedToCartMessage.isDisplayed()) {
			System.out.println("Product added to cart");
		}

		Thread.sleep(8000);
		WebElement cartSubtotalMsg = driver.findElement(By.id("attach-accessory-cart-total-string"));
		// attach-accessory-cart-subtotal

		WebElement cartSubtotalPrice = driver.findElement(By.id("attach-accessory-cart-subtotal"));
		String subtotalString = cartSubtotalMsg.getText();

		System.out.println("Cart item count: " + subtotalString);
		System.out.println("Cart subtotal: " + cartSubtotalPrice);

		// Click on above product image available in Added to Cart section.
		driver.findElement(By.id("attach-close_sideSheet-link")).click();
		;
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Scroll up by 500 pixels
		jsExecutor.executeScript("window.scrollBy(0, -500)");

		// Click on Visit the Apple Store link, navigate to APPLEWATCH menu and click on
		// any Series 8 watch from submenu and Click on any related available watch and
		// perform Add to Cart 3 Units
		WebElement appleStoreLink = driver.findElement(By.xpath("//a[text()='Visit the Apple Store']"));

		jsExecutor.executeScript("arguments[0].click();", appleStoreLink);

		Assert.assertEquals(driver.getTitle(), "Amazon.in: Apple");

		WebElement appleWatchLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a//span[text()='Apple Watch'])[1]")));
		jsExecutor.executeScript("arguments[0].click();", appleWatchLink);

		WebElement series8Watch = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[text()='Apple Watch Series 8 (GPS)']/ancestor::a")));
		series8Watch.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		List<WebElement> appleWatch = driver
				.findElements(By.xpath("//div[@class='EditorialTile__innerContent__n92i8']/a"));

		appleWatch.get(0).click();

		// Click on any related available watch and add to cart 3 units
		WebElement addToCartButtonAppleWatch = driver.findElement(By.id("add-to-cart-button"));
		for (int i = 0; i < 2; i++) {
			addToCartButtonAppleWatch.click();
			Thread.sleep(2000);
			driver.findElement(By.id("attach-close_sideSheet-link")).click();
			Thread.sleep(1000); // Wait for the cart to update
		}

		// Verify Added to Cart section displays correct product details
		addToCartButtonAppleWatch.click();
		WebElement addedToCartSection = driver.findElement(By.xpath("//*[@class='huc-image-wrapper sw-fallback']"));
		Assert.assertEquals(addedToCartSection.isDisplayed(), true);
		driver.findElement(By.id("attach-close_sideSheet-link")).click();

		// Click on the Cart icon
		js.executeScript("window.scrollBy(0,-500)");
		WebElement cartIconSection = driver.findElement(By.id("nav-cart"));
		jsExecutor.executeScript("arguments[0].click();", cartIconSection);

		// Search for Dell Laptop and add to cart 2 units First Laptop
		WebElement searchInput = driver.findElement(By.id("twotabsearchtextbox"));
		searchInput.sendKeys("Dell Laptop");
		searchInput.sendKeys(Keys.RETURN);

		List<WebElement> firstLaptop = driver.findElements(By.xpath(
				"//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']"));
		firstLaptop.get(0).click();
		driver.switchTo().window(driver.getWindowHandles().toArray()[2].toString());
		js.executeScript("window.scrollBy(0,-500)");
		WebElement quantity = driver.findElement(By.xpath("(//*[@name='quantity'])[2]"));
		Select select1 = new Select(quantity);

		select1.selectByVisibleText("3");
		WebElement addToCartButton3 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"add-to-cart-button\"]")));
		addToCartButton3.click();
		// WebElement
		// addedProductMsg=driver.findElement(By.xpath("//*[@id=\"attachDisplayAddBaseAlert\"]/span"));
		// Assert.assertTrue(addedProductMsg.getText().contains("Added to Cart"));

		js.executeScript("window.scrollBy(0,-500)");
		Thread.sleep(5000);
		WebElement cartIconSection1 = driver.findElement(By.id("nav-cart"));

		jsExecutor.executeScript("arguments[0].click();", cartIconSection1);
		WebElement product1 = driver
				.findElement(By.xpath("(//*[@class='a-truncate sc-grid-item-product-title a-size-base-plus'])[1]"));
		WebElement productPrice = driver.findElement(By.xpath(
				"(//*[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold'])[1]"));
		WebElement quantityDropdown1 = driver.findElement(By.xpath("(//*[@class='a-dropdown-prompt'])[1]"));
		String quanitytnumber1 = quantityDropdown1.getText();
		String price1 = productPrice.getText().replaceAll(",", "");
		double doublenumber1 = Double.parseDouble(quanitytnumber1);
		System.out.println(price1);
		Assert.assertTrue(product1.getText().contains("Dell Vostro"));

		WebElement product2 = driver
				.findElement(By.xpath("(//*[@class='a-truncate sc-grid-item-product-title a-size-base-plus'])[2]"));
		Assert.assertTrue(product2.getText().contains("Apple Watch"));
		WebElement productPrice2 = driver.findElement(By.xpath(
				"(//*[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold'])[2]"));
		WebElement quantityDropdown2 = driver.findElement(By.xpath("(//*[@class='a-dropdown-prompt'])[2]"));
		String quanitytnumber2 = quantityDropdown2.getText();
		String price2 = productPrice2.getText().replaceAll(",", "");
		double doublenumber2 = Double.parseDouble(quanitytnumber2);
		String productprice2 = productPrice2.getText().replaceAll(",", "");
		System.out.println(productprice2);

		WebElement product3 = driver
				.findElement(By.xpath("(//*[@class='a-truncate sc-grid-item-product-title a-size-base-plus'])[3]"));
		Assert.assertTrue(product3.getText().contains("Apple iPhone"));
		WebElement productPrice3 = driver.findElement(By.xpath(
				"(//*[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold'])[3]"));
		WebElement quantityDropdown3 = driver.findElement(By.xpath("(//*[@class='a-dropdown-prompt'])[3]"));
		String quanitytnumber3 = quantityDropdown3.getText();
		String price3 = productPrice3.getText().replaceAll(",", "");
		double doublenumber3 = Double.parseDouble(quanitytnumber3);

		System.out.println(price3);

		Double TotalPrice = (Double.parseDouble(price1) * doublenumber1) + (Double.parseDouble(price2) * doublenumber2)
				+ (Double.parseDouble(price3) * doublenumber3);
		System.out.println("Total price is" + TotalPrice);

		// Assert.assertEquals(dellLaptop.isDisplayed(), true);
		// Click on the Cart icon
		WebElement cartIcon3 = driver.findElement(By.id("nav-cart"));
		cartIcon3.click();

		WebElement subtotalText = driver.findElement(By.xpath("//*[@id=\"sc-subtotal-amount-activecart\"]/span"));
		String subtotalPrice = subtotalText.getText().replace(",", "");
		String newPrice = String.valueOf(TotalPrice);
		Assert.assertEquals(newPrice.replace(".0", "").trim(), subtotalPrice.replace(".00", "").trim());
		System.out.println("Subtotal price" + subtotalPrice);

		WebElement cartIcon4 = driver.findElement(By.id("nav-cart"));
	
		cartIcon4.click();

		WebElement quantityDropdown = driver.findElement(By.xpath("(//*[@class='a-dropdown-prompt'])[2]"));
		String quanitytnumber = quantityDropdown.getText();
		double doublenumber = Double.parseDouble(quanitytnumber);
		double reduced = doublenumber - 1;
		quantityDropdown.click();

		List<WebElement> quantityEntry = driver.findElements(By.xpath("//*[@class=\"a-nostyle a-list-link\"]/li"));
		for (int i = 0; i < quantityEntry.size(); i++) {
			if (quantityEntry.get(i).getText().equals(String.valueOf(reduced).replace(".0", "")));
				 quantityEntry.get(i).click();

		}
		driver.close();

	}
	
	

}
