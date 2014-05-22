/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tarea.redes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Paracelso
 */
public class TareaRedes extends Thread {

    /**
     * @param args the command line arguments
     */	
        private static DataInputStream in;
        private static BufferedWriter out;
        
    public static void main(String[] args) throws Exception {
//    int port = 8000;
    ServerSocket serverSocket = new ServerSocket(8000);
    System.err.println("el servidor utiliza el puerto : 8000");

    while (true) {
        
        Socket clientSocket = serverSocket.accept();
        System.err.println("nuevo cliente conectado");

//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: Mon, 23 May 2005 22:38:34 GMT\r\n");
        out.write("Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Connection: close\r\n");
        out.write("\r\n");
        out.write("<body>");
        out.write("<div style=\"float:right\">");
        out.write("<div>");
            out.write("<textarea rows=\"30\" cols=\"100\" disabled=\"True\"></textarea>");
        out.write("</div>");
        out.write("<div>");
            out.write("<input type=\"text\" id=\"mensaje\" name=\"mensaje\" style=\"width:700px;\"/>");
            out.write("<input type=\"button\" value=\"Enviar\" id=\"Enviar\" onclick=\"\"/>");
        out.write("</div>");
        out.write("</div>");
        
        out.write("<h1 id=\"agre_c\" style=\"visibility:\"\"\">Agregar contacto</h1>");
        out.write("<form method=\"POST\">");
        out.write("<P>");
        out.write("<b><label id=\"nom\" style=\"visibility:\"\" \">Nombre<input type=\"text\" id=\"User\" name=\"User\"/></label>");
        out.write("</P>");
        out.write("<P>");
        out.write("<b><label id=\"dir\" style=\"visibility:\"\" \">Direccion IP<input type=\"text\" id=\"IP\" name=\"IP\"/></label>");
        out.write("</P>");
        out.write("<P>");
        out.write("<b><label  id=\"puer\" style=\"visibility:\"\" \">Puerto<input type=\"text\" id=\"Puerto\" name=\"Port\"/></label>");
        out.write("</P>");
        out.write("<input type=\"submit\" value=\"Guardar Contacto\" id=\"agre\" style=\"visibility: \"/>");
        out.write("</form>");
        
        out.write("<form method=\"GET\" name=\"las2\">");
        out.write("<h1>Contactos</h1>");
        out.write("<input type=\"submit\" value=\"Actualizar Contactos\" id=\"mostrar\" />");
        out.write("<input type=\"text\" name=\"get_con\" value=\"asd\" style=\"visibility:hidden \">");
        out.write("</form>");
        
        out.write("<select name=\"drop1\" id=\"select1\"> method=\"GET\"");
        out.write("</select>");
        out.write("<input type=\"button\" value=\"Detalle\" id=\"detalle\" onclick=\"abc()\"/>");
        out.write("<P>");
	out.write("<label  id=\"deta\" ></label>");
        out.write("</P>");
        out.write("<script language=\"javascript\">");  
        out.write("function abc()"); 
        out.write("{");
        out.write("var TerminalType = document.getElementById(\"select1\").value;");
        out.write("document.getElementById(\"deta\").innerHTML=TerminalType;");
         out.write("<body>");

        
        out.write("}");
        out.write("</script>");
        in = new DataInputStream(clientSocket.getInputStream());
        
        String inputRequest;
//        List<String> inputRequest = new ArrayList<String>();
        StringBuilder buff = new StringBuilder();
        int i;
        byte[] buffer = new byte[2048];
        i = in.read(buffer);
        for (int j = 0; j < i; j++) {
            buff.append((char) buffer[j]);           
        }
//        System.out.println(buff.toString());
//        System.out.println("\n");
        inputRequest=buff.toString();
        String[] line1 = inputRequest.split(" ");
        /*out.write("<h1>"+inputRequest+"</h1>");
        out.write("<h1>"+line1[0]+"</h1>");
        out.write("<h1>"+line1[1]+"</h1>");*/
        String method = line1[0];
        if(method.equals("POST")){
            System.out.println("metodo: " + method);
            String[] tokens = inputRequest.split("\n");
            for (i = 0; i < tokens.length; i++){
                System.out.println(tokens[i]);
            }
            String users=tokens[i-1];
            BufferedWriter aux = null;
            try  
            {
                FileWriter fstream = new FileWriter("clients.txt", true); //true tells to append data.
                aux = new BufferedWriter(fstream);
                aux.write(users);
                aux.newLine();
                //agregar window.location.href="http://localhost:8000"
            }
            catch (IOException e)
            {
                System.err.println("Error: " + e.getMessage());
            }
            finally
            {
                if(aux != null) {
                    aux.close();
                }
            }
            
        }
        else if(line1[1].equals("/?get_con=asd") && method.equals("GET")){
            System.out.println("metodo: " + method);
            String[] tokens = inputRequest.split("\n");
            
            for (i = 0; i < tokens.length; i++){
                System.out.println(tokens[i]);
            }
            String users=tokens[i-1];
            BufferedWriter aux = null;
            try{
                // Open the file that is the first 
                // command line parameter
                FileInputStream fstream = new FileInputStream("clients.txt");
                // Get the object of DataInputStream
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                //Read File Line By Line
                while ((strLine = br.readLine()) != null)   
                {
                // Print the content on the console
                    String[] line2 = strLine.split("&");
                    int largo=line2[0].length();
                    int largo2=line2[1].length();
                    int largo3=line2[2].length();
                    if(largo!=0)
                    {
                        String line3= line2[0].substring(5,largo);
                        String line4= line2[1].substring(3,largo2);
                        String line5= line2[2].substring(5,largo3);
                        String ccc="USUARIO: "+line3+"    IP: "+ line4+"   PUERTO: "+line5;
                        System.out.println (ccc);
                        /*System.out.println (line3);
                    System.out.println (line4);
                    System.out.println (line5);*/
                        //out.write("<h1>"+largo+"</h1>");
                        out.write("<script language=\"javascript\">");



                        out.write(
    "            var mySel = select1; // Listbox Name\n" +
    "            var myOption;\n" +
    "\n" +
    "            myOption = document.createElement(\"Option\");\n" +
    "            myOption.text = \""+line3+"\"; //Textbox's value\n            "
            + "myOption.value =  \""+ ccc+"\"; //Textbox's value\n" +
    "            mySel.add(myOption);");

                        out.write("</script>");
                    }
                }
                //Close the input stream
                in.close();
  
    }catch (Exception e){//Catch exception if any
  System.err.println("Error: " + e.getMessage());
  }
            finally
            {
                if(aux != null) {
                    aux.close();
                }
            }
           
        }
        System.err.println("Conexion con el cliente terminada");
        out.close();
        clientSocket.close();

        }   
    }
}
