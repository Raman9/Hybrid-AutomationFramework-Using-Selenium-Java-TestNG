package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import Base.baseTest;
import Utils.JsonUtil;
import driverFactory.driverManager;
import pageObjects.*;
import testdata.UserData;

public class E2EDataDrivenTest extends baseTest {

	HomePage homepage;
	LoginPage pageLogin;
	UserHomePage userhome;
	ProductsPage productpage;
	CartPage cartpage;
	CheckoutPage checkoutpage;
	PaymentPage paymentpage;
	OrderConfirmationPage orderconfirmpage;

	UserData myUser;

	@Test(priority = 1)
	public void testLogin() {
		log.info(">>> Step 1: Login Flow");

		homepage = new HomePage(driver);
		homepage.signupLogin();

		pageLogin = new LoginPage(driver);

		// Fetch User Data from JSON
		myUser = JsonUtil.getUser("user4");

		pageLogin.login(myUser.email, myUser.password);

		userhome = new UserHomePage(driver);
		String message = userhome.loggedIntext();

		Assert.assertTrue(message.contains("Logged in as"), "Login Failed! Expected text 'Logged in as' not found.");
		log.info("Login Successful for user: " + myUser.email);
	}

	@Test(priority = 2, dependsOnMethods = "testLogin")
	public void testAddProductsBySearch() throws InterruptedException {
		log.info(">>> Step 2: Add products by search");

		userhome.goToproductsPage();
		productpage = new ProductsPage(driver);

		// Fetch Search ID from JSON logic
		List<String> searchIds = Arrays.asList("search1");
		List<String> searchStrings = searchIds.stream().map(id -> JsonUtil.getProduct(id).name).toList();

		// Search
		productpage.searchProducts(searchStrings);
		productpage.submitSearch();
		Thread.sleep(2000); // Ideally replace with Explicit Wait

		// Add Searched Products
		List<String> productIds = Arrays.asList("product1", "product2");
		List<String> productNames = productIds.stream().map(id -> JsonUtil.getProduct(id).name).toList();

		List<String> addedProducts = productpage.addMultipleProductsToCart(productNames);

		log.info("Products added via search: " + addedProducts);
		Assert.assertFalse(addedProducts.isEmpty(), "No products were added from search results!");
	}

	@Test(priority = 3, dependsOnMethods = "testLogin")
	public void testAddProductsByFilter() throws InterruptedException {
		log.info(">>> Step 3: Add products by filter");

		userhome.goToproductsPage();

		String filterPageMessage = productpage.filterProduct();
		Assert.assertEquals(filterPageMessage, "WOMEN - DRESS PRODUCTS", "Filter page title mismatch!");

		// Add Filtered Products
		List<String> filterProductKeys = Arrays.asList("product3", "product4");
		List<String> filterProductNames = filterProductKeys.stream().map(id -> JsonUtil.getProduct(id).name).toList();

		List<String> addedFilterProducts = productpage.addFilterMultipleProduct(filterProductNames);

		log.info("Products added via filter: " + addedFilterProducts);
		Assert.assertFalse(addedFilterProducts.isEmpty(), "No products were added from filter results!");
	}

	@Test(priority = 4, dependsOnMethods = { "testAddProductsBySearch", "testAddProductsByFilter" })
	public void testVerifyCartAndAddress() {
		log.info(">>> Step 4: Cart and Address Verification");

		userhome.goToCartpage();
		cartpage = new CartPage(driver);

		Assert.assertEquals(cartpage.getCardTitle(), "Shopping Cart", "Not on Cart Page!");

		cartpage.proceedCheckoutbutton();
		checkoutpage = new CheckoutPage(driver);

		Assert.assertEquals(checkoutpage.getcheckoutPageTitle(), "Checkout", "Not on Checkout Page!");

		log.info("Verifying Delivery Address...");

		String expectedAddress = myUser.deliveraddress.replaceAll("\\r?\\n", " ").replaceAll("\\s+", " ").trim();

		String actualAddress = checkoutpage.deliveryAddress().replaceAll("\\r?\\n", " ").replaceAll("\\s+", " ").trim();
		log.info("Expected: " + expectedAddress);
		log.info("Actual:   " + actualAddress);

		Assert.assertEquals(actualAddress, expectedAddress, "Address mismatch on Checkout page!");
	}

	@Test(priority = 5, dependsOnMethods = "testVerifyCartAndAddress")
	public void testPlaceOrderAndPayment() {
		log.info(">>> Step 5: Payment Processing");

		checkoutpage.placeOrder();

		paymentpage = new PaymentPage(driver);
		Assert.assertEquals(paymentpage.getpaymentPageTitle(), "Payment", "Not on Payment Page!");

		paymentpage.confirmPayment("payment1");

		orderconfirmpage = new OrderConfirmationPage(driver);
		String placeOrderMessage = orderconfirmpage.orderPlaceMessage();

		Assert.assertTrue(placeOrderMessage.equalsIgnoreCase("ORDER PLACED!"), "Order Placed text mismatch!");

		String confirmMessage = orderconfirmpage.orderConfirmMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Congratulations! Your order has been confirmed!"),
				"Order Confirmation text mismatch!");
	}

	@Test(priority = 6, dependsOnMethods = "testPlaceOrderAndPayment")
	public void testInvoiceDownloadAndLogout() {
		log.info(">>> Step 6: Invoice Download & Logout");

		String fileNameToExpect = "invoice.txt";
		orderconfirmpage.invoiceDownload();

		log.info("Waiting for file to download...");
		boolean isDownloaded = driverManager.waitForFileDownload(fileNameToExpect, 15);

		Assert.assertTrue(isDownloaded, "File " + fileNameToExpect + " not found in download directory!");

		userhome.logOut();
		log.info("Logged Out Successfully");
	}
}