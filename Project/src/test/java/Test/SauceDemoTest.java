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
    // Declaring variables to represent different parts of the website (pages)
    WebDriver driver;               // To control the browser
    LoginPage loginPage;            // Login page object
    HomePage homePage;              // Home page object
    CartPage cartPage;              // Cart page object
    CheckoutPage checkoutPage;      // Checkout page object
    static ExtentReports reports;   // To generate test reports
    static ExtentHtmlReporter htmlReporter; // To format the test report
    ExtentTest test;                // Individual test reports

    // Before running any tests, set up the reporting system
    @BeforeSuite
    public void setUpReport() {
        reports = new ExtentReports(); // Creating a report
        htmlReporter = new ExtentHtmlReporter("extent.html"); // HTML format for easy viewing
        reports.attachReporter(htmlReporter); // Attaching the reporter
    }

    // Before each test, set up the browser and navigate to the website
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Set up the Chrome driver
        driver = new ChromeDriver(); // Open a new browser window
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(ConfigReader.get("url")); // Navigate to the URL (from a config file)

        // Initialize page objects to interact with the website's pages
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    /**
     * Test Case 1: Valid Login and Add Products to Cart
     * This test checks if a user can log in with correct credentials and add products to the cart.
     */
    @Test(priority = 1)
    public void validLoginAndAddToCartTest() throws InterruptedException{
        test = reports.createTest("Valid Login and Add to Cart Test"); // Creating a report for this test
        test.pass("Passing the UserName and Password"); // Logging in
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding product to cart"); // Adding products
        homePage.addProductsToCart(2); // Add 2 products to the cart
        test.pass("Navigating to the cartPage"); // Going to the cart page
        homePage.goToCart();
        test.pass("Giving Assertion to check if CartPage is Displayed or Not"); 
        // Verifying if the cart page appears
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Failed to navigate to Cart Page");
        test.pass("Generating and saving the test report"); // Saving the report after the test
        reports.flush();
    }

    /**
     * Test Case 2: Invalid Login Test
     * This test checks if the system correctly handles invalid login attempts.
     */
    @Test(priority = 2)
    public void invalidLoginTest() throws InterruptedException{
        test = reports.createTest("Invalid Login Test"); // Creating a report for this test
        test.pass("Attempting login with invalid credentials"); // Trying to log in with wrong details
        loginPage.login("invalid_user", "wrong_password"); // Invalid credentials
        test.pass("Capturing the error message from login attempt"); // Capture error message
        String errorMessage = loginPage.getErrorMessage();
        test.pass("Verifying that correct error message is displayed for invalid login"); 
        // Verifying the error message is correct
        Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service", 
                            "Invalid login error message did not match");
        test.pass("Generating and saving the test report"); // Saving the report after the test
        reports.flush();
    }

    /**
     * Test Case 3: Add Multiple Products to Cart and Verify Count
     * This test checks if the correct number of products is added to the cart.
     */
    @Test(priority = 3)
    public void addMultipleProductsTest() throws InterruptedException{
        test = reports.createTest("Add Multiple Products to Cart Test");
        test.pass("Logging in with credentials from config file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Adding 3 products to the shopping cart");
        homePage.addProductsToCart(3); // Adding 3 products to the cart
        test.pass("Getting the current cart item count"); 
        int cartCount = homePage.getCartItemCount(); // Getting the number of products in the cart
        test.pass("Verifying cart count matches number of added products");
        Assert.assertEquals(cartCount, 3, "Cart item count mismatch after adding products");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 4: Remove Product from Cart
     * This test checks if a product can be removed from the cart.
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
        cartPage.removeProductFromCart(); // Removing a product from the cart
        test.pass("Getting updated cart item count");
        int cartCount = cartPage.getCartItemCount(); // Checking the updated count
        test.pass("Verifying cart count is 1 after product removal");
        Assert.assertEquals(cartCount, 1, "Product was not removed from the cart");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 5: Checkout and Verify Success Message
     * This test checks if a user can successfully complete a checkout process.
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
        cartPage.clickCheckout(); // Clicking checkout
        test.pass("Filling shipping details with user information");
        checkoutPage.fillDetails("John", "Doe", "12345"); // Filling in user details
        test.pass("Capturing order confirmation message");
        String successMessage = checkoutPage.getSuccessMessage(); // Capturing the confirmation message
        test.pass("Verifying successful order completion message");
        Assert.assertEquals(successMessage, "Thank you for your order!", "Success message did not match");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 6: Product Sorting by Price (Low to High)
     * This test checks if products can be sorted by price from low to high.
     */
    @Test(priority = 6)
    public void validateProductSortingTest()throws InterruptedException {
        test = reports.createTest("Product Sorting Test");
        test.pass("Logging in with credentials from configuration file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Selecting sort option: Price low to high");
        homePage.selectSortOption("Price (low to high)");
        test.pass("Verifying product list price sorting order");
        boolean isSorted = homePage.verifyProductPriceSorting(); // Verifying the sorting
        test.pass("Validating products are correctly sorted by price in ascending order");
        Assert.assertTrue(isSorted, "Products are not sorted by price (Low to High)");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    /**
     * Test Case 7: Verify Logout Functionality
     * This test checks if the user can successfully log out of the application.
     */
    @Test(priority = 7)
    public void verifyLogoutTest() throws InterruptedException {
        test = reports.createTest("Verify Logout Functionality Test");
        test.pass("Logging in with credentials from configuration file");
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        test.pass("Performing logout from the application");
        homePage.logout(); // Logging out
        test.pass("Verifying successful logout by checking login button presence");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed");
        test.pass("Generating and saving the test execution report");
        reports.flush();
    }

    // After each test, close the browser
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }

    // After all tests are done, finalize the report
    @AfterSuite
    public void tearDownReport() {
        if (reports != null) {
            reports.flush(); // Save the final report
        }
    }
}
