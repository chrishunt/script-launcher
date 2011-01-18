package net.progeny.uswdss.chci.utility;

import java.net.Socket;

import java.io.*;

public class ScriptLauncherThread extends Thread {
  private Socket clientSocket = null;
  private String policy = null;

  public ScriptLauncherThread (Socket clientSocket, String policy){
    this.clientSocket = clientSocket;
    this.policy = policy;
  }

  public void run(){
    try {
      // See if this is a policy request
      BufferedReader in = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));
      char[] request = new char[30];
      in.read(request);
      String requestString = new String(request).trim();

      if(requestString.equals("<policy-file-request/>")){
        // Create an new output stream writer and send the XML
        OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
        out.write(policy + '\0');
        out.flush();
      }

      clientSocket.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}
