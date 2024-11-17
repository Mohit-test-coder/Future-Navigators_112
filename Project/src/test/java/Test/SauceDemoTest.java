package Test;




import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.HomePage;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;


public class SauceDemoTest {
	 WebDriver driver;
	    LoginPage loginPage;
	    HomePage homePage;
	    CartPage cartPage;
	    CheckoutPage checkoutPage;
	    static ExtentReports reports;
	    static ExtentHtmlReporter htmlReporter;
	    ExtentTest test;
	    

	    @BeforeSuite
	    public void setUpReport() {
	        // Initialize ExtentReports only once before the test suite starts
	        reports = new ExtentReports();
	        htmlReporter = new ExtentHtmlReporter("extent.html");
	        reports.attachReporter(htmlReporter);
	    }
	    
	    @BeforeMethod
	    public void setUp() {
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get(ConfigReader.get("url"));

	        loginPage = new LoginPage(driver);
	        homePage = new HomePage(driver);
	        cartPage = new CartPage(driver);
	        checkoutPage = new CheckoutPage(driver);
	    }

    /**
     * Test Case 1: Valid Login and Add Products to Cart
     */
    @Test(priority = 1)
    public void validLoginAndAddToCartTest() throws InterruptedException{
    	test = reports.createTest("Valid Login and Add to Cart Test");
    	test.pass("Passing the UserName and Password");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding product to cart");
        homePage.addProductsToCart(2);
        test.pass("Navigating to the cartPage");
        homePage.goToCart();
        test.pass("Giving Asseration is CartPage is Display or Not");
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Failed to navigate to Cart Page");
        test.pass("Generating and saving the test report");
        reports.flush();
    }

    /**
     * Test Case 2: Invalid Login Test
     */
    @Test(priority = 2)
    public void invalidLoginTest() throws InterruptedException{
    	
    	test = reports.createTest("Invalid Login Test");
    	test.pass("Attempting login with invalid credentials");
        loginPage.login("invalid_user", "wrong_password");
        test.pass("Capturing the error message from login attempt");
        String errorMessage = loginPage.getErrorMessage();
        test.pass("Verifying that correct error message is displayed for invalid login");
        Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service", "Invalid login error message did not match");
        test.pass("Generating and saving the test report");
        reports.flush();
    }

    /**
     * Test Case 3: Add Multiple Products to Cart and Verify Count
     */
    @Test(priority = 3)
    public void addMultipleProductsTest() throws InterruptedException{
    	test = reports.createTest("Add Multiple Products to Cart Test");
    	test.pass("Logging in with credentials from config file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding 3 products to the shopping cart");
        homePage.addProductsToCart(3);
        test.pass("Getting the current cart item count");
        int cartCount = homePage.getCartItemCount();
        test.pass("Verifying cart count matches number of added products");
        Assert.assertEquals(cartCount, 3, "Cart item count mismatch after adding products");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 4: Remove Product from Cart
     * @throws InterruptedException 
     */
    @Test(priority = 4)
    public void removeProductFromCartTest() throws InterruptedException {
    	test = reports.createTest("Remove Product from Cart Test");
    	test.pass("Logging in with valid credentials from configuration");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding 2 products to the shopping cart");
        homePage.addProductsToCart(2);
        test.pass("Navigating to the cart page");
        homePage.goToCart();
        test.pass("Removing a product from the cart");
        cartPage.removeProductFromCart();
        test.pass("Getting updated cart item count");
        int cartCount = cartPage.getCartItemCount();
        test.pass("Verifying cart count is 1 after product removal");
        Assert.assertEquals(cartCount, 1, "Product was not removed from the cart");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 5: Checkout and Verify Success Message
     */
    @Test(priority = 5)
    public void verifyCheckoutFlowTest() throws InterruptedException{
    	test = reports.createTest("Verify Checkout Flow Test");
    	test.pass("Logging in with credentials from configuration file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding 2 items to the shopping cart");
        homePage.addProductsToCart(2);
        test.pass("Navigating to cart page");
        homePage.goToCart();
        test.pass("Proceeding to checkout");
        cartPage.clickCheckout();
        test.pass("Filling shipping details with user information");
        checkoutPage.fillDetails("John", "Doe", "12345");
        test.pass("Capturing order confirmation message");
        String successMessage = checkoutPage.getSuccessMessage();
        test.pass("Verifying successful order completion message");
        Assert.assertEquals(successMessage, "Thank you for your order!", "Success message did not match");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 6: Product Sorting by Price (Low to High)
     */
    @Test(priority = 6)
    public void validateProductSortingTest()throws InterruptedException {
    	test = reports.createTest("Product Sorting Test");
    	test.pass("Logging in with credentials from configuration file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Selecting sort option: Price low to high");
        homePage.selectSortOption("Price (low to high)");
        test.pass("Verifying product list price sorting order");
        boolean isSorted = homePage.verifyProductPriceSorting();
        test.pass("Validating products are correctly sorted by price in ascending order");
        Assert.assertTrue(isSorted, "Products are not sorted by price (Low to High)");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 7: Verify Logout Functionality
     * @throws InterruptedException 
     */
    @Test(priority = 7)
    public void verifyLogoutTest() throws InterruptedException {
    	test = reports.createTest("Verify Logout Functionality Test");
    	test.pass("Logging in with credentials from configuration file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Performing logout from the application");
        homePage.logout();
        test.pass("Verifying successful logout by checking login button presence");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        // Flush the report only once after all tests are complete
        if (reports != null) {
            reports.flush();
        }
    }
    
}
