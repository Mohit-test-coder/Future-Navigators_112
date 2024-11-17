package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    // WebDriver helps interact with the browser
    WebDriver driver;

    // Identifies and stores the "First Name" field on the checkout page
    @FindBy(id = "first-name")
    WebElement firstName;

    // Identifies and stores the "Last Name" field on the checkout page
    @FindBy(id = "last-name")
    WebElement lastName;

    // Identifies and stores the "Postal Code" field on the checkout page
    @FindBy(id = "postal-code")
    WebElement postalCode;

    // Identifies and stores the "Continue" button to proceed with checkout
    @FindBy(id = "continue")
    WebElement continueButton;

    // Identifies and stores the "Finish" button to complete the order
    @FindBy(id = "finish")
    WebElement finishButton;

    // Identifies and stores the success message displayed after completing the checkout
    @FindBy(className = "complete-header")
    WebElement successMessage;

    // Constructor that initializes the elements defined above for the WebDriver provided
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method fills in the user's shipping details and completes the checkout.
     * 
     * @param fName - The user's first name
     * @param lName - The user's last name
     * @param pCode - The user's postal code
     */
    public void fillDetails(String fName, String lName, String pCode) {
        firstName.sendKeys(fName);        // Enters the first name
        lastName.sendKeys(lName);         // Enters the last name
        postalCode.sendKeys(pCode);       // Enters the postal code
        continueButton.click();           // Clicks 'Continue' to proceed
        finishButton.click();             // Clicks 'Finish' to complete the order
    }

    /**
     * This method retrieves the success message shown after checkout.
     * 
     * @return A string containing the success message
     */
    public String getSuccessMessage() {
        return successMessage.getText();  // Retrieves the text of the success message
    }
}
