package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objects.SearchPage;
import org.openqa.selenium.WebDriver;
import utils.PropertyProvider;
import utils.WebDriverSetup;

import java.util.Properties;

public class SearchSteps {

	private Properties properties;
	private WebDriver driver;
	private SearchPage searchPage;

	@Before()
	public void beforeActivities() {
		properties = new PropertyProvider().getPropertyFile();
		driver = new WebDriverSetup().getDriver(properties);
	}

	@Given("I opened the Search page")
	public void iOpenedTheSearchPage() {
		driver.get(properties.getProperty("url.search"));
		searchPage = new SearchPage(driver);
		if(searchPage.cookiePopupIsDisplayed()){
			searchPage.handleCookiePreference(); }
	}

	@When("I search for {string} via the navigation bar")
	public void i_search_for_via_the_primary_navigation(String string) {
		searchPage.clickNavigationSearchIcon();
		searchPage.enterValueInNavigationSearchTextBoxAndSearch(string);
	}

	@Then("the Search Results page is opened")
	public void the_Search_Results_page_is_opened() {
		searchPage.assertNavigationToSearchResultsPage();
	}

	@Then("search results are shown")
	public void search_results_are_shown() {
		searchPage.assertFacetsItemsList();
		searchPage.assertSearchResultsList();
	}

	@When("I search for {string} via the search results page")
	public void i_search_for_via_the_search_results_page(String string) {
		searchPage.enterValueInSearchTextBox(string);
		searchPage.clickSearchIcon();
	}

	@Then("the results match the search term {string}")
	public void the_results_match_the_search_term(String string) {
		searchPage.assertSearchStringInResultItems(string);
	}

	@Then("the error message {string} is shown")
	public void the_error_message_is_shown(String string) {
		searchPage.assertErrorMessage(string);
	}

	@After()
	public void afterActivities() {
		driver.close();
	}
}
