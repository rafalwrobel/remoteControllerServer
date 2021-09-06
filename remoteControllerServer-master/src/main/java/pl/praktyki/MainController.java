package pl.praktyki;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {

    @FXML
    private Label ipAddress;

    @FXML
    private Label portNumber;

    @FXML
    private Button stopServer;

    @FXML
    private Button startServer;

    @FXML
    private TextArea messages;

    private NetworkConnection connection = createServer();

    public void initialize() {

        InetAddress addr;
        try {
            addr = Inet4Address.getLocalHost();
            ipAddress.setText(addr.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ipAddress.setText("Problem with IP... ");
        }

        portNumber.setText("33914");

        startServer.setOnAction(actionEvent -> {
            try {
                connection.startConnecion();
                messages.appendText("Server: Start server. " + " [" + getTime() +"]\n");
            } catch (Exception e) {
                e.printStackTrace();
                messages.appendText("Server: Problem with start server. " + " [" + getTime() +"]\n");
            }

        });

        stopServer.setOnAction(actionEvent -> {
            try {
                connection.closeConnection();
                messages.appendText("Server: Close server. "+ " [" + getTime() +"]\n");
            } catch (Exception e) {
                e.printStackTrace();
                messages.appendText("Server: Problem with close server. " + " [" + getTime() +"]\n");
            }
        });

    }
    private Server createServer(){
        return new Server(33914, data -> {
            Platform.runLater(() -> {

                messages.appendText("Client: " + data.toString() + " [" + getTime() +"]\n");

                if(tryParse(data.toString()) != null)
                {
                    int time = tryParse(data.toString());
                    shutdown(time);
                }
                else
                {
                    switch(data.toString()) {
                        case "up": {
                            int i = 1;
                            changeVolume(i);
                            break;
                        }
                        case "down": {
                            int i = 2;
                            changeVolume(i);
                            break;
                        }
                        case "mute": {
                            int i = 3;
                            changeVolume(i);
                            break;
                        }
                        case "now": {
                            shutdownNow();
                            break;
                        }

                        case "cancel": {
                            shutdownCancel();
                            break;
                        }
                    }
                }



            });
        });
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void shutdown(int time) throws RuntimeException{

        String shutdownCommand = "shutdown.exe -s -t " + time;
        //System.out.println(shutdownCommand);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(shutdownCommand);
            messages.appendText("Server: Komputer zostanie wyłączony za " + time + " sekund... " + " [" + getTime() +"]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void shutdownCancel(){
        String shutdownCommand = "shutdown.exe -a ";
        //System.out.println(shutdownCommand);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(shutdownCommand);
            messages.appendText("Server: Wyłączenie komputera zostało anulowane " + " [" + getTime() +"]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdownNow(){
        String shutdownCommand = "shutdown.exe -s ";
        //System.out.println(shutdownCommand);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(shutdownCommand);
            messages.appendText("Server: Natychmiastowe wyłączenie PC " + " [" + getTime() +"]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeVolume(int i){
        String command = "";
        Runtime runtime = Runtime.getRuntime();
        if(i == 1)
        {
            command = "nircmd.exe changesysvolume 3276";
            try {
                Process proc = runtime.exec(command);
                messages.appendText("Server: Zwiększono głośność. " + " [" + getTime() +"]\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(i == 2)
        {
            command = "nircmd.exe changesysvolume -3276";
            try {
                Process proc = runtime.exec(command);
                messages.appendText("Server: Zmiejszono głośność. " + " [" + getTime() +"]\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            command = "nircmd.exe mutesysvolume 2";
            try {
                Process proc = runtime.exec(command);
                messages.appendText("Server: Wyciśnięto przycisk \"mute\". " + " [" + getTime() +"]\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public void about() {
        DialogUtills.showInformation();
    }
}
