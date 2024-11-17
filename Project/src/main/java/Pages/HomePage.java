package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage {
    // The main tool that allows interaction with the browser
    WebDriver driver;

    // Identifies and stores all 'Add to Cart' buttons for products on the page
    @FindBy(className = "btn_inventory")
    List<WebElement> products;

    // Identifies and stores the shopping cart icon
    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;
    
    // Identifies and stores the dropdown menu to sort products by price or other options
    @FindBy(css = ".product_sort_container")
    WebElement sortDropdown;
    
    // Identifies and stores the list of product prices displayed on the page
    @FindBy(css = ".inventory_item_price")
    List<WebElement> productPricesList;
    
    // Identifies and stores the cart badge, which shows the number of items in the cart
    @FindBy(className = "shopping_cart_badge")
    WebElement cartBadge;
    
    // Identifies and stores the menu button to access additional options like logout
    @FindBy(id = "react-burger-menu-btn")
    WebElement burgerMenuButton;
    
    // Identifies and stores the logout button within the menu
    @FindBy(id = "logout_sidebar_link")
    WebElement logoutButton;

    // Constructor that sets up the web elements defined above for use with the provided WebDriver
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Adds a specified number of products to the shopping cart
    public void addProductsToCart(int numberOfProducts) {
        for (int i = 0; i < numberOfProducts; i++) {
            products.get(i).click();  // Clicks 'Add to Cart' button for each product
        }
    }

    // Navigates to the shopping cart page to view items added
    public void goToCart() {
        cartIcon.click();  // Clicks on the cart icon to open the cart page
    }
    
    // Sorts the products on the page based on a given sorting option, such as price (low to high)
    public void selectSortOption(String sortOption) {
        new Select(sortDropdown).selectByVisibleText(sortOption);  // Chooses a sorting option from the dropdown menu
    }

    // Verifies if the products on the page are sorted by price in ascending order
    public boolean verifyProductPriceSorting() {
        // Collects all product prices on the page into a list
        List<Double> productPrices = new ArrayList<>();
        for (WebElement price : productPricesList) {
            productPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        // Creates a sorted version of the list to compare with the original list
        List<Double> sortedPrices = new ArrayList<>(productPrices);
        Collections.sort(sortedPrices);
        return productPrices.equals(sortedPrices);  // Returns true if prices are sorted in ascending order
    }

    // Gets the number of items currently in the shopping cart
    public int getCartItemCount() {
        return Integer.parseInt(cartBadge.getText());  // Reads the number from the cart badge and returns it
    }

    // Logs the user out of their account
    public void logout() throws InterruptedException {
        Thread.sleep(2000);  // Waits briefly to avoid clicking too quickly
        burgerMenuButton.click();  // Opens the menu with additional options
        Thread.sleep(3000);  // Waits to ensure the menu loads completely
        logoutButton.click();  // Clicks the logout button to sign out
    }

}
