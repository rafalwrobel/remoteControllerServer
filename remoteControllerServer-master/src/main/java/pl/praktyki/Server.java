package pl.praktyki;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {

    private int port;

    public Server(int port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
    }


    @Override
    protected int getPort() {
        return port;
    }
}
