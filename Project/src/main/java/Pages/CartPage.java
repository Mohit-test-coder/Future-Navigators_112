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
    WebDriver driver;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    @FindBy(id  = "remove-sauce-labs-backpack")
    WebElement removeButton;
    
    @FindBy(css = ".shopping_cart_badge")
    WebElement cartBadge;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCheckout() {
        checkoutButton.click();
    }

    public void removeProduct() {
        removeButton.click();
    }
    
    public void removeProductFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Remove')]")));
        removeButton.click();
    }

    public boolean isCartPageDisplayed() {
        return checkoutButton.isDisplayed();
    }

    public int getCartItemCount() {
        return Integer.parseInt(cartBadge.getText());
    }

}
