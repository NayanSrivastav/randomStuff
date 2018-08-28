package crawling;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Selenium {

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeDriver driver = getDriver();
//        WebDriver driver = null;
//        try {
//            driver = WebDriverFactory.getDriver();
//            driver.get("https://www.domain.com.au/sold-listings/");
//            parseDomain(driver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (Objects.nonNull(driver)) {
//                driver.quit();
//            }
//        }
        int i = 1;
        do {
            driver.get("https://www.realestate.com.au/sold/list-" + (i++));
        } while (parse(driver));
    }

    static ChromeDriver getDriver() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        return new ChromeDriver(options);
    }

    private static boolean parse(WebDriver driver) {
        if (hasData(driver)) {
//            document.getElementsByClassName('details-link residential-card__details-link')
            List<WebElement> results = driver.findElements(By.className("residential-card__address-heading"));
            for (WebElement element : results) {
                Document doc = Jsoup.parse(element.getAttribute("innerHTML"));
                doc.outputSettings().prettyPrint(true);
                doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
                JSONObject table = XML.toJSONObject(doc.toString());
                String href = table.getJSONObject("html").getJSONObject("body").getJSONObject("a").getString("href");
                parseRealState(href);
            }

            return true;
        }

        return false;
    }

    private static void parseRealState(String link) {
        link = "https://www.realestate.com.au" + link;
        ChromeDriver driver = getDriver();
        driver.get(link);
        String street = driver.findElements(By.className("property-info-address__street")).get(0).getAttribute("innerText");
        String address=driver.findElements(By.className("property-info-address__suburb")).get(0).getAttribute("innerText");
        String amount = driver.findElement(By.id("reavicalc_calculator_mortgagerepayments_ad_unit_in_page_responsive_sold_uplift-fd_propertyPrice")).getAttribute("value");
        PropertyDetails propertyDetails = new PropertyDetails();
        String[] addressChunks = address.split(" ");
        propertyDetails.setSuburbName(address);
        propertyDetails.setStreetName(street);
        propertyDetails.setPrice(amount);
        if (addressChunks.length > 0) {
            String postalCode = addressChunks[addressChunks.length - 1];
            if (postalCode.matches("\\d+")) {
                propertyDetails.setPostalCode(postalCode);
            }
        }

    }

    private static void parseDomain(WebDriver driver) {
//        List<WebElement> searchResults = driver.findElements(By.cssSelector("li.search-results__listing"));
//        System.out.println(searchResults.size());
//        WebElement webElement = searchResults.get(0);
        WebElement searchContainer = driver.findElement(By.cssSelector("ul.search-results__results"));

        if (Objects.nonNull(searchContainer)) {
            Document doc = Jsoup.parse(searchContainer.getAttribute("innerHTML"));
            doc.outputSettings().prettyPrint(true);
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            JSONObject table = XML.toJSONObject(doc.toString());
            JSONObject tableBody = table.getJSONObject("body");
            if (Objects.nonNull(tableBody)) {
                JSONArray list = tableBody.getJSONArray("li");
            }
            System.out.println(table.toString());

        }
    }

    private static boolean hasData(WebDriver driver) {
        try {
            List<WebElement> resultsWrapper = driver.findElements(By.className("noMatch"));

            for (WebElement e : resultsWrapper) {
                if (e.getTagName().equalsIgnoreCase("p")) {
                    if (e.getAttribute("innerHTML").contains("We couldn't find anything matching your search criteria")) {
                        return false;
                    }
                }
            }

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return true;
    }
}
