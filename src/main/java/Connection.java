import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Connection {
    private Socket socket;
    private ConnectionHandler handler;
    private ConnectionEmitter emitter;

    public Connection(String url) {
        try {
            socket = IO.socket(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.handler = new ConnectionHandler(this);
        this.emitter = new ConnectionEmitter(this);
        socket.connect();
    }

    public ConnectionHandler getHandler() {
        return handler;
    }

    public void setHandler(ConnectionHandler handler) {
        this.handler = handler;
    }

    public ConnectionEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(ConnectionEmitter emitter) {
        this.emitter = emitter;
    }

    public class ConnectionEmitter {
        private Connection connection;

        public ConnectionEmitter(Connection connection) {
            this.connection = connection;
        }

        public void sendBasse64Image(String imageBase64String) {
            socket.emit("stream", imageBase64String);
        }

        public void requestStatus() {
            socket.emit("requestStatus", Main.getMain().getID());
        }
    }

    public class ConnectionHandler {
        public ConnectionHandler(Connection connection) {
            handleSocketEvents();
        }

        private void handleSocketEvents() {
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected");
                    socket.emit("username", System.getProperty("user.name"));
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected");
                    Main.getMain().getFrame().changeText("Connecting...");
                }
            }).on("clientId", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonId = (JSONObject) args[0];
                    Main.getMain().setID(jsonId);
                }
            }).on("pressKey", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    int keyEvent = (int) args[0];
                    Main.getMain().getStatus().typeKey(keyEvent);
                }
            }).on("moveMouse", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject json = (JSONObject) args[0];
                    Main.getMain().getStatus().moveMouse(json);
                }
            });
        }
    }
}
