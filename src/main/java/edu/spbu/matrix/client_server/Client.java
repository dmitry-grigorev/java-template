package edu.spbu.matrix.client_server;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    public Socket socket;//сокет
    private DataOutputStream outstream;//выходной поток
    private DataInputStream instream;//входной поток

    Client() throws IOException {
         socket= new Socket("localhost", 8080);
         outstream=new DataOutputStream(socket.getOutputStream());
         instream=new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
            try {
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(outstream));
                out.write("GET/test.html/HTTP/1.0\r\n\r\n");
                System.out.println("Response was sent");
                out.newLine();
                out.flush();

                Thread.sleep(3000);

                getMessage();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

    }

    public void getMessage()throws IOException{
        BufferedReader bread=new BufferedReader(new InputStreamReader(instream));
        String Message=bread.readLine();
        while(Message!=null)
        {
            System.out.println(Message);
            Message=bread.readLine();
        }

    }
}
