package net.progeny.uswdss.chci.utility;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import java.util.Scanner;

public class ScriptLauncher {

  public static void main (String args[]) {
    // Load our arguments
    String policy = "";
    int port = 1234;

    try {
      // Load the policy XML
      File policyFile = new File(args[0]);
      Scanner policyScanner = new Scanner(policyFile);

      while(policyScanner.hasNextLine())
        policy += policyScanner.nextLine() + "\n";

      // Save the port
      port = Integer.parseInt(args[1]);
    } catch (Exception e){
      printUsage();
      System.exit(1);
    }

    // Start server socket
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      System.out.println("Server listening on port: " + port);
    } catch (IOException e) {
      System.err.println("Could not listen on port: " + port);
      System.exit(1);
    }

    while (true) {
      // Accept a client and spawn a thread
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("Connection accepted, sending XML...");
      } catch (IOException e) {
        System.out.println("Accept failed: " + port);
      }

      ScriptLauncherThread thread = new ScriptLauncherThread(clientSocket, policy);
      thread.run();
    }
  }

  private static void printUsage(){
    System.out.println("Usage: policyserver <flashpolicy.xml> <port>");
  }
}
