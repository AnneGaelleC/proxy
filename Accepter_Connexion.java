package proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Accepter_Connexion implements Runnable {

    private ServerSocket socketserver;
    private Socket socket;
    
     public Accepter_Connexion(ServerSocket s){
         socketserver = s;
     }
     
     public void run() {

         try {
             while(true){
           socket = socketserver.accept(); // Un client se connecte on l'accepte
           socket.close();
             }
         
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

 }
