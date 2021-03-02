import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho 
{
    //Convert byte to string
    private static String decode(byte[] bytes,int length) throws UnsupportedEncodingException
    {   
        //Converts a byte array to string. 
        String string = new String(bytes,0,length,"UTF-8");
        return string;
    }

    //Convert String to byte
    private static byte[] encode(String text) throws UnsupportedEncodingException
    {
        //Converts a string to a byte array
        text = text + '\n';
        byte [] toBytes = text.getBytes("UTF-8");
        return toBytes;
    }

    //Load one megabyte 
    static int BUFFERSIZE = 1000000;

    public static void main( String[] args) throws IOException 
    {

        //From the client will be stored here
        byte [] fromClientbuffer = new byte[BUFFERSIZE];

        //Gets the portnumber from the terminal
        int port = Integer.parseInt(args[0]);
        
        //Calls socket(), bind() and listen() system calls to create a "welcoming " socket with portnumber X
        ServerSocket serverSocket = new ServerSocket(port);

        try 
        {
            while(true)
            {
                //Wait for a client to connect. Create a new socket for communication with the client
                Socket clientSocket = serverSocket.accept();
    
                //Lägger en timer
                clientSocket.setSoTimeout(5000);

                //This is what you return to the client
                StringBuilder serverResponse = new StringBuilder();
                
                //Used for the while loop
                
                int fromClientLength = clientSocket.getInputStream().read(fromClientbuffer);
                                  
                while(fromClientLength != -1)
                {
                    
                    String decoded = decode(fromClientbuffer, fromClientLength);
                    if(decoded.contains("\n"))
                    {
                        serverResponse.append(decode(fromClientbuffer, fromClientLength));
                        break;
                    }
                }

                OutputStream outFromServer = clientSocket.getOutputStream();
                outFromServer.write(encode("HTTP/1.1 200 OK\r\n\r\n" + serverResponse));
                clientSocket.close();

            }
        } 
        catch (Exception e) 
        {
            // Exception: Din mamma
        }

       
    }
}