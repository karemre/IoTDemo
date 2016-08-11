import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.net.ssl.HostnameVerifier;

public class HeatControl {

            private BufferedReader in;
            static String hostIp;
            public void connect(String source) throws Exception {

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
                
                //String hostIp = (InetAddress.getLocalHost()).getHostAddress();
                //String hostIp = getLocalHostLANAddress().toString();
                
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


            /**
             * Returns an <code>InetAddress</code> object encapsulating what is most likely the machine's LAN IP address.
             * <p/>
             * This method is intended for use as a replacement of JDK method <code>InetAddress.getLocalHost</code>, because
             * that method is ambiguous on Linux systems. Linux systems enumerate the loopback network interface the same
             * way as regular LAN network interfaces, but the JDK <code>InetAddress.getLocalHost</code> method does not
             * specify the algorithm used to select the address returned under such circumstances, and will often return the
             * loopback address, which is not valid for network communication. Details
             * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665037">here</a>.
             * <p/>
             * This method will scan all IP addresses on all network interfaces on the host machine to determine the IP address
             * most likely to be the machine's LAN address. If the machine has multiple IP addresses, this method will prefer
             * a site-local IP address (e.g. 192.168.x.x or 10.10.x.x, usually IPv4) if the machine has one (and will return the
             * first site-local address if the machine has more than one), but if the machine does not hold a site-local
             * address, this method will return simply the first non-loopback address found (IPv4 or IPv6).
             * <p/>
             * If this method cannot find a non-loopback address using this selection algorithm, it will fall back to
             * calling and returning the result of JDK method <code>InetAddress.getLocalHost</code>.
             * <p/>
             *
             * @throws UnknownHostException If the LAN address of the machine cannot be found.
             */
            private InetAddress getLocalHostLANAddress() throws Exception {
                try {
                    InetAddress candidateAddress = null;
                    // Iterate all NICs (network interface cards)...
                    for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                        NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                        // Iterate all IP addresses assigned to each card...
                        for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                            InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                            if (!inetAddr.isLoopbackAddress()) {

                                if (inetAddr.isSiteLocalAddress()) {
                                    // Found non-loopback site-local address. Return it immediately...
                                    return inetAddr;
                                }
                                else if (candidateAddress == null) {
                                    // Found non-loopback address, but not necessarily site-local.
                                    // Store it as a candidate to be returned if site-local address is not subsequently found...
                                    candidateAddress = inetAddr;
                                    // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                                    // only the first. For subsequent iterations, candidate will be non-null.
                                }
                            }
                        }
                    }
                    if (candidateAddress != null) {
                        // We did not find a site-local address, but we found some other non-loopback address.
                        // Server might have a non-site-local address assigned to its NIC (or it might be running
                        // IPv6 which deprecates the "site-local" concept).
                        // Return this non-loopback candidate address...
                        return candidateAddress;
                    }
                    // At this point, we did not find a non-loopback address.
                    // Fall back to returning whatever InetAddress.getLocalHost() returns...
                    InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
                    if (jdkSuppliedAddress == null) {
                        throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
                    }
                    return jdkSuppliedAddress;
                }
                catch (Exception e) {
                    UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
                    unknownHostException.initCause(e);
                    throw unknownHostException;
                }
            }

            public static void main(String[] args) throws Exception {
                HeatControl client = new HeatControl();
                //client.connect();
                hostIp = args[0];
                for(int i = 1; i < args.length ; i++){
                    client.connect(args[0]);
                }
            }

}