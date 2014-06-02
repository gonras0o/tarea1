

package clientechat;

/**
 *
 * @author Paracelso
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URI;

import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Thread {
private static int port = 8000; /* el puerto */
private static String host = "localhost"; /* el evidente host */

private static BufferedReader stdIn;
private static BufferedWriter out;
private static String nick;
/*
    Lee el nickname y trata de autenticar con el servidor, mandando un commando NICK
    a travez de el buffered writer @out. 
    si la respuesta del bufferedreader @in no es ok queda la embarrada
 */
private static String getNick(BufferedReader in,PrintWriter out) throws IOException {
    System.out.print("Introdusca su nick: ");
    String msg = stdIn.readLine();
    out.println("NICK " + msg);
    String serverResponse = in.readLine();
    if ("SERVER: OK".equals(serverResponse)) return msg;
    System.out.println(serverResponse);
    return getNick(in, out);
}

public static void main (String[] args) throws IOException {

    ServerSocket serverSocket = new ServerSocket(8080);
    Socket server = null;
    Socket clientSocket= null;
//    int pert = 8080;
    try {
        server = new Socket(host, port);
//        clientSocket = serverSocket.accept();
            try{
                if(Desktop.isDesktopSupported()){
                      Desktop.getDesktop().browse(new URI("http://localhost:8080"));
                }}
                catch(Exception e){
                }   
//        clientSocket = new Socket(host,pert);
    } catch (UnknownHostException e) {
        System.err.println(e);
        System.exit(1);
    }

    stdIn = new BufferedReader(new InputStreamReader(System.in));

    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
    /* Obtiene el output eh input reader */
    BufferedReader in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
    
    
        
    nick = getNick(in, out);

    /* thread que lee mensajes asicronicamente */
    ServerConn sc = new ServerConn(server);
    Thread t = new Thread(sc);
    t.start();
    
    ServerHTTP sh = new ServerHTTP(clientSocket,serverSocket);
    Thread http = new Thread(sh);
    http.start();
    String msg;
       
    /* loop leyendo mensajes de stdin y los manda al server */
    while ((msg = stdIn.readLine()) != null) {
        out.println(msg);
    }
  }
}

class ServerConn implements Runnable {
private BufferedReader in = null;

public ServerConn(Socket server) throws IOException {
    /* pal servidor */
    in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
}
public void run() {
    String msg;

    try {
        FileWriter fstream = new FileWriter("mensajes.txt", true); //true tells to append data.
        BufferedWriter aux = null;
        /* lee mensajes del servidor y los muestra en stdout */
        while ((msg = in.readLine()) != null) {
            aux = new BufferedWriter(fstream);
            aux.newLine();
            aux.write(msg);               
            System.out.println(msg);
            aux.flush();
        }
    }catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
    }            
  }
}
class ServerHTTP implements Runnable {
private static BufferedWriter out=null;
private static Socket ServerHttp=null;
private static ServerSocket SocketHttp=null;
private static DataInputStream in;
    public ServerHTTP(Socket server,ServerSocket serverSocket) throws IOException {
        /* pal servidor */
//        o = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        ServerHttp = server;
        SocketHttp=serverSocket;
        
    }
    public void run() {
        String msg;
        try {
            /* lee mensajes del servidor y los muestra en stdout */
            while (true) {
                 ServerHttp = SocketHttp.accept();
                out = new BufferedWriter(new OutputStreamWriter(ServerHttp.getOutputStream()));
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: Mon, 23 May 2005 22:38:34 GMT\r\n");
        out.write("Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("Connection: close\r\n");
        out.write("\r\n");
        out.write("<body>");
        out.write("<div style=\"float:right\">");
        out.write("<div>");
            out.write("<textarea id=\"area1\" name=\"area1\" rows=\"30\" cols=\"100\" disabled=\"True\"></textarea>");
        out.write("</div>");
        
        out.write("<form method=\"POST\" name=\"nnn\" action=\"http://localhost:8080\">");
      
        out.write("<input type=\"text\" id=\"mensaje\" name=\"mensaje\" style=\"width:700px;\"/>");
        
        out.write("<input type=\"submit\" value=\"Enviar\" id=\"Enviar\" style=\"visibility: \"/>");
     
        out.write("</form>");
        out.write("<form method=\"GET\" name=\"las3\" action=\"http://localhost:8080\">");
        out.write("<input type=\"submit\" value=\"Actualizar Chat\" id=\"act_chat\" />");
        out.write("<input type=\"text\" name=\"act_chat\" value=\"asd\" style=\"visibility:hidden \">");
        out.write("</form>");
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
        
        out.write("<form method=\"GET\" name=\"las2\" action=\"http://localhost:8080\">");
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
        out.write("<body>");
        out.write("<script language=\"javascript\">");  
        out.write("function abc()"); 
        out.write("{");
        out.write("var TerminalType = document.getElementById(\"select1\").value;");
        out.write("document.getElementById(\"deta\").innerHTML=TerminalType;");
     

        
        out.write("}");
        out.write("</script>");
        
        in = new DataInputStream(ServerHttp.getInputStream());
        
        String inputRequest;
//        List<String> inputRequest = new ArrayList<String>();
        StringBuilder buff = new StringBuilder();
        int i;
        byte[] buffer = new byte[2048];
        i = in.read(buffer);
        for (int j = 0; j < i; j++) {
            buff.append((char) buffer[j]);           
        }
        inputRequest=buff.toString();
        String[] line1 = inputRequest.split(" ");

        String method = line1[0];
        if(method.equals("POST")){
            System.out.println("metodo: " + method);
                String[] tokens = inputRequest.split("\n");
                int flag=0;
                for (i = 0; i < tokens.length; i++){
                    System.out.println(tokens[i]);
                    
                }
                String users=tokens[i-1];
                String users2= users.substring(0,7);
                if(users2.equals("mensaje"))
                {
                    BufferedWriter aux = null;
                    try  
                    {
                        FileWriter fstream = new FileWriter("mensajes.txt", true); //true tells to append data.
                        aux = new BufferedWriter(fstream);
                        aux.write(users);
                        aux.newLine();
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
                else
                {
                    BufferedWriter aux = null;
                    try  
                    {
                        FileWriter fstream = new FileWriter("clients.txt", true); //true tells to append data.
                        aux = new BufferedWriter(fstream);
                        aux.write(users);
                        aux.newLine();
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
                DataInputStream ina = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(ina));
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
                ina.close();
  
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
        else if(line1[1].equals("/?act_chat=asd") && method.equals("GET"))
        {
            //hacer actualizar!!!!!!!!
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
                FileInputStream fstream = new FileInputStream("mensajes.txt");
                // Get the object of DataInputStream
                DataInputStream ina = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(ina));
                String strLine;
                //Read File Line By Line
                while ((strLine = br.readLine()) != null)   
                {
                // Print the content on the console
                    //String[] line2 = strLine.split("&");
                    int largo=strLine.length();
                    //int largo2=line2[1].length();
                    //int largo3=line2[2].length();
                    if(largo!=0)
                    {
                        //String line3= line2[0].substring(5,largo);
                        //String line4= line2[1].substring(3,largo2);
                        //String line5= line2[2].substring(5,largo3);
                        //String ccc="USUARIO: "+line3+"    IP: "+ line4+"   PUERTO: "+line5;
                        System.out.println (strLine);
                        out.write("<script language=\"javascript\">");



                        /*out.write(
    "            var mySel = select1; // Listbox Name\n" +
    "            var myOption;\n" +
    "\n" +
    "            myOption = document.createElement(\"Option\");\n" +
    "            myOption.text = \""+line3+"\"; //Textbox's value\n            "
            + "myOption.value =  \""+ ccc+"\"; //Textbox's value\n" +
    "            mySel.add(myOption);");*/
                        out.write(
                        "var aux=\""+strLine+"\";"+
                        "area1.value+=aux;" 
                        );
                        out.write("</script>");
                    }
                }
                //Close the input stream
                ina.close();
  
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
        ServerHttp.close();
    
        } 
//        clientSocket.close();
            }
        catch (IOException e) {
            System.err.println(e);
        }
        }
    }
