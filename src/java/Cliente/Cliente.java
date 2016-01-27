/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; //Sólo se usa para el ejemplo de leer datos por teclado (después lo eliminas)
import org.json.simple.JSONObject;



/**
 *
 * @author Rudolfaraya
 */
public class Cliente{
    public Cliente(String strBusqueda) {
        
        //Comunicación con el cache
        Socket miSocket;
        DataOutputStream out;
        DataInputStream in;
        String StopWords = " algún | alguna | algunas | algun | algunos | ambos | ampleamos | ante | antes | aquel | aquellas | aquellos | aqui | arriba | atras | bajo | bastante | bien | cada | cierta | ciertas | cierto | ciertos | como | con | conseguimos | conseguir | consigo | consigue | consiguen | consigues | cual | cuando | dentro | desde | donde | dos | el | ellas | ellos | empleais | emplean | emplear | empleas | empleo | en | encima | entonces | entre | era | eramos | eran | eras | eres | es | esta | estaba | estado | estais | estamos | estan | estoy | fin | fue | fueron | fui | fuimos | gueno | ha | hace | haceis | hacemos | hacen | hacer | haces | hago | incluso | intenta | intentais | intentamos | intentan | intentar | intentas | intento | ir | la | largo | las | lo | los | mientras | mio | modo | muchos | muy | nos | nosotros | otro | para | pero | podeis | podemos | poder | podria | podriais | podriamos | podrian | podrias | por | por qué | porque | primero | puede | pueden | puedo | quien | sabe | sabeis | sabemos | saben | saber | sabes | ser | si | siendo | sin | sobre | sois | solamente | solo | somos | soy | su | sus | también | teneis | tenemos | tener | tengo | tiempo | tiene | tienen | todo | trabaja | trabajais | trabajamos | trabajan | trabajar | trabajas | trabajo | tras | tuyo | ultimo | un | una | unas | uno | unos | usa | usais | usamos | usan | usar | usas | uso | va | vais | valor | vamos | van | vaya | verdad | verdadera | verdadero | vosotras | vosotros | voy | yo";
        strBusqueda = (" "+strBusqueda).replaceAll(StopWords, " ");//Elimino las stopwords de la busqueda
        
        //Comunicación con el Index
        ServerSocket serverSocketIndex;
        Socket socketIndex;
        DataOutputStream outIndex;
        ObjectInputStream inIndex;
        
        
        JSONObject mensajeRecibidoIndex;
        ArrayList<DBCursor> Resultados;
        
        
        try{
           
            miSocket = new Socket("127.0.0.1",4444);
            out = new DataOutputStream(miSocket.getOutputStream());
                        
            out.writeUTF(strBusqueda);
            System.out.println("He enviado al SERVIDOR CACHE: "+strBusqueda);
            
            
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
                outIndex.writeUTF(strBusqueda);
                System.out.println("He enviado al SERVIDOR INDEX: "+strBusqueda);
                
                //RECIBIR RESPUESTA DEL INDEX SERVIDOR
                inIndex = new ObjectInputStream(socketIndex.getInputStream());
                mensajeRecibidoIndex = (JSONObject) inIndex.readObject(); // recibe el objeto json con las respuestas a cada palabra de la busqueda
                System.out.println("He recibido el objecto del Index");
                
                String[] palabrasBusqueda=strBusqueda.split("\\s+" );    // array de palabras separadas por espacios
               
                for(int i =0;i<palabrasBusqueda.length;i++){ 
            
                    if(!palabrasBusqueda[i].contentEquals(" ")&&!palabrasBusqueda[i].contentEquals("")){
                        
                    }
                
                }
                
                
                String str = (String) mensajeRecibidoIndex.get("p"); 

                 System.out.println(str);
               
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
/*List<Person> persons = new ArrayList<Persons>();
// TODO fill persons.

ByteArrayOutputStream bao = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(bao);
oos.writeObject(persons);
oos.close();

byte[] byteToTransfer = oos.getBytes();
// transfer

On server side:

byte[] bytesFromSocket = ....;
ByteArrayInputStream bis = new ByteArrayInputStream(bytesFromSocket);
ObjectInputStream ois = new ObjectOutputStream(bis);
List<Persons> persons = (List<Persons>) ois.readObject(persons);
*/