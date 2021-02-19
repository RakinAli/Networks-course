import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho 
{
    //Load one megabyte 
    static int BUFFERSIZE = 102;

    public static void main( String[] args) throws IOException 
    {
        //Gets the portnumber from the terminal
        int port = Integer.parseInt(args[0]);
        
        //Calls socket(), bind() and listen() system calls to create a "welcoming " socket with portnumber X
        ServerSocket welcomeSocket = new ServerSocket(port);

        while(true)
        {
            //Wait for a client to connect. Create a new socket for communication with the client
            Socket toClientSocket = welcomeSocket.accept();

            // A byte array, stores the request from the client
            byte[] fromClientBytes = new byte[BUFFERSIZE];

            //Calculates the length of the byte array that stores the request from the client
            int fromClientLength = toClientSocket.getInputStream().read(fromClientBytes);

            //Compute response for client
            byte[] toClientBuffer = new byte[BUFFERSIZE];

            //Echoes what the client requested. 
            toClientSocket.getOutputStream().write(toClientBuffer,0,fromClientLength);
            toClientSocket.close();
            
        }
    }
}

