package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    // WebDriver is used to control browser actions.
    WebDriver driver;

    // Finds and stores the "Checkout" button on the cart page.
    @FindBy(id = "checkout")
    WebElement checkoutButton;

    // Finds and stores the "Remove" button for a specific product (e.g., "Sauce Labs Backpack").
    @FindBy(id = "remove-sauce-labs-backpack")
    WebElement removeButton;
    
    // Finds and stores the cart icon badge that shows the number of items in the cart.
    @FindBy(css = ".shopping_cart_badge")
    WebElement cartBadge;

    // Constructor that initializes the page elements for the provided WebDriver instance.
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method clicks the "Checkout" button to proceed with the purchase.
     */
    public void clickCheckout() {
        checkoutButton.click();  // Clicks the button to move to the checkout page.
    }

    /**
     * This method clicks the "Remove" button to delete a specific product from the cart.
     */
    public void removeProduct() {
        removeButton.click();  // Removes the "Sauce Labs Backpack" from the cart.
    }

    /**
     * This method waits for the "Remove" button to be clickable and removes an item from the cart.
     */
    public void removeProductFromCart() {
        // Waits until the "Remove" button is clickable before clicking it.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Remove')]")));
        removeButton.click();  // Clicks to remove the item from the cart.
    }

    /**
     * Checks if the Cart Page is visible by verifying the display of the "Checkout" button.
     * 
     * @return True if the "Checkout" button is displayed, otherwise false.
     */
    public boolean isCartPageDisplayed() {
        return checkoutButton.isDisplayed();  // Confirms that the cart page is open.
    }

    /**
     * Retrieves the number of items currently in the cart by reading the cart badge.
     * 
     * @return The number of items in the cart as an integer.
     */
    public int getCartItemCount() {
        return Integer.parseInt(cartBadge.getText());  // Reads the cart badge to get item count.
    }
}
