package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.Protocol;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarClient {

   private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Client > %5$s%n");
      LOG.info("Caesar client starting...");
      LOG.log(Level.INFO, "Protocol constant: {0}", Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
      Socket clientSocket = null;
      OutputStream os = null;
      BufferedReader is = null;
      int key;
      String msg;
      Scanner scan = new Scanner(System.in);

      try {
         clientSocket = new Socket("localhost", Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
         LOG.log(Level.INFO, "Connection to server on port {0}", Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
         
         os = clientSocket.getOutputStream();
         is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         
         //Key agreement
         String keyRequest = "hello\n";
         os.write(keyRequest.getBytes());
         os.flush();

         String line = is.readLine();

         LOG.log(Level.INFO, "Key sent by the server: {0}", line);
         
         key = Integer.parseInt(line);
         
         while(true){
            System.out.println("Entrez votre message: ");
            msg = scan.nextLine() + "\n";
            os.write(Protocol.encrypt(msg, key).getBytes());
            os.flush();
            
            line = is.readLine();

            LOG.log(Level.INFO, "Response sent by the server: ");
            LOG.log(Level.INFO, line);
            
            line = Protocol.decrypt(line, key);
            
            LOG.log(Level.INFO, "Decrypted message: {0}", line);
                  
            if(line.equalsIgnoreCase("bye")){
               break;
            }
         }
         
      } catch (IOException ex) {
         LOG.log(Level.SEVERE, null, ex);
      }

   }

}
