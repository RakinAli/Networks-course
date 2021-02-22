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
    static int BUFFERSIZE = 1024;

    public static void main( String[] args) throws IOException 
    {

        // Buffered size
        private static int BUFFERSIZE = 1024;


        

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
                clientSocket.setSoTimeout(2000);

                //This is what you return to the client
                StringBuilder serverResponse = new StringBuilder();
                

                //Used for the while loop
                int fromServerLength = 0;

                while(fromServerLength != -1)
                {
                    // Lägger in data från server till stringbuilder
                    fromServer.append(decode(fromServerBuffer, fromServerLength));
                    fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);
                }
                
                /*
                while(clientSocket.getInputStream().read() != -1)
                {
                    serverResponse.append(clientSocket.getInputStream().toString() + "\r\n");
                    break;
                }
                */
                
                OutputStream outFromServer = clientSocket.getOutputStream().;
                outFromServer.write(encode("HTTP/1.1 200 OK\r\n\r\n" + serverResponse.toS));
                
                clientSocket.close();

            }
        } 
        catch (Exception e) 
        {
            //Exception: Din mamma
        }

       
    }
}