package edu.spbu.matrix.client_server;

import java.io.IOException;
import java.net.SocketException;

public class PerformingClient {

    public static void main(String[] args) throws IOException
    {
        while(true)
        {
        try {
            //Client cl=new Client("test.html","localhost",8080);
            Client cl = new Client("", "ru.wikipedia.org",80);
            cl.run();
        }catch (SocketException e)
            {
                System.out.println("Не удалось установить соединение");
                e.printStackTrace();
            }
        }
    }


}
