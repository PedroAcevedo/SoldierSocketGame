/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author digital
 */
public class Client{
    
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    static String message;
    static boolean Ready = false;
    static int number;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Connecting...");
        socket = new Socket("localhost"/*"172.17.5.29"*/,5100);
        System.out.println("Connection sucessful");
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Input input = new Input(in);
        Thread thread = new Thread(input);
        Scanner sc = new Scanner(System.in);
        Inicio ini = new Inicio();
        ini.setVisible(true);
        ini.setLocationRelativeTo(null);
        synchronized(ini.i.th){
            ini.i.th.wait();            
        }
        String name= TypeName.Name;
        out.writeUTF(name);
        thread.start();
        Principal p = new Principal(name);
        p.setVisible(true);
        p.th.start();
        while(true){
            if ("Ready".equals(message)) {
                    Client.Ready = true;
                    
            }
            if (Client.Ready){
                if (p.Inicio) {
                    p.DefUsers(number);
                }
                String sendMessage = name + "%" + p.j1.Position();
                out.writeUTF(sendMessage);
            }else{
                System.out.println(message);
            }
            
            
        }
    }
    
}

class Input implements Runnable{

    DataInputStream in;

    public Input(DataInputStream in) {
        this.in = in;
    }
   
    @Override
    public void run() {
        while(true){
            try {
                
                String SocketText = in.readUTF();
                if (SocketText.length()>1) {
                   Client.message = SocketText;
                }else{
                    Client.number = Integer.parseInt(SocketText);
                }

            } catch (IOException ex) {
               ex.printStackTrace();
            }

        }
}
    
    
    
    
    
}