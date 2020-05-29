package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		System.out.println("Executing main method...");
		ServerSocket listener = new ServerSocket(8080);
		while (true) {
			Socket sock = listener.accept();
			new PrintWriter(sock.getOutputStream(), true).println("Hello World!");
			sock.close();
		}
	}

}
