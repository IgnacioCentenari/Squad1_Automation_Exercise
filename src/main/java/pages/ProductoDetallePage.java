//
//package pages;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//public class ProductDetailsPage extends BasePage {
//
//    @FindBy(id = "quantity")
//    private WebElement inputQuantity;
//
//    @FindBy(css = "button.cart")
//    private WebElement btnAddToCart;
//
//    @FindBy(xpath = "//button[text()='Continue Shopping']")
//    private WebElement btnContinueShopping;
//
//    public ProductDetailsPage(WebDriver driver) {
//        super(driver);
//        PageFactory.initElements(driver, this);
//    }
//
//    public void ingresarCantidadYAgregar(int quantity) {
//        WebElement inputQty = wait.until(ExpectedConditions.visibilityOf(inputQuantity));
//        inputQty.clear();
//        inputQty.sendKeys(String.valueOf(quantity));
//
//        wait.until(ExpectedConditions.elementToBeClickable(btnAddToCart)).click();
//        clickContinueShopping();
//    }
//
//    public void clickContinueShopping() {
//        wait.until(ExpectedConditions.elementToBeClickable(btnContinueShopping)).click();
//    }
//}
//
//
