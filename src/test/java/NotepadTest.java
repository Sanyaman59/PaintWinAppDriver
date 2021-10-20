import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class NotepadTest {
    private static WindowsDriver notepadSession = null;
    private static String DriverUrl = "http://127.0.0.1:4723";
    private static String NotepadAppId = "C:\\Windows\\System32\\notepad.exe";
    public static String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }
    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", NotepadAppId);
            //capabilities.setCapability("platformName","Windows");
            //capabilities.setCapability("deviceName", "WindowsPC");
            notepadSession = new WindowsDriver(new URL(DriverUrl), capabilities);
            notepadSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterMethod
    public void cleanApp(){
        notepadSession.quit();
        setUp();
    }
    @AfterSuite
    public void tearDown(){
        notepadSession.close();
        notepadSession.quit();
    }
    @Test
    public void checkAboutWindow() {
        notepadSession.findElementByName("Help").click();
        notepadSession.findElementByName("About Notepad").click();
        notepadSession.findElementByName("OK").click();
    }
    @Test
    public void sendTestText(){
        notepadSession.findElementByClassName("Edit").sendKeys(getDate());
        notepadSession.findElementByClassName("Edit").clear();
    }
    @Test()
    public void pressTimeAndDateButton() throws InterruptedException {
        Assert.assertEquals("Untitled - Notepad",notepadSession.getTitle());
        notepadSession.findElementByName("Edit").click();
        notepadSession.findElementByAccessibilityId("26").click();
        Assert.assertNotNull(notepadSession.findElementByClassName("Edit"));
        notepadSession.findElementByName("File").click();
        notepadSession.findElementByXPath("//MenuItem[starts-with(@Name, \"Save As\")]").click();
        Thread.sleep(2000);
        notepadSession.findElementByAccessibilityId("FileNameControlHost").sendKeys("Notepad test file");
        notepadSession.findElementByName("Save").click();
        notepadSession.findElementByName("Confirm Save As").findElement(By.name("Yes")).click();
    }
}
