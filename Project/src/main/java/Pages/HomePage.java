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
    WebDriver driver;

    @FindBy(className = "btn_inventory")
    List<WebElement> products;

    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;
    
    @FindBy(css = ".product_sort_container")
    WebElement sortDropdown;
    
    @FindBy(css = ".inventory_item_price")
    List<WebElement> productPricesList;
    
    @FindBy(className  = "shopping_cart_badge")
    WebElement cartBadge;
    
    @FindBy(id = "react-burger-menu-btn")
    WebElement burgerMenuButton;
    
    @FindBy(id = "logout_sidebar_link")
    WebElement logoutButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addProductsToCart(int numberOfProducts) {
        for (int i = 0; i < numberOfProducts; i++) {
            products.get(i).click();
        }
    }

    public void goToCart() {
        cartIcon.click();
    }
    
    public void selectSortOption(String sortOption) {
        new Select(sortDropdown).selectByVisibleText(sortOption);
    }

    public boolean verifyProductPriceSorting() {
        List<Double> productPrices = new ArrayList<>();
        for (WebElement price : productPricesList) {
            productPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        List<Double> sortedPrices = new ArrayList<>(productPrices);
        Collections.sort(sortedPrices);
        return productPrices.equals(sortedPrices);
    }

    public int getCartItemCount() {
        return Integer.parseInt(cartBadge.getText());
    }

    public void logout() throws InterruptedException {
    	Thread.sleep(2000);
        burgerMenuButton.click();
        Thread.sleep(3000);
        logoutButton.click();
    }

}
