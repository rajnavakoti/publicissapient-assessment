package objects;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;

    static By cookieSelectionPopupLocator = By.id("onetrust-pc-sdk");
    static By acceptAllButtonLocator = By.cssSelector(".save-preference-btn-handler");
    static By searchTextBoxLocator = By.cssSelector(".c-search-form--input");
    static By searchIconLocator = By.cssSelector("button.c-search-form--icon-submit");
    static By navigationSearchTextBoxLocator = By.id("searchbar");
    static By navigationSearchIconLocator = By.xpath("//label[contains(text(),'Search')]");
    static By facetListItemsLocator = By.cssSelector(".c-search-facets--list > li");
    static By resultItemsLocator = By.cssSelector(".c-search-results--items > li");
    static By searchResultsMessage = By.cssSelector(".c-search-result--message");
    static By resultGridLocator = By.cssSelector("div.l-grid--w-100pc-s");

    public static List<WebElement> resultItems;

    public SearchPage(WebDriver driver) {
        this.driver = driver; wait = new WebDriverWait(driver,10); }

    public Boolean cookiePopupIsDisplayed(){
        return driver.findElement(cookieSelectionPopupLocator).isDisplayed(); }

    public void handleCookiePreference()  {
        driver.findElement(acceptAllButtonLocator).click(); driver.navigate().refresh(); }

    public void enterValueInSearchTextBox(String searchString) {
        driver.findElement(searchTextBoxLocator).sendKeys(searchString); }

    public void clickSearchIcon() {
        driver.findElement(searchIconLocator).click(); }

    public void clickNavigationSearchIcon() {
        driver.findElement(navigationSearchIconLocator).click(); }

    public void enterValueInNavigationSearchTextBoxAndSearch(String searchString) {
        driver.findElement(navigationSearchTextBoxLocator).sendKeys(searchString + Keys.ENTER); }

    public void assertFacetsItemsList() {
        Assert.assertTrue(driver.findElements(facetListItemsLocator).size()!= 0); }

    public void assertSearchResultsList() {
        Assert.assertTrue(driver.findElements(resultItemsLocator).size()!= 0); }

    public List<WebElement> getFacetListItems()  {
        return driver.findElements(facetListItemsLocator); }

    public List<WebElement> getResultItems() {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(resultGridLocator)));
        return driver.findElements(resultItemsLocator); }

    public void assertErrorMessage(String expectedErrorMessage){
        Assert.assertEquals(expectedErrorMessage,driver.findElement(searchResultsMessage).getText()); }

    public void assertSearchStringInResultItems(String searchString){
        for(WebElement facetItem : getFacetListItems()){
                facetItem.click();
                resultItems = getResultItems();
                for (WebElement resultItem : resultItems) {
                    try {
                        Assert.assertTrue(resultItem.getText().contains(searchString));
                    } catch(StaleElementReferenceException e) {
                       resultItems = getResultItems();
                    } catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println(e.toString()); } } } }

    public void assertNavigationToSearchResultsPage(){
        Assert.assertTrue(driver.findElement(resultItemsLocator).isDisplayed()); }
}
