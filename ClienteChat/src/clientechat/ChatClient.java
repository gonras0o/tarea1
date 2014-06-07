/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
private static String Nick;
/*
    Lee el nickname y trata de autenticar con el servidor, mandando un commando NICK
    a travez de el buffered writer @out. 
    si la respuesta del bufferedreader @in no es ok queda la embarrada
 */
private static String getNick(BufferedReader in,PrintWriter out,String Nick) throws IOException {
    System.out.print("Introdusca su nick: ");
//    String msg = stdIn.readLine();
    String msg=Nick;
    System.out.println(msg);
    out.println("NICK " + msg);
    out.flush();
    String serverResponse = in.readLine();
    if ("SERVER: OK".equals(serverResponse)) return msg;
    System.out.println(serverResponse);
    
    return getNick(in, out,Nick);
}

public static void main (String[] args) throws IOException {

    ServerSocket serverSocket = new ServerSocket(8079);
    Socket server = null;
    Socket clientSocket= null;
//    int pert = 8080;
    try {
        server = new Socket(host, port);
//        clientSocket = serverSocket.accept();
            try{
                if(Desktop.isDesktopSupported()){
                      Desktop.getDesktop().browse(new URI("http://localhost:8079"));
                      
                }}
                catch(Exception e){
                }   
//        clientSocket = new Socket(host,pert);
    } catch (UnknownHostException e) {
        System.err.println(e);
        System.exit(1);
    }
    ServerHTTP HTTPserver = new ServerHTTP(clientSocket,serverSocket);
    Thread http = new Thread(HTTPserver);
    http.start();
    stdIn = new BufferedReader(new InputStreamReader(System.in));

    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
    out.flush();
    /* Obtiene el output eh input reader */
    BufferedReader in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
    boolean TieneNick = false;
    Nick=null;
    while(TieneNick==false){
        if(HTTPserver.GetHayNick()==true){
            Nick=HTTPserver.GetNick(); 
            TieneNick=true;
        }
        
    }
        
    nick = getNick(in, out,Nick);

    /* thread que lee mensajes asicronicamente */
    ServerConn ServerConn = new ServerConn(server);
    Thread tcp = new Thread(ServerConn);
    tcp.start();
    
    String msg;
       
        /* loop leyendo mensajes de stdin y los manda al server */
//    while ((msg = stdIn.readLine()) != null) {
//        out.println(msg);
//    }
    while(true){
        if(HTTPserver.GetEnviado()==false){
            out.println(HTTPserver.GetMensaje());
            HTTPserver.SetEnviado();
            out.flush();
        }
        out.flush();
        }
    }
}