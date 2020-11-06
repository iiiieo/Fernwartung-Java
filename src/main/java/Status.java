import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;

public class Status {
    private int mouseX, mouseY;
    private Robot robot;

    public Status() {
        this.mouseX = 0;
        this.mouseY = 0;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void typeKey(int keyEvent) {
        robot.keyPress(keyEvent);
        robot.keyRelease(keyEvent);
    }
    public void moveMouse(JSONObject json) {
        try {
            this.mouseX = json.getInt("mouseX");
            this.mouseY = json.getInt("mouseY");
            robot.mouseMove(mouseX, mouseY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

}
