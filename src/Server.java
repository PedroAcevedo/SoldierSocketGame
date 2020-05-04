/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author digital
 */
public class Server {

    static Socket socket;
    static ServerSocket serverSocket;
    static DataInputStream in;
    static DataOutputStream out;
    static Users[] user = new Users[10]; 
    static ArrayList<String> Users = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting server... ");
        serverSocket = new ServerSocket(5100);
        System.out.println("Server Started... ");
        while(true){
            socket = serverSocket.accept();
            for (int i = 0; i < 10; i++) {
                 System.out.println("Connection from: " + socket.getInetAddress() );
                 in = new DataInputStream(socket.getInputStream());
                 out = new DataOutputStream(socket.getOutputStream());
                 if (user[i] == null) {
                    user[i] = new Users(out,in,user,i);
                    Thread thread = new Thread(user[i]);
                    thread.start();
                    break;
                }
            }
        }
       
        
    }
    
   
}

 class Users implements Runnable{

     DataOutputStream out;
     DataInputStream in;
     Users[] user = new Users[10];
     String name;
     int number;
     boolean Inicio;
    public Users(DataOutputStream out, DataInputStream in, Users[] user,int number) {
        this.out = out;
        this.in = in;
        this.user = user;
        this.number= number;
        this.Inicio=true;
    }
         
    @Override
    public void run() {   
        try {
              name = in.readUTF();
              Server.Users.add(name);
              String Usuarios = ""; 
              for (String User : Server.Users) {
                Usuarios = Usuarios + " " +User +  " ";
              }
               user[number].out.writeUTF(number + "");
               System.out.println(Usuarios);
               for (int i = 0; i < 10; i++) {
                   
                   if (user[i] != null) {
                       user[i].out.writeUTF(Usuarios);
                   }
               }
           } catch (IOException ex) {
               ex.printStackTrace();
           }       
        
        while (true) {

           try {
                if (Server.Users.size()==2 && Inicio==true) {
                    for (int i = 0; i < 10; i++) {
                        if (user[i] != null) {
                            user[i].out.writeUTF("Ready");
                        }
                    }       
                    Inicio = false;
                }
               String message = in.readUTF();
                for (int i = 0; i < 10; i++) {
                   if (user[i] != null && i != number) {
                       user[i].out.writeUTF(message);
                   }
                }            
           } catch (IOException ex) {
               this.out = null;
               this.in = null;
           }
           
           
       }
        
        
    }
    
  
 }
