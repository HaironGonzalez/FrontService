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

    public String getStr() {
        return str;
    }

    public JSONObject getJson() {
        return json;
    }
 
    JSONObject json = new JSONObject ();
    String str;
    
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
                
                String[] palabrasBusqueda=strBusqueda.split("\\s+" );    // array de palabras separadas por espacios (String Ingresado por usuario)
               
                
                
                
                for(int i =0;i<palabrasBusqueda.length;i++){              //trabaja el Json recibido para mostrarlo en resultados.jsp
            
                    if(!palabrasBusqueda[i].contentEquals(" ")&&!palabrasBusqueda[i].contentEquals("")){
                        String Aux = (String) mensajeRecibidoIndex.get(palabrasBusqueda[i]);
                        String [] str2 =Aux.split(",");                     // se separan las comas quedan string pequeños de ID y Frecuencia
                        
                        for (int j=0; j<str2.length;j++) {            
                            String [] str3 = str2[j].split(" ");               // separo los id y frecuencia

                            //System.out.println("Largo "+str2.length+" Aqui"+str3[0]+" Aqui"+str3[1]);
                            if(json.containsKey(str3[0])){                // rebiso si json ya contiene esa id
                                String Aux2 = (String) json.get(str3[0]);
                                Aux2 = Aux2+","+"Palabra:"+palabrasBusqueda[i]+" F:"+str3[1];
                                json.replace(str3[0], Aux2);
                            }else{
                                json.put(str3[0],"Palabra:"+palabrasBusqueda[i]+" F:"+str3[1]);
                            }

                        }
  
                    }
                }
                
                
                 //str = (String) mensajeRecibidoIndex.get("p"); 

                // System.out.println(str);
               
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