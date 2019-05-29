import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.lang.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 

public class client_socket 
{
    client_socket(String ch,String ipadd,String portadd) throws ClassNotFoundException 								//Client Socket
    {
        try {
            this.work(ch,ipadd,portadd);
        } catch (IOException ex) {
            Logger.getLogger(client_socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void work(String ch,String ipadd,String portadd) throws IOException, ClassNotFoundException 
    {

        Socket socket = new Socket(ipadd,Integer.parseInt(portadd));                    							//New Socket
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());										//Object Streams
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

	    oos.writeUTF(ch);                                                                 							//Write Language Choice       

        File file = new File("code.txt");																			//Write Code.txt To server
        FileInputStream fis = new FileInputStream(file);
        byte [] buffer = new byte[100];
        Integer bytesRead = 0;
        while ((bytesRead = fis.read(buffer)) > 0)
        {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
	    oos.flush();

        file = new File("input.txt");																				//Write input.txt To server
        fis = new FileInputStream(file);
        bytesRead = 0;
        while ((bytesRead = fis.read(buffer)) > 0)
        {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
	    oos.flush();	
	
	
        FileOutputStream fos = new FileOutputStream("output.txt");																//Recieving Output.txt From Server
        bytesRead = 0;
        do 
        {
            bytesRead = (Integer)ois.readObject();
            buffer = (byte[])ois.readObject();
	        fos.write(buffer, 0, bytesRead);  
        } while (bytesRead == 100);      

        System.out.println("Output Received");																		//Closing Object Streams
        oos.close();
        ois.close();
        
    }
    
}
