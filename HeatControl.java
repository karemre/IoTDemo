import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HeatControl {

            static String hostIp;
            
            public void connect(String source) throws Exception {

                
            }

            public static class MyThread implements Runnable {
                
                   String source; 
                   public MyThread(String s) {
                       source = s;
                   }

                   public void run() {
                       
                    // Make connection and initialize streams
                       // Socket socket = new Socket(source, 32769);
                       // if(socket.isConnected())
                       //        System.out.println("sdfsadfsdafasd");
                       // in = new BufferedReader(
                       //        new InputStreamReader(socket.getInputStream()));

                       // Consume the initial welcoming messages from the server

                       
                       while(true){
                           int temp = 60 + (int) (Math.random() * 30);
                           
                           String actuate = "";
                           if(temp < 75)
                               actuate = "heater_on";
                           else if (temp > 80)
                               actuate = "cooler_on";
                           else 
                               actuate = "normal";
                           
                  
                           String date = (new SimpleDateFormat("HH:mm:ss")).format((Calendar.getInstance()).getTime());     
    
                           String result = date + "-" + source + "-" + temp + "-" + hostIp + "-" + actuate ;
                               
                           try {
                               
                               String command = "curl -i --data " + result + " -X POST http://9.1.196.15:8000/results";
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
			
			   try {
				TimeUnit.SECONDS.sleep(1);
			   } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
                       }
                   }
                }
            
            public static void main(String[] args) throws Exception {
                //HeatControl client = new HeatControl();
                hostIp = args[0];
                for(int i = 1; i < args.length ; i++){
                    //client.connect(args[0]);
                    
                    Runnable r = new MyThread(args[i]);
                    new Thread(r).start();
                }
            }

}
