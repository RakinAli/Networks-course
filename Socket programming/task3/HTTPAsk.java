import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk 
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
  
    public static void main(String[] args)throws IOException
    {

        //From the client will be stored here
        byte [] fromClientbuffer = new byte[BUFFERSIZE];

        // Used to initiate the port 
        int httpPort;
        
        // If we have arguments
        if(args.length > 0 )
        {
            // We take args[0] argument as an integer
            httpPort = Integer.parseInt((args[0]));
        }
        else
        {
            //We use port 8888 -> HTTPS
            httpPort = 8888;
        }

        ServerSocket httpSocket = new ServerSocket(httpPort);

        //Http headers 
        String http200 = "HTTP/1.1 200 ok\r\n\r\n";
        String http400 = "HTTP/1.1 400 Bad Request\r\n";
        String http404 = "HTTP/1.1 404 Not Found\r\n";

        
        String clientPort = null;
        String clientHostname = null;
        String clientData = null;

        try 
        {
            while(true)
            {
                //Accepts client
                Socket channelSocket = httpSocket.accept();

                // 2 seconds timeout
                channelSocket.setSoTimeout(2000);

                //This is what is being returned to the client
                StringBuilder clientRequest = new StringBuilder();

                //This gives out the client of the inputStream
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

                // Understand what the client requested
                String clientRequestt = clientRequest.toString();

                // Cuts the request into three strings. First contains GET/ASk,Second -> Domain, Third->Port
                String[] requestCut = clientRequestt.split("[?&= ]");

                //Here we get the values form the String array
                for(int i=0; i < requestCut.length; i++)
                {
                    //Here we extract the hostname
                    if(requestCut[i].equals("hostname"))
                    {
                        //After "hostname" -> We get the hostname 
                        clientHostname = requestCut[++i];
                    }

                    //Here we extract the port number
                    if(requestCut[i].equals("port"))
                    {
                        clientPort = requestCut[++i];
                    }

                    //Here we extract 
                    if(requestCut[i].equals("string"))
                    {
                        clientData = requestCut[++i];
                    }
                }
                // This is what will be reponsed 
                StringBuilder responseBuilder = new StringBuilder();

                if(clientHostname != null && clientPort != null && clientPort.matches("[0-9]+")&& requestCut[1].equals("/ask"))
                {
                    try 
                    {
                        // The response from the server
                        String response = TCPClient.askServer(clientHostname,Integer.parseInt(clientPort),clientData);

                        // What returns ->
                        responseBuilder.append(http200);
                        responseBuilder.append(response);
                    } 
                    catch (IOException e) 
                    {   
                        responseBuilder.append(http404);
                    }
                }
                else
                {
                    responseBuilder.append(http400);
                }

                OutputStream responseStream = channelSocket.getOutputStream();
                responseStream.write(encode("HTTP/1.1 200 OK\r\n\r\n" + responseBuilder.toString()));
                channelSocket.close();
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Crash the code");
        } 
    }
}

