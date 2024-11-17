package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    // Web elements (representing parts of the login page)
    @FindBy(id = "user-name") // Finding the "Username" input field by its ID
    WebElement username;

    @FindBy(id = "password") // Finding the "Password" input field by its ID
    WebElement password;

    @FindBy(id = "login-button") // Finding the "Login" button by its ID
    WebElement loginButton;
    
    @FindBy(xpath  = "//h3[@data-test='error']") // Finding the error message element using its XPath
    WebElement errorMessage;

    // Constructor to initialize the driver and set up the page objects
    public LoginPage(WebDriver driver) {
        this.driver = driver; // Assigning the WebDriver instance to the class
        PageFactory.initElements(driver, this); // Initializing all the elements on this page
    }

    // Method to perform login action by entering username and password
    public void login(String user, String pass) {
        // Typing the username and password into the respective fields
        username.sendKeys(user); 
        password.sendKeys(pass);
        loginButton.click(); // Clicking the login button to submit the login form
    }
    
    // Method to get the error message if login fails
    public String getErrorMessage() {
        return errorMessage.getText(); // Returning the error message displayed on the page
    }

    // Method to check if the login button is visible on the page
    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed(); // Returning true if the login button is visible, otherwise false
    }
}
