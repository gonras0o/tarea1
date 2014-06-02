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

public class ChatClient extends Thread{
private static int port = 8000; /* el puerto */
private static String host = "localhost"; /* el evidente host */

private static BufferedReader stdIn;
private static BufferedWriter out;
private static String nick;
private static String MensajeParaEnviar;
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
    ServerConn TCPserver = new ServerConn(server);
    Thread ConexionServerTCP = new Thread(TCPserver);
    ConexionServerTCP.start();
    
    ServerHTTP HTTPserver = new ServerHTTP(clientSocket,serverSocket);
    Thread http = new Thread(HTTPserver);
    http.start();
//    String msg;
       
    /* loop leyendo mensajes de stdin y los manda al server */
//    while ((msg = stdIn.readLine()) != null) {
    while (true) {
        MensajeParaEnviar = HTTPserver.GetMensaje();
        if(HTTPserver.GetEnviado()==false){
            out.println(MensajeParaEnviar);
            HTTPserver.SetEnviado();
            out.flush();
        }
        out.flush();
//        out.println(msg);
    }
  }
}

