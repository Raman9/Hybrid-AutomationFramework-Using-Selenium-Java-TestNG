package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.actionUtils;
import Utils.waitUtils;

public class ProductsPage {

    WebDriver driver;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "search_product")
    private WebElement searchBar;

    @FindBy(id = "submit_search")
    private WebElement submitSearch;

    @FindBy(xpath = "//button[contains(text(),'Continue Shopping')]")
    private WebElement continueshopping;
    
    @FindBy(xpath = "//a[normalize-space()='Women']")
    private WebElement womenCategory;
    
    @FindBy(xpath = "//div[@id='Women']//div//a[contains(text(),'Dress')]")
    private WebElement womenDress;
    
    @FindBy(xpath = "//h2[normalize-space()='Women - Dress Products']")
    private WebElement filterPage;
    

    // Search for a product
    public void searchProduct(String productName) {
        waitUtils.waitForVisibility(driver, searchBar, 5);
        searchBar.clear();
        searchBar.sendKeys(productName);
        
    }
    
    // search using JSON 
    public List<String> searchProducts(List<String> productNames) {
        List<String> searchedProducts = new ArrayList<>();
        
        for (String name : productNames) {
            
            searchProduct(name);
            
            
            searchedProducts.add(name); 
        }
        return searchedProducts;
    }
    

    public void submitSearch() {
        waitUtils.waitForClickable(driver, submitSearch, 5);
        submitSearch.click();
    }
 
    // Add a single product to cart
    public String addProductToCart(String productName) throws InterruptedException {
        actionUtils.scrollDown(driver);

        // Refetch product list each time
        List<WebElement> products = driver.findElements(By.cssSelector(".product-image-wrapper"));

        for (WebElement product : products) {

            String name = product.findElement(By.cssSelector(".productinfo p")).getText().trim();

            if (name.equalsIgnoreCase(productName)) {

                // Scroll and hover to reveal overlay
                actionUtils.scrollToElement(driver, product);
                Actions actions = new Actions(driver);
                actions.moveToElement(product).perform();
                Thread.sleep(300); // wait for overlay animation

                // Click Add to Cart
                WebElement addToCartButton = product.findElement(
                    By.xpath(".//div[contains(@class,'product-overlay')]//a[contains(text(),'Add to cart')]")
                );
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOf(addToCartButton));
                addToCartButton.click();
                
                //waitUtils.waitForClickable(driver, addToCartButton, 20);
                //actions.moveToElement(addToCartButton).click().perform();

                // Wait for modal overlay
                //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement ccontinueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='cartModal']//button[contains(text(),'Continue Shopping')]")
                ));
                WebElement continueBtn = waitUtils.waitForClickable(driver, ccontinueBtn, 20);
                // Click Continue Shopping and wait for modal to disappear
                continueBtn.click();
                wait.until(ExpectedConditions.invisibilityOf(continueBtn));

                // Small wait to stabilize page before next product
                Thread.sleep(500);

                return name;
            }
        }

        throw new RuntimeException("Product not found: " + productName);
    }

    // Add multiple products to cart
    public List<String> addMultipleProductsToCart(List<String> productNames) throws InterruptedException {
        List<String> addedProducts = new ArrayList<>();

        for (String name : productNames) {
            addedProducts.add(addProductToCart(name));
        }

        return addedProducts;
    }

    
    // add product by filtering 
    public String filterProduct() {
    	waitUtils.waitForClickable(driver, womenCategory, 10);
    	womenCategory.click();
    	waitUtils.waitForClickable(driver, womenDress, 10);
    	womenDress.click();
    	String message = waitUtils.waitForVisibility(driver, filterPage, 3).getText();
    	return message;
    	
    }
   public List<String> addFilterMultipleProduct(List<String> filterProductNames) throws InterruptedException {
	   List<String> addedFilterProducts = new ArrayList<>();
	   
	  for( String name :filterProductNames )
	  {
		  addedFilterProducts.add(addProductToCart(name));
		  
	  }
	   return filterProductNames;
	   
	   
   }

}
