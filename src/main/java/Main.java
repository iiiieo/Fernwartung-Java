import org.json.JSONException;
import org.json.JSONObject;

public class Main {
    private static Main main;
    //private final String URL = "http://localhost:3000/";
    private final String URL = "https://fernwartung-java.herokuapp.com/";
    private Connection connection;
    private String ID;
    private Status status;
    private Frame frame;

    public Main() {
        if (this.main == null) {
            this.main = this;
        }
        connection = new Connection(URL);
        this.status = new Status();
        this.frame = new Frame("Connecting...");
        connection.getEmitter().requestStatus();
        VideoStream stream = new VideoStream(connection);
        stream.start();
    }

    public static Main getMain() {
        return main;
    }

    public String getID() {
        return ID;
    }

    public void setID(JSONObject jsonId) {
        try {
            String id = jsonId.getString("id");
            String shortId = String.valueOf(jsonId.getInt("shortId"));

            System.out.println("ID::" + id+", "+"SHORTID::"+shortId);
            this.ID = id;
            frame.changeText(shortId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public static void main(String[] args) {
        System.out.println("Start");
        new Main();
    }

}
