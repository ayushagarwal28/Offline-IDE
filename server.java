import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
class ThreadServer implements Runnable
{
    public static final int BUFFER_SIZE = 100;                                                  //Variable Declaration
    Socket skt;
    String choice="";
    ObjectOutputStream oos ;
    ObjectInputStream  ois ;
    byte [] buffer;
    Object obj ;
    Integer bytesRead;
    
    ThreadServer(Socket stemp) throws IOException                                               //Socket Connection Attempting
    {
        this.skt=stemp;
        this.oos = new ObjectOutputStream(skt.getOutputStream());
        this.ois = new ObjectInputStream(skt.getInputStream());
        new Thread(this).start();
    }

    public void run()                                                                           // Thread Run
    {   
        try 
        {
            saveFile();                                                                         //Save Files Locally
            runCode();                                                                          //Compile Files
            sendOutput();                                                                       //Send Output to client
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void saveFile() throws Exception
    {
        
        
        
        buffer = new byte[BUFFER_SIZE];
        FileOutputStream fos = null;

        choice = ois.readUTF();                                                                 //1.Read Language Choice

		if(choice.equals("c_language"))                                                         //Generating Code file
        	fos = new FileOutputStream(new File("code.c"));
		if(choice.equals("cpp"))
        	fos = new FileOutputStream(new File("code.cpp"));
		if(choice.equals("java"))
        	fos = new FileOutputStream(new File("code.java"));
        
        
	    bytesRead = 0;                                                                         //Writing Code File                                                 
        do 
        {
            bytesRead = (Integer)ois.readObject();
            buffer = (byte[])ois.readObject();
	        fos.write(buffer, 0, bytesRead);  

        } while (bytesRead == BUFFER_SIZE);
        
        fos=null;
        fos = new FileOutputStream(new File("input.txt"));                                      //Writing Input File
        bytesRead = 0;
        do 
        {
            bytesRead = (Integer)ois.readObject();
            buffer = (byte[])ois.readObject();
            fos.write(buffer, 0, bytesRead);   
           
        } while (bytesRead == BUFFER_SIZE);
        fos.close();
        System.out.println("Inputs Recieved From Client");

    }

    private void runCode() throws IOException, InterruptedException                                                 //Compilation Locally On server
    {
        if(choice.equals("c_language"))
	    {
                Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","gcc code.c &> output.txt"});            //Compilation for C codes
		        p.waitFor();
		        Process q;
		        File file_output = new File("output.txt");
                if (file_output.length() == 0)
		        {
		             System.out.println("Compiled");
        	         q = Runtime.getRuntime().exec(new String[]{"bash","-c","./a.out < input.txt &> output.txt"});     //Execute and save in output.txt
		             q.waitFor();
                     System.out.println("Executed\n");
		       }

	    }
	    if(choice.equals("cpp"))
	    {
        	    Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","g++ code.cpp &> output.txt"});         //Compilation for C++ codes
		        p.waitFor();
		        Process q;
		        File file_output = new File("output.txt");
        	    if (file_output.length() == 0)
		        {
		            System.out.println("Compiled");
        	        q = Runtime.getRuntime().exec(new String[]{"bash","-c","./a.out < input.txt &> output.txt"});      //Execute and save in output.txt
		            q.waitFor();
                    System.out.println("Executed\n");
		        }
	    }
	    if(choice.equals("java"))
	    {
        	    Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","javac code.java &> output.txt"});      //Compilation for java codes
		        p.waitFor();
		        Process q;
		        File file_output = new File("output.txt");
        	    if (file_output.length() == 0)
		        {
		            System.out.println("Compiled");
        	        q = Runtime.getRuntime().exec(new String[]{"bash","-c","java code < input.txt &> output.txt"});    //Execute and save in output.txt
		            q.waitFor();
                    System.out.println("Executed\n");
		        }
	    }
    }
	
	

    private void sendOutput() throws IOException 
    {
        File file_output = new File("output.txt");
	    if(file_output.length() == 0)                                                                                  //Check if output.txt is empty
	    {
	       	FileWriter fileWriter = new FileWriter(file_output);
		    fileWriter.write("Executed Succesfully : No output to display");
		    fileWriter.flush();
		    fileWriter.close();
	    }
        
        FileInputStream fis = new FileInputStream("output.txt");                                                        //Write output.txt to client            
        bytesRead = 0;
        while ((bytesRead = fis.read(buffer)) > 0)
        {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
        

        new File("code.c").delete();                                                                                    //Deleting all temporary files
        new File("code.cpp").delete();
        new File("code.java").delete();
        new File("a.out").delete(); 
        new File("code.java").delete();
        new File("output.txt").delete();
        new File("input.txt").delete();

        ois.close();                                                                                                    //Closing all the streams
        oos.close();
        skt.close();
    }
}


public class server                                                                                                     //Main Class: Multithreaded Server
{

	public static void main(String[] args)  throws IOException
	{
		
		ServerSocket s=null;
		Socket s1;
		try
		{
			s=new ServerSocket(50102);
			while(true)
			{
				s1=s.accept();
				ThreadServer ob =new ThreadServer(s1);
			}
		}
        finally
		{
						s.close();
		}
	}
    
}