import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HeatControl {

            private BufferedReader in;
            public void connect(String source) throws IOException {

                // Make connection and initialize streams
                // Socket socket = new Socket(source, 32769);
                // if(socket.isConnected())
                //        System.out.println("sdfsadfsdafasd");
                // in = new BufferedReader(
                //        new InputStreamReader(socket.getInputStream()));

                // Consume the initial welcoming messages from the server

                
                
                int temp = 60 + (int) (Math.random() * 30);
                
                String actuate = "";
                if(temp < 75)
                    actuate = "heater_on";
                else if (temp > 80)
                    actuate = "cooler_on";
                else 
                    actuate = "normal";
                
                String hostIp = (InetAddress.getLocalHost()).getHostAddress();
                
                String date = (new SimpleDateFormat("HH:mm:ss")).format((Calendar.getInstance()).getTime());
                

                String result = date + "-" + source + "-" + temp + "-" + hostIp + "-" + actuate ;
                
                    
                try {
                    
                    String command = "curl -i --data " + result + " -X POST http://9.1.189.10:8000/results";
                    Process p = Runtime.getRuntime().exec(command);             
                    p.waitFor(); 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine())!= null){
                        System.out.println(line);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            public static void main(String[] args) throws Exception {
                HeatControl client = new HeatControl();
                //client.connect();
                for (String s: args) {
                        client.connect(s);
                }
            }

}
