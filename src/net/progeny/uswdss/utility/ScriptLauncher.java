package net.progeny.uswdss.utility;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import java.util.Scanner;

public class ScriptLauncher {

  public static void main (String args[]) {
    // Load our arguments
    int port = 3010;

    try {
      if (args.length > 0)
        port = Integer.parseInt(args[0]);
    } catch (Exception e){
      printUsage();
      System.exit(1);
    }

    // Start server socket
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      System.out.println("Script launcher listening on port: " + port);
    } catch (IOException e) {
      System.err.println("Script launcher could not listen on port: " + port);
      e.printStackTrace();
      System.exit(1);
    }

    while (true) {
      // Accept a client and spawn a thread
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        System.out.println("Connection accepted, spawning script thread...");
      } catch (IOException e) {
        System.out.println("Connection accept failed: " + port);
        e.printStackTrace();
      }

      ScriptLauncherThread thread = new ScriptLauncherThread(clientSocket);
      thread.run();
    }
  }

  private static void printUsage(){
    System.out.println("Usage: ScriptLauncher <port>");
  }
}
