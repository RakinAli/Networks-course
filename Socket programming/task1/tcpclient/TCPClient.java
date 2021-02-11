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
        if(ToServer==null)
        {
            return askServer(hostname, port);
        }
        else
        {

        }


    }

    public static String askServer(String hostname, int port) throws  IOException 
    {
        Socket clientSocket = new Socket(hostname,port);
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

