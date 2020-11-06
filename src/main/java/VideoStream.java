import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

public class VideoStream extends Thread {

    private final Dimension screenSize;
    private final Rectangle screenRectangle;
    private Connection connection;
    private Robot robot;
    private int fps;

    public VideoStream(Connection connection) {
        this.connection = connection;
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenRectangle = new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.fps = 40;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            BufferedImage screenCapture = robot.createScreenCapture(screenRectangle);
            String imageBase65String = encodeToBase64(screenCapture, "jpeg");
            connection.getEmitter().sendBasse64Image(imageBase65String);
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeToBase64(final BufferedImage img, final String formatName) {
        String encodedString = "";
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, formatName, os);
            encodedString = Base64.getEncoder().encodeToString(os.toByteArray());
            os.flush();
            os.close();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        return encodedString;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
