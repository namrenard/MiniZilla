package cli;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import serv.CommandExecutor;

public class Client {
    public static String currentDir = "";

    public static void main(String[] args) throws IOException {
        final Socket client = new Socket("localhost", 2121);
        final BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        final PrintStream ps = new PrintStream(client.getOutputStream());
        final Scanner sc = new Scanner(System.in);
        
        // Thread pour l'envoi de messages
        Thread envoyer = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                while (true) {
                    msg = sc.nextLine();
                    String[] message = msg.split(" ");
                    
                    // Vérification si le message envoyé est 'stor' ou 'get' ET si l'utilisateur est connecté
                    if ((message[0].equals("stor") || message[0].equals("get"))) {
                        if (message[0].equals("stor")) {
                            try {
                            	String cheminSource = currentDir+"\\"+message[1];
                            	Path pathSource = Paths.get(cheminSource).toAbsolutePath();
                            	if(Files.exists(pathSource)) {
                            		ps.println(msg);
	                                File myFile = new File(cheminSource);
	                                Paths.get(cheminSource).toAbsolutePath();
	                                //System.out.println(Paths.get(cheminSource).toAbsolutePath().toString());
	                                
	                                FileInputStream fis = new FileInputStream(myFile);
	                                BufferedInputStream bis = new BufferedInputStream(fis);
	                                byte[] buffer = new byte[(int) myFile.length()];
	                                bis.read(buffer, 0, buffer.length);
	                                Thread.sleep(100);
	                                Socket socketFichier = new Socket("127.0.0.1", 4010);
	                                BufferedReader br = new BufferedReader(new InputStreamReader(socketFichier.getInputStream()));
	                                OutputStream os = socketFichier.getOutputStream();
	                                os.write(buffer, 0, buffer.length);
	                                os.flush();
	                                bis.close();
	                                socketFichier.close();
                                } else {
                                	System.out.println("<< 2 - Le fichier est introuvable ou est un dossier");
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                            	String nomFichier = message[1];
                    		    String cheminDestination = currentDir+"\\"+nomFichier;
                    		    String cheminSource = CommandExecutor.racineDir+"\\"+nomFichier;
                    		    Path pathDestination = Paths.get(cheminDestination).toAbsolutePath();
                    		    Path pathSource = Paths.get(cheminSource).toAbsolutePath();
                    		    //ps.println("cheminDestination");
                    		    if(Files.exists(pathSource)) {
                    		    	ps.println(msg);
                    	            ServerSocket serverSocket = new ServerSocket(4002);
                    	            Socket clientSocket = serverSocket.accept();
                    	            // Créer un flux d'entrée pour lire les données envoyées par le client
                    	            InputStream inputStream = clientSocket.getInputStream();

                    	            // Créer un flux de sortie pour écrire les données dans le fichier
                    	            OutputStream outputStream = new FileOutputStream(pathDestination.toString());
                    	            // Copier les données du flux d'entrée dans le fichier
                    	            byte[] buffer = new byte[4096];
                    	            int bytesRead;
                    	            while ((bytesRead = inputStream.read(buffer)) != -1) {
                    	                outputStream.write(buffer, 0, bytesRead);
                    	                
                    	            }

                    	            // Fermer les flux et la connexion
                    	            inputStream.close();
                    	            outputStream.close();
                    	            clientSocket.close();
                    	            serverSocket.close();
                                } else {
                                    System.out.println("<< 2 - Le fichier est introuvable ou est un répertoire");
                            } 
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ps.println(msg);
                        ps.flush();
                    }
                }
            }
        });
        envoyer.start();
        Thread recevoir = new Thread(new Runnable() {
            String msg;

            @Override
            public void run() {
                try {
                    msg = br.readLine();
                    while (msg != null) {
                     
                        if (msg.startsWith("CD&")) {
                        	String[] message = msg.split("&");
                        	currentDir = message[1];
                        }else if (!msg.equals("userOk=true") && !msg.equals("pwOk=true") && !msg.startsWith("CD&")) {
                            System.out.println("<< " + msg);
                        }
                        msg = br.readLine();
                    }
                    System.out.println("! Serveur déconnecté !");
                    sc.close();
                    ps.close();
                    client.close();
                } catch (IOException e) {
                    System.err.println("Erreur serveur déconnecté");
                }
            }
        });
        recevoir.start();
    }
}
