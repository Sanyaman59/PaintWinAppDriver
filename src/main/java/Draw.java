import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Draw {
    private WebElement canvas;
    private WindowsDriver paintSession;
    private Actions actions;
    public Draw(WindowsDriver paintSession,WebElement canvas,Actions actions)

    {
        this.paintSession = paintSession;
        this.canvas = canvas;
        this.actions = actions;
    }

    public void chooseElement(String name)
    {
        paintSession.findElementByName(name).click();
        var canvas = paintSession.findElementByName("Using "+name+" tool on Canvas");
    }

    public void drawSimpleFigure( int x1, int y1, int x2, int y2)
    {
        actions.moveToElement(canvas,x1,y1).clickAndHold();
        actions.moveToElement(canvas,x2,y2).release();
        actions.perform();
    }

    public void drawCurve(int x1,int y1,int x2,int y2,int x3,int y3)
    {
        actions.moveToElement(canvas,x1,y1).clickAndHold();
        actions.moveToElement(canvas,x2,y2).release();
        actions.moveToElement(canvas,x1,y1).clickAndHold();
        actions.moveToElement(canvas,x3,y3).release();
        actions.perform();
    }
}
