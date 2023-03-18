package serv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Serveur2 {
    private static final String ROOT_DIRECTORY = "/home/user/ftp"; // dossier racine du serveur FTP
    private static final Map<String, String> USER_DIRECTORIES = new HashMap<>(); // dossiers courants des utilisateurs
    private static final String WELCOME_MESSAGE = "220 Welcome to FTP Server";

    public static void main(String[] args) throws IOException {
        System.out.println("FTP Server running on port 2121");

        // Initialisation des dossiers courants des utilisateurs
        USER_DIRECTORIES.put("user1", "/user1");
        USER_DIRECTORIES.put("user2", "/user2");

        ServerSocket serverSocket = new ServerSocket(2121);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private String currentDirectory; // dossier courant du client

        public ClientHandler(Socket socket) {
            clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintStream ps = new PrintStream(clientSocket.getOutputStream())) {

            	ps.println(WELCOME_MESSAGE);
                String command;
                boolean isAuthenticated = false;

                while ((command = input.readLine()) != null) {
                    if (!isAuthenticated) {
                        // Vérification des identifiants de l'utilisateur
                        if (command.startsWith("user")) {
                            String username = command.substring(5).trim();
                            ps.println(username);
                            isAuthenticated = authenticateUser(username);
                            if (isAuthenticated) {
                                currentDirectory = USER_DIRECTORIES.get(username);
                                ps.println("230 User logged in, proceed.");
                            } else {
                            	ps.println("530 Login incorrect.");
                            }
                        } else {
                        	ps.println("530 Please log in with USER and PASS.");
                        }
                    } else {
                        // Exécution des commandes après authentification
                        CommandExecutor.executeCommande(ps, currentDirectory, command);
                    }
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean authenticateUser(String username) {
            // Vérification des identifiants de l'utilisateur
            // Ici, on considère que l'utilisateur est authentifié s'il existe un dossier portant son nom dans le dossier racine
            File userDirectory = new File(ROOT_DIRECTORY, username);
            return userDirectory.exists() && userDirectory.isDirectory();
        }
    }
}