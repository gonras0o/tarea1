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
        
    static final String HTML_START = 
			"<html>" +
			"<title>HTTP POST Server in java</title>" +
			"<body>";
			
    static final String HTML_END = 
			"</body>" +
			"</html>";
    
    public static void main(String[] args) throws Exception {
//    int port = 8000;
    ServerSocket serverSocket = new ServerSocket(8000);
    System.err.println("el servidor utiliza el puerto : 8000");

    while (true) {
        
        Socket clientSocket = serverSocket.accept();
        System.err.println("nuevo cliente conectado");

//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
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
        System.out.println(buff.toString());
        System.out.println("\n");
        inputRequest=buff.toString();
        String[] line1 = inputRequest.split(" ");
        String method = line1[0];
        if(method.equals("POST")){
            System.out.println("metodo: " + method);
            String[] tokens = inputRequest.split("\n");
            
            for (i = 0; i < tokens.length; i++){
                System.out.println(tokens[i]);
            }
            String users=tokens[i-1];
            
            BufferedWriter out = null;
            try  
            {
                FileWriter fstream = new FileWriter("clients.txt", true); //true tells to append data.
                out = new BufferedWriter(fstream);
                out.write(users);
                out.newLine();
            }
            catch (IOException e)
            {
                System.err.println("Error: " + e.getMessage());
            }
            finally
            {
                if(out != null) {
                    out.close();
                }
            }
        }
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: Mon, 23 May 2005 22:38:34 GMT\r\n");
        out.write("Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Connection: close\r\n");
        out.write("\r\n");
        out.write("<h1>Agregar contacto</h1>");
        out.write("<form method=\"POST\">");
        out.write("<P>");
        out.write("<b><label>Nombre<input type=\"text\" id=\"User\" name=\"User\"/></label>");
        out.write("</P>");
        out.write("<P>");
        out.write("<b><label>Direccion IP<input type=\"text\" id=\"IP\" name=\"IP\"/></label>");
        out.write("</P>");
        out.write("<P>");
        out.write("<b><label>Puerto<input type=\"text\" id=\"Puerto\" name=\"Port\"/></label>");
        out.write("</P>");
        out.write("<input type=\"submit\"/>");
        out.write("</form>");

        System.err.println("Conexion con el cliente terminada");
        out.close();
        clientSocket.close();

        }   
    }
}
