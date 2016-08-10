import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HeatControl {

            private BufferedReader in;

            public void connect() throws IOException {

                // Make connection and initialize streams
                Socket socket = new Socket("9.1.75.60", 32769);
                if(socket.isConnected())
                        System.out.println("sdfsadfsdafasd");
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                // Consume the initial welcoming messages from the server
            }

            public static void main(String[] args) throws Exception {
                HeatControl client = new HeatControl();
                client.connectToServer();
            }

}

