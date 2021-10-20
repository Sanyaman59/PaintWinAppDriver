import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExcelTest {
    private static WindowsDriver excelSession = null;
    private String testVariable = "Han Navel";
    public static String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeMethod
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE");
            capabilities.setCapability("platformName","Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            excelSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            excelSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void cleanApp() throws InterruptedException {
        excelSession.close();
        excelSession.quit();
    }

    @Test
    public void fontChangeTest() {
        excelSession.findElementByName("Blank workbook").click();
        excelSession.findElementByName("\"C\" 3").click();
        excelSession.findElementByName("Font").findElement(By.name("Open")).click();
        excelSession.findElementByName("Algerian").click();
        excelSession.findElementByName("\"C\" 3").sendKeys("Sample text");
        excelSession.findElementByName("\"E\" 15").sendKeys(getDate());
        saveFile("Font change test");
    }

    @Test
    public void cellInsertionTest() {
        excelSession.findElementByName("Blank workbook").click();
        var b9 = excelSession.findElementByName("\"B\" 9");
        var d5 = excelSession.findElementByName("\"D\" 5");
        var c3 = excelSession.findElementByName("\"C\" 3");

        Actions addActions = new Actions(excelSession);
        addActions.doubleClick(b9).sendKeys(testVariable).sendKeys(Keys.ENTER);
        addActions.doubleClick(d5).sendKeys(testVariable).sendKeys(Keys.ENTER);
        addActions.doubleClick(c3).sendKeys(testVariable).sendKeys(Keys.ENTER);
        addActions.perform();

        excelSession.findElementByName("\"D\" 5").click();
        excelSession.findElementByName("\"D\" 5").sendKeys(Keys.DELETE);
        excelSession.findElementByName("\"D\" 5").sendKeys("1234554321");
        saveFile("Sell insertion test");
    }

    public void saveFile(String name) {
        excelSession.findElementByName("File Tab").click();
        excelSession.findElementByName("Save As").click();
        excelSession.findElementByName("Browse").click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        excelSession.findElementByAccessibilityId("FileNameControlHost").sendKeys(name);
        try {
            excelSession.findElementByName("Save").click();
            excelSession.findElementByName("Confirm Save As").findElement(By.name("Yes")).click();
        }
        catch (Exception e)
        {}
    }
}
