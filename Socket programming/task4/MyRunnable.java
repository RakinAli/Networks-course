import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import tcpclient.TCPClient;

public class MyRunnable implements Runnable
{

    //Load one megabyte 
    static int BUFFERSIZE = 1000000;

    // What the client requests for 
    String clientPort = null;
    String clientHostname = null;
    String clientData = null;

    //Http headers 
    String http200 = "HTTP/1.1 200 OK\r\n\r\n";
    String http400 = "HTTP/1.1 400 Bad Request\r\n";
    String http404 = "HTTP/1.1 404 Not Found\r\n";


    //This is what gets connected
    private Socket channelSocket;

    //Constructor -> Used in threads 
    public MyRunnable(Socket socket)
    {
        this.channelSocket = socket;
    }

    //What the thread runs 
    public void run() 
    {
        try 
        {
            //From the client will be stored here
            byte [] fromClientbuffer = new byte[BUFFERSIZE];

            //Bulding a String -> This is what the client requests 
            StringBuilder clientRequest = new StringBuilder(); 
            int fromClientLength = channelSocket.getInputStream().read(fromClientbuffer);

            //Get the entire request from the client
            while(fromClientLength != 1)
            {
                String decoded = decode(fromClientbuffer, fromClientLength);
                if(decoded.contains("\n"))
                {
                    clientRequest.append(decode(fromClientbuffer, fromClientLength));
                    break;
                }
            }

               // Understand what the client requested -> Turn it to stirng
               String clientRequestt = clientRequest.toString();

               // Get the request
               String[] requestCut1 = clientRequestt.split("\\r\\n");

               // Cuts the request into three strings. First contains GET/ASk,Second -> Domain, Third->Port
               String [] requestCut = requestCut1[0].split("[?&= ]");  
            
               //Here we get the values form the String array
               for(int i=0; i < requestCut.length; i++)
               {
                   //Here we extract the hostname
                   if(requestCut[i].equals("hostname"))
                   {
                       //After "hostname" -> We get the hostname 
                       clientHostname = requestCut[i+1];
                   }

                   //Here we extract the port number
                   if(requestCut[i].equals("port"))
                   {
                       clientPort = requestCut[1+i];
                   }

                   //Here we extract the String the client requested 
                   if(requestCut[i].equals("string"))
                   {
                       clientData = requestCut[i+1];
                   }
               }
                // This is what will be reponsed 
                StringBuilder responseBuilder = new StringBuilder();

                if(clientHostname != null && clientPort != null && clientPort.matches("[0-9]+") && requestCut[1].equals("/ask") && requestCut[0].equals("GET"))
                {
                    try 
                    {
                        // The response from the server
                        String response = TCPClient.askServer(clientHostname,Integer.parseInt(clientPort),clientData);

                        // What returns ->
                        responseBuilder.append(http200 + response);                      
                    } 
                    catch (IOException e) 
                    {   
                        responseBuilder.append(http404);
                    }
                }
                else
                {
                    //If postname, hostname is empty or if there's no ask
                    responseBuilder.append(http400);
                }               
                OutputStream responseStream = channelSocket.getOutputStream();
                responseStream.write(encode(responseBuilder.toString())); // <------------- HÄR LIGGER PROBLEMET 
                channelSocket.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Crash the code");
        }
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
    
}
