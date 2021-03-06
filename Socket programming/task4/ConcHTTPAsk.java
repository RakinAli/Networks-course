import java.net.*;
import java.io.*;

public class ConcHTTPAsk 
{
    public static void main(String[] args) throws IOException
    {
        int httpPort;
        if(args.length>0)
        {
            httpPort = Integer.parseInt(args[0]);
        }
        else
        {
            httpPort = 8888;
        }
        try 
        {
            ServerSocket serverSocket = new ServerSocket(httpPort);
            while(true)
            {
                //Accepts a client and creates a thread with connection
                (new Thread(new MyRunnable(serverSocket.accept()))).start();
            }     
        }
         
        catch (IOException e) 
        {
            System.out.println("Crash code");
        }


    }
    
    
    




}
