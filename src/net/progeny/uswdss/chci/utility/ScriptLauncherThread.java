package net.progeny.uswdss.chci.utility;

import java.net.Socket;

import java.io.*;

public class ScriptLauncherThread extends Thread {
  private final String PASSWORD = "hellolocalhost";
  private Socket clientSocket = null;

  public ScriptLauncherThread (Socket clientSocket){
    this.clientSocket = clientSocket;
  }

  public void run(){
    try {
      // Confirm that this is a script push
      BufferedReader in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));

      // Wait for password, if match then continue
      String password = in.readLine();
      if (password != null && password.equals(PASSWORD)){
        String script = in.readLine();
        if (script != null){
          PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
          try{
            // Wait for filename of script to execute, return SUCCESS or FAILED
            Runtime.getRuntime().exec(script);
            out.println("SUCCESS");
          } catch (Exception e){
            out.println("FAILED");
          }
        }
      }
      System.out.println("Connection closed.");
      clientSocket.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}
