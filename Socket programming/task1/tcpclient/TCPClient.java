/*
    Ali Rakin
    2021 - February 11
    TCP client socket connection 
*/
package tcpclient;
import java.net.*;
import java.io.*;
import java.util.Scanner;



public class TCPClient
{

    private static int BUFFERSIZE = 1024;
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException 
    {

        //Allocate space for the user & server 
        byte [] fromServerBuffer = new byte[BUFFERSIZE];

        //If we are not sending to server then we are just receieving 
        if(ToServer==null)
        {
            return askServer(hostname, port);
        }
        else
        {
            //Initiate a Socket that connects to server
            Socket clientSocket = new Socket(hostname,port);
            
            //Timer to 2 seconds 
            clientSocket.setSoTimeout(2000);

            //Skicka data till server
            clientSocket.getOutputStream().write(encode(ToServer),0,encode(ToServer).length);

            // Sätter serverns info in i byte arrayn sedan räknar ut hur många platser det star 
            int fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);

            //Close the socket
            clientSocket.close();

            return decode(fromServerBuffer, fromServerLength);
        }
    }

    public static String askServer(String hostname, int port) throws  IOException 
    {
        //Initiate a Socket that connects to server
        Socket clientSocket = new Socket(hostname,port);
       
        //Timer to 2ms 
        clientSocket.setSoTimeout(2000);

        // Allocate space to receieve from server. Byte array
        byte [] fromServerBuffer = new byte[BUFFERSIZE];

        // Get how long the data from the server is and put the data from the server to the byte array
        int fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);

        return decode(fromServerBuffer, fromServerLength);

    }

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


    

    public static void main(String[] args) throws Exception
    {

       
            
    }
}

