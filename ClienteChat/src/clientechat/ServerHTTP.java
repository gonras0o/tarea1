/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientechat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Tomas
 */
class ServerHTTP implements Runnable {
private static BufferedWriter out=null;
private static Socket ServerHttp=null;
private static ServerSocket SocketHttp=null;
private static DataInputStream in;
private static StringBuilder stringBuilder;
private static String Mensaje = "prueba";
private static boolean Enviado = true;
private static String Nick="prueba";
private static boolean HayNick = false;
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
        out.write("<form method=\"POST\" id=\"nick2\" name=\"nick2\"  style=\"visibility:visible\">");
      
        out.write("<input type=\"text\" id=\"nick\" name=\"nick\" style=\"visibility:visible width:100px;\"/>");
        
        out.write("<input type=\"submit\" value=\"Logear\" id=\"logear\" style=\"visibility: \"/>");
     
        out.write("</form>");
        out.write("</div>");
        out.write("<div>");
        out.write("<textarea id=\"area1\" name=\"area1\" rows=\"30\" cols=\"100\" disabled=\"True\" style=\"visibility:hidden\"></textarea>");
        out.write("</div>");

        out.write("<div>");
        out.write("<form method=\"POST\" id=\"nnn\" name=\"nnn\" style=\"visibility:hidden\">");
      
        out.write("<input type=\"text\" id=\"mensaje\" name=\"mensaje\" style=\"width:700px;\"/>");
        
        out.write("<input type=\"submit\" value=\"Enviar\" id=\"Enviar\" style=\"visibility: \"/>");
     
        out.write("</form>");
        out.write("</div>");
        out.write("<form method=\"GET\" id=\"las3\" name=\"las3\"  style=\"visibility:hidden\">");
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
        
        out.write("<form method=\"GET\" name=\"las2\" >");
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
                String users4= users.substring(0,4);
                if(users2.equals("mensaje"))
                {
                    out.write("<script language=\"javascript\">");
                    out.write("{");
                    out.write("document.getElementById('nick2').style.visibility= \"hidden\" ;");
                    out.write("document.getElementById('nnn').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('area1').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('las3').style.visibility= \"visible\" ;");
                    out.write("}");
                    out.write("</script>");
                    BufferedWriter aux = null;
                    try  
                    {
                        FileWriter fstream = new FileWriter("mensajes.txt", true); //true tells to append data.
                        aux = new BufferedWriter(fstream);
                        String users3=users.substring(8,users.length());
                        //System.out.println("users3: " + users3);
                        String[] las = users3.split("\\+");
                        stringBuilder = new StringBuilder();
                        int w;
                        for(w=0;w<las.length;w++)
                        {
                            aux.write(las[w]+" ");
                            stringBuilder.append(las[w]+" ");
                        }
                        aux.newLine();
                        SetMensaje(stringBuilder.toString());
                        Enviado=false;
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
                else if(users4.equals("nick"))
                {
                    //aca guardo el nick en la variable nick
                    Nick=users.substring(5,users.length());
                    HayNick=true;
                    System.out.println(Nick);
                    out.write("<script language=\"javascript\">");
                    out.write("{");
                    out.write("document.getElementById('nick2').style.visibility= \"hidden\" ;");
                    out.write("document.getElementById('nnn').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('area1').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('las3').style.visibility= \"visible\" ;");
                    out.write("}");
                    out.write("</script>");
      
                }
                else
                {
                    out.write("<script language=\"javascript\">");
                    out.write("{");
                    out.write("document.getElementById('nick2').style.visibility= \"hidden\" ;");
                    out.write("document.getElementById('nnn').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('area1').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('las3').style.visibility= \"visible\" ;");
                    out.write("}");
                    out.write("</script>");
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
            out.write("<script language=\"javascript\">");
                    out.write("{");
                    out.write("document.getElementById('nick2').style.visibility= \"hidden\" ;");
                    out.write("document.getElementById('nnn').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('area1').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('las3').style.visibility= \"visible\" ;");
                    out.write("}");
                    out.write("</script>");
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
            out.write("<script language=\"javascript\">");
                    out.write("{");
                    out.write("document.getElementById('nick2').style.visibility= \"hidden\" ;");
                    out.write("document.getElementById('nnn').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('area1').style.visibility= \"visible\" ;");
                    out.write("document.getElementById('las3').style.visibility= \"visible\" ;");
                    out.write("}");
                    out.write("</script>");
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
                        //String hhh="\n   ";
                        out.write(
                        "var aux=\""+strLine+"\";"+
                        "area1.value+=aux;" 
                        );
                 
                        //out.write("area1.value+='\n'");
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
    public String GetMensaje(){
        return Mensaje;
    }
    public boolean GetEnviado(){
        return Enviado;
    }
    public void SetEnviado(){
        Enviado=true;
    }
    public void SetMensaje(String nuevo){
        Mensaje=nuevo;
    }
    public String GetNick(){
        return Nick;
    }
    public boolean GetHayNick(){
        return HayNick;
    }
}