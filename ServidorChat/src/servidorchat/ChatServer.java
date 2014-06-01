/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidorchat;
/**
 *
 * @author Paracelso
 */
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.Hashtable;

public class ChatServer {
private static int port = 8000; /* puerto que se esta escuchando */

public static void main (String[] args) throws IOException {

    ServerSocket server = null;
    try {
        server = new ServerSocket(port); /* start listening on the port */
    } catch (IOException e) {
        System.err.println("No se pudo escuchar el puerto: " + port);
        System.err.println(e);
        System.exit(1);
    }

    Socket client = null;
    while(true) {
        try {
            client = server.accept();
        } catch (IOException e) {
            System.err.println("No se pudo aceptar.");
            System.err.println(e);
            System.exit(1);
        }
        /* Empieza un thread, de los multiples pa poder manejar el cliente 
            Wohooo multithreas   */
        Thread t = new Thread(new ClientConn(client));
        t.start();
    }
   }
}

class ChatServerProtocol {
private String nick;
private ClientConn conn;

/* tabla hash de nick + conexiones respectivaz */
private static Hashtable<String, ClientConn> nicks = 
    new Hashtable<String, ClientConn>();

private static final String OK = "OK";
private static final String NickNoValido = "Nick ya en uso";
private static final String IngreseNick = "Ingrese su Nick de manera correcta";
private static final String INVALIDO = "COMANDO INVALIDO";
private static final String Error = "ERROR: Fallo el envio de el mensaje";

/**
 * Agregas el nick a la tabla (supongamos que es como un "inicio" de sesion 
 * Falso = si ya estan usando el nick, True= el nick esta disponible
 medio como chat clasico web xD */
private static boolean add_nick(String nick, ClientConn c) {
    if (nicks.containsKey(nick)) {
        return false;
    } else {
        nicks.put(nick, c);
        return true;
    }
}

public ChatServerProtocol(ClientConn c) {
    nick = null;
    conn = c;
}

private void log(String msg) {
    System.err.println(msg);
}

public boolean isAuthenticated() {
    return ! (nick == null);
}

/**
 * implementa el protocolo de autentificacion:
 * 
 * Chequea que el mensaje este escroto correctamente y que el nick no este usado
 * 
 * 
 * retorna: 
 *  OK = esta todo bn
 *  NikcNoValido = ya existe(o problemas)
 *  IngreseNick no empieza con el commando NICK
 */
private String authenticate(String msg) {
    if(msg.startsWith("NICK")) {
        String tryNick = msg.substring(5);
        if(add_nick(tryNick, this.conn)) {
            log("Nick " + tryNick + " se conecto.");
            this.nick = tryNick;
            return OK;
        } 
        else {
            return NickNoValido;
        }
        } 
        else {
        return IngreseNick;
    }
}

/**
 * Manda Mensajes
 * @recepient tiene el nick del recipiente
 * @msg el mensaje
 * True =  existe el nick
 */
private boolean sendMsg(String recipient, String msg) {
    if (nicks.containsKey(recipient)) {
        ClientConn c = nicks.get(recipient);
        c.sendMsg(nick + ": " + msg);
        return true;
    } else {
        return false;
    }
}

/**
 * Procesa el mensaje que viene de otra parte
 */
public String process(String msg) {
    if (!isAuthenticated()) 
        return authenticate(msg);

    String[] msg_parts = msg.split(" ", 3);
    String msg_type = msg_parts[0];

    if(msg_type.equals("Send")) {
        if(msg_parts.length < 3) return INVALIDO;
        if(sendMsg(msg_parts[1], msg_parts[2])) return OK;
        else return Error;
    } else {
        return INVALIDO;
    }
 }
}

class ClientConn implements Runnable {
private Socket client;
private BufferedReader in = null;
private PrintWriter out = null;

ClientConn(Socket client) {
    this.client = client;
    try {
        in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
        /*Obtiene el inputstream ...  y su respectivo output */
        out = new PrintWriter(client.getOutputStream(), true);
    } catch (IOException e) {
        System.err.println(e);
        return;
    }
}

public void run() {
    String msg, response;
    ChatServerProtocol protocol = new ChatServerProtocol(this);
    try {
        while ((msg = in.readLine()) != null) {
            response = protocol.process(msg);
            out.println("SERVER: " + response);
             /* loop que lee la lineas del cliente y que se procesan a travez del protoloco
            si esta todo ok se le envia respuesta al cleinte */
        }
    } catch (IOException e) {
        System.err.println(e);
    }
}

public void sendMsg(String msg) {
    out.println(msg);
 }
}