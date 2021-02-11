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
        //If we are not sending to server then we are just receieving 
        if(ToServer==null)
        {
            return askServer(hostname, port);
        }
        else
        {
            //Initiate a Socket
            Socket clientSocket = new Socket(hostname,port);

            //Streams for input and output from the server
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            byte [] sender = encode(ToServer);
            output.write(sender);

            

        }


    }

    public static String askServer(String hostname, int port) throws  IOException 
    {
        // Pre - Allocate byte buffers for reading/receive
        byte[] fromUserBuffer = new byte [BUFFERSIZE];
        byte[] fromServerBuffer = new byte[BUFFERSIZE];

        Socket clientSocket = new Socket(hostname,port);
        int fromServerLength = clientSocket.getOutputStream().write(b);


    }

    //Convert byte to string
    private static String decode(byte[] bytes,int length) throws UnsupportedEncodingException
    {   
        String string = new String(bytes,0,length,"UTF-8");
        return string;
    }

    //Convert String to byte
    private static byte[] encode(String text) throws UnsupportedEncodingException
    {
        text = text + '\n';
        byte [] toBytes = text.getBytes("UTF-8");
        return toBytes;
    }

    

    public static void main(String[] args) throws Exception
    {

        // Pre - Allocate byte buffers for reading/receive
        byte[] fromUserBuffer = new byte [BUFFERSIZE];
        byte [] fromServerBuffer = new byte[BUFFERSIZE];

        Scanner user_in = new Scanner(System.in);
        System.out.print("Hostname: ");
        String hostnamee = user_in.nextLine();
        System.out.print("Portnumber: ");
        int portnumber = user_in.nextInt();
        

        Socket clientSocket = new Socket(hostnamee,portnumber);

        int fromUserLength = System.in.read(fromUserBuffer); // User input
        clientSocket.getOutputStream().write(fromUserBuffer,0,fromUserLength);

        int fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);
        System.out.print("FROM SERVER: "); //Use print method since it is a string
        System.out.write(fromServerBuffer,0,fromServerLength);
        clientSocket.close();
            
    }
}

