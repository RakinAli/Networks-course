import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho 
{
    static int BUFFERSIZE = 1024;

    public static void main( String[] args) throws IOException 
    {
        //Calls socket(), bind() and listen() system calls to create a "welcoming " socket with portnumber X
        ServerSocket welcomeSocket = new ServerSocket();

        while(true)
        {
            //Wait for a client to connect. Create a new socket for communication with the client
            Socket connectionSocket = welcomeSocket.accept();
            byte[] fromClientBytes = new byte[BUFFERSIZE];
            int fromClientLength = connectionSocket.getInputStream().read(fromClientBytes);

            //Compute response for client
            byte[] toClientBuffer = 
            connectionSocket.getOutputStream().write(toClientBuffer);

            
        }
        

    }
}

