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
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
private static int port = 8000; /* el puerto */
private static String host = "localhost"; /* el evidente host */

private static BufferedReader stdIn;

private static String nick;

/*
    Lee el nickname y trata de autenticar con el servidor, mandando un commando NICK
    a travez de el buffered writer @out. 
    si la respuesta del bufferedreader @in no es ok queda la embarrada
 */
private static String getNick(BufferedReader in, 
                              PrintWriter out) throws IOException {
    System.out.print("Introdusca su nick: ");
    String msg = stdIn.readLine();
    out.println("NICK " + msg);
    String serverResponse = in.readLine();
    if ("SERVER: correcto ".equals(serverResponse)) return msg;
    System.out.println(serverResponse);
    return getNick(in, out);
}

public static void main (String[] args) throws IOException {

    Socket server = null;

    try {
        server = new Socket(host, port);
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
        /* lee mensajes del servidor y los muestra en stdout */
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }
    } catch (IOException e) {
        System.err.println(e);
    }
  }
}
