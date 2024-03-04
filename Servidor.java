package ciaf.clienteservidor.ejemplomonohilo;

import java.io.*;
import java.net.*;
public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Puerto del servidor

            while (true) {
                Socket socket = serverSocket.accept(); // Espera por la conexión del cliente
                System.out.println("Cliente conectado.");

                // Inicia un nuevo hilo para manejar al cliente
                Thread clienteThread = new Thread(new ClienteHandler(socket));
                clienteThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket socket;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Configura las secuencias de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Lee y escribe mensajes hasta que el cliente se desconecte
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Mensaje del cliente: " + mensaje);
                salida.println("Mensaje recibido: " + mensaje);
            }

            // Cierre de los recursos
            entrada.close();
            salida.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}