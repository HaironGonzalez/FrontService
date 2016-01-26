/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.net.*;
import java.io.*;
import java.util.Scanner; //Sólo se usa para el ejemplo de leer datos por teclado (después lo eliminas)



/**
 *
 * @author Rudolfaraya
 */
public class Cliente{
    public Cliente(String palabraBuscada) {
        
        //Comunicación con el cache
        Socket miSocket;
        DataOutputStream out;
        DataInputStream in;
        
        //Comunicación con el Index
        ServerSocket serverSocketIndex;
        Socket socketIndex;
        DataOutputStream outIndex;
        DataInputStream inIndex;
        String mensajeRecibidoIndex;
        
        try{
           
            miSocket = new Socket("127.0.0.1",4444);
            out = new DataOutputStream(miSocket.getOutputStream());
                        
            out.writeUTF(palabraBuscada);
            System.out.println("He enviado al SERVIDOR CACHE: "+palabraBuscada);
            
            
            //LEER RESPUESTA DEL CACHE SERVIDOR
            in = new DataInputStream(miSocket.getInputStream());
            String mensajeRecibido = in.readUTF();
            System.out.println("He recibido del SERVIDOR CACHE: "+mensajeRecibido);
            
            in.close();
            out.close();
            miSocket.close();
            
            if("miss".equals(mensajeRecibido)){
                //ENVIAR CONSULTA AL INDEX SERVIDOR
                socketIndex = new Socket("127.0.0.1",5555);
                outIndex = new DataOutputStream(socketIndex.getOutputStream());
                outIndex.writeUTF(palabraBuscada);
                System.out.println("He enviado al SERVIDOR INDEX: "+palabraBuscada);
                
                //RECIBIR RESPUESTA DEL INDEX SERVIDOR
                inIndex = new DataInputStream(socketIndex.getInputStream());
                mensajeRecibidoIndex = inIndex.readUTF();
                System.out.println("He recibido del SERVIDOR INDEX: "+mensajeRecibidoIndex);
                  
                outIndex.close();
                inIndex.close();
                socketIndex.close();
            }
            
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
               
    }
    public static void main(String[] args) {
        
        
    }
    
}
