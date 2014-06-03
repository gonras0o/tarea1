/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientechat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Tomas
 */
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