import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HeatControl {

                private BufferedReader in;

            public void connectToServer() throws IOException {

                // Make connection and initialize streams
                Socket socket = new Socket("9.1.75.60", 32769);
                if(socket.isConnected())
                        System.out.println("sdfsadfsdafasd");
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                // Consume the initial welcoming messages from the server
            }
	    /**
             * Runs the client application.
             */
            public static void main(String[] args) throws Exception {
                CapitalizeClient client = new CapitalizeClient();
                client.connectToServer();
            }

}

