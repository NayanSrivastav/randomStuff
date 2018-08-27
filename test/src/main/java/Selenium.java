import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;

public class Selenium {

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();

        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        ChromeDriver driver = new ChromeDriver(options);
        int i = 1;
        do {
            driver.get("https://www.realestate.com.au/buy/list-" + (i++));
        } while (parse(driver));
    }

    private static boolean parse(ChromeDriver driver) {
        if (hasData(driver)) {
            WebElement results = driver.findElementById("searchResultsTbl");
            Document doc = Jsoup.parse(results.getAttribute("innerHTML"));
            doc.outputSettings().prettyPrint(true);
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            JSONObject table = XML.toJSONObject(doc.toString());
            System.out.println(table.getJSONObject("html").getJSONObject("body").getJSONArray("article").toString());

            return true;
        }

        return false;
    }

    private static boolean hasData(ChromeDriver driver) {
        try {
            List<WebElement> resultsWrapper = driver.findElementsByClassName("noMatch");

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
