import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class PaintTest {
    private static WindowsDriver paintSession = null;
    private static String DriverUrl = "http://127.0.0.1:4723";
    private static String paintAppId = "C:\\Windows\\System32\\mspaint.exe";
    private Draw draw;
    private WebElement canvas;
    public static String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", paintAppId);
            paintSession = new WindowsDriver(new URL(DriverUrl), capabilities);
            paintSession.manage().window().maximize();
            paintSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void cleanApp(){
        paintSession.quit();
        setUp();
    }

    @AfterSuite
    public void tearDown(){
        paintSession.close();
        paintSession.quit();
    }

    @Test
    public void drawTest() throws InterruptedException {

        //draw head
        paintSession.findElementByName("Oval").click();
        paintSession.findElementByName("Size").click();
        paintSession.findElementByName("3px").click();
        canvas = paintSession.findElementByName("Using Oval tool on Canvas");
        Actions actions1 = new Actions(paintSession);
        draw = new Draw(paintSession,canvas,actions1);
        draw.drawSimpleFigure(500,200,780,400);

        //draw ears
        draw.chooseElement("Line");
        draw.drawSimpleFigure(530,130,508,261);
        draw.drawSimpleFigure(573,213,530,130);
        draw.drawSimpleFigure(703,211,729,130);
        draw.drawSimpleFigure(765,254,729,130);

        //draw eyes
        draw.chooseElement("Oval");
        draw.drawSimpleFigure(538,248,612,282);
        draw.drawSimpleFigure(656,248,730,282);

        //draw mouth
        draw.chooseElement("Curve");
        draw.drawCurve(565,320,703,320,634,360);

        //draw nose
        draw.chooseElement("Triangle");
        draw.drawSimpleFigure(642,285,627,315);

        //draw pupils
        draw.chooseElement("Diamond");
        draw.drawSimpleFigure(582,256,567,276);
        draw.drawSimpleFigure(700,256,685,276);
        saveFile("Test picture");
    }

    public void saveFile(String fileName) {
        paintSession.findElementByName("File tab").click();
        paintSession.findElementByName("Save as").click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        paintSession.findElementByAccessibilityId("FileNameControlHost").sendKeys("%TEMP%"+fileName);
        try {
            paintSession.findElementByName("Save").click();
            paintSession.findElementByName("Confirm Save As").findElement(By.name("Yes")).click();
        }
        catch (Exception e)
        {}
    }
}
