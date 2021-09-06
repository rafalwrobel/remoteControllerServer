package pl.praktyki;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {

    private ConnectionThread connectionThread = new ConnectionThread();
    private Consumer<Serializable> onReceiveCallback;

    public NetworkConnection(Consumer<Serializable> onReceiveCallback){
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public void startConnecion() throws Exception{
        connectionThread.start();
    }

    public void sendConnection(String data) throws Exception{
        connectionThread.out.println(data);
    }

    public void closeConnection() throws Exception{
        connectionThread.socket.close();
    }


    protected abstract int getPort();

    private class ConnectionThread extends Thread{

        private Socket socket;
        private PrintWriter out;

        @Override
        public void run() {
            try(ServerSocket server = new ServerSocket(getPort()); Socket socket = server.accept();
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {


                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);
                onReceiveCallback.accept("Connection success.");


                while(true){
                    String data = in.readLine();
                    onReceiveCallback.accept(data);
                }

            }
            catch (Exception e) {
                this.out.close();
                onReceiveCallback.accept("Connection closed.");
            }
        }
    }

}