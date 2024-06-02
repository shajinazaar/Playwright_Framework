package test;
import Base.ExcelDataReader;
import Base.Helper;
import Base.Locators;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class SampleTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    private static Helper helper;
    private static Locators locator;


    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        helper = new Helper(page);
        locator = new Locators();
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        // Return data from your ExcelDataReader
        String excelFilePath = System.getProperty("user.dir") + "\\src\\resources\\Data.xlsx";
        return ExcelDataReader.getDataFromExcel("LOGIN", excelFilePath);
    }


    @Test(dataProvider = "loginData", priority = 1)
    public void VerifyLogin(String username, String password) {
        login(username, password);
    }

    @Test(priority = 2, dependsOnMethods = "VerifyLogin")
    public void VerifyHomePage() {
        String homePageText = helper.getInnerText(helper.getByLocator(locator.HomeText));
        helper.wait(1000);
        Assert.assertEquals(homePageText, "Home");
        System.out.println(homePageText.contains("Home") ? "Login successful." : "Login failed.");
    }

    @Test(priority = 2, dependsOnMethods = "VerifyLogin")
    public void VerifyRepoLinks() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(1000);
        Locator ulLocator = helper.getByLocator(locator.ULElement);
        if (ulLocator != null) {
            List<ElementHandle> liElements = ulLocator.locator("li").elementHandles();
            System.out.println("Number of Repo links : " + liElements.size());
            for (ElementHandle liElement : liElements) {
                String textContent = liElement.textContent();
                System.out.println("Respository Links : " + textContent.trim());
            }
        } else {
            System.out.println("UL element not found.");
        }
    }

    @Test(priority = 3, dependsOnMethods = "VerifyLogin")
    public void VerifySecurityAlerts(){
        helper.click(helper.getByLocator(locator.notificationPageBtn));
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForTimeout(1000);
        int securityAlertCount = helper.getByLocator(locator.securityAlertText).count();
        if (securityAlertCount > 0) {
            System.out.println("Number of security alert found: " + securityAlertCount);
            System.out.println("At least one or more security alert is present on the page.");
        } else {
            System.out.println("No security alert found on the page.");
        }
    }

    private void login(String username, String password) {
        helper.navigate("https://github.com/login");
        helper.wait(5);
        Assert.assertEquals(helper.getInnerText(helper.getByLocator(locator.SignupText)), "Sign in to GitHub");
        helper.fill(helper.getById(locator.username), username);
        helper.fill(helper.getById(locator.password), password);
        helper.click(helper.getByLocator(locator.login_btn));
    }
}


