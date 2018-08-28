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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Selenium {

    public static void main(String[] args) throws IOException {
//        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
//
//        ChromeOptions options = new ChromeOptions();
//
//        options.addArguments("headless");
//        options.addArguments("window-size=1200x600");
//        ChromeDriver driver = new ChromeDriver(options);
        WebDriver driver = null;
        try {
            driver = WebDriverFactory.getDriver();
            driver.get("https://www.domain.com.au/sold-listings/");
            parseDomain(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(driver)) {
                driver.quit();
            }
        }
//        int i = 1;
//        do {
//            driver.get("https://www.realestate.com.au/buy/list-" + (i++));
//        } while (parse(driver));
    }

    private static boolean parse(WebDriver driver) {
        if (hasData(driver)) {
            WebElement results = driver.findElement(By.id("searchResultsTbl"));
            Document doc = Jsoup.parse(results.getAttribute("innerHTML"));
            doc.outputSettings().prettyPrint(true);
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            JSONObject table = XML.toJSONObject(doc.toString());
            System.out.println(table.getJSONObject("html").getJSONObject("body").getJSONArray("article").toString());

            return true;
        }

        return false;
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
