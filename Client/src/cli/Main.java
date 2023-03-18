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


public class Main {
	public static boolean userOk = false ;
	public static boolean pwOk = false ;

	    public static void main(String[] args) throws IOException {
	        final Socket client = new Socket("localhost", 2121);
	        final BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        final PrintStream ps = new PrintStream(client.getOutputStream());
	        final Scanner sc = new Scanner(System.in);
	        Thread envoyer = new Thread(new Runnable() {
	            String msg;

	            @Override
	            public void run() {
	                while (true) {
	                    msg = sc.nextLine();
	                    String[] message = msg.split(" ");
	                    //System.out.println(serv.CommandExecutor.pwOk);
	                    //on regarde si le msg envoyé est 'stor' ou 'get' ET s'il est déja connecté
	                    if ( (message[0].equals("stor") || message[0].equals("get")) && (pwOk && userOk) ) {
	                    	if (message[0].equals("stor")) {
	                                try {
	                                	String cheminSource = "Client/documents/" + message[1];
	                                	File myFile = new File(cheminSource); 
	                            	    Paths.get(cheminSource).toAbsolutePath();
	                                    FileInputStream fis = new FileInputStream(myFile);    // Mise en place lecture du fichier
	                                    ps.println(msg);    // Envoi de la taille du fichier
	                                    BufferedInputStream bis = new BufferedInputStream(fis);    // Mise en place lecture du fichier
	                                    byte[] buffer = new byte[(int) myFile.length()];        // Buffer pour l'envoi d'un fichier (de la taille du fichier)
	                                    bis.read(buffer, 0, buffer.length);                        // Lecture du fichier dans le buffer
	                                    Thread.sleep(100);                                        // Attente d'une seconde pour attendre le serveur
	                                    Socket socketFichier = new Socket("127.0.0.1", 4010);    // Ouverture du socket de transfert 'stor'
	                                    OutputStream os = socketFichier.getOutputStream();        // Préparation de l'envoi au serveur
	                                    os.write(buffer, 0, buffer.length);                        // Envoi du buffer au serveur
	                                    os.flush();                                                // Terminer l'envoi
	                                    bis.close();            // Fermeture du BufferedInputStream
	                                    socketFichier.close();    // Fermeture du socket d'envoi du fichier
	                                } catch (FileNotFoundException te) {
	                                	System.out.println("<< 2 - Le fichier est introuvable ou est un dossier");
	                                } catch (Exception e){
		                                e.printStackTrace();
		                            }
	                        } else {
	                        	
	                     	    try {
	                     	    		String cheminDestination = "Client/documents/" + message[1];
	                     	    		Path pathDestination = Paths.get(cheminDestination).toAbsolutePath();
	                     	    		new File(".");
	                     	    		pathDestination.toFile();
	                     	    		String cheminSource = "Serveur/documents/" + message[1]; //pour regarder si le fichier sur le serveur existe
	                     			    Path pathSource = Paths.get(cheminSource).toAbsolutePath(); //
	                     	    		ps.println(msg);
	                     	    		if(Files.exists(pathSource) && !(Files.isDirectory(pathSource))) {
		                     	            ServerSocket serverSocket = new ServerSocket(4002); //ouverture du socket de recu du 'get'
		                     	            Socket clientSocket = serverSocket.accept(); //acceptation de la socket du serveur
		                     	            // Créer un flux d'entrée pour lire les données envoyées par le client
		                     	            InputStream inputStream = clientSocket.getInputStream();
	
		                     	            // Créer un flux de sortie pour écrire les données dans le fichier
		                     	            OutputStream outputStream = new FileOutputStream(pathDestination.toString());
	
		                     	            // Copier les données du flux d'entrée dans le fichier
		                     	            byte[] buffer = new byte[(int) cheminSource.length()];
		                     	            int bytesRead;
		                     	            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                     	                outputStream.write(buffer, 0, bytesRead);
		                     	            }
		                     	            // Fermer les flux et la connexion
		                     	            inputStream.close();
		                     	            outputStream.close();
		                     	            clientSocket.close();
		                     	            serverSocket.close();
	                     	    		}
	                     	    } catch (FileNotFoundException te) {
                                	System.out.println("<< 2 - Le fichier est introuvable ou est un répertoire");
                                }  catch (Exception e) {
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
	                    	if (msg.equals("userOk=true")) {
	                    		userOk=true;
	                    	}
	                    	if (msg.equals("pwOk=true")) {
	                    		pwOk=true;
	                    	}
	                    	else if (!msg.equals("userOk=true") && !msg.equals("pwOk=true")) {
	                            System.out.println("<< " + msg);
	                        }
	                        msg = br.readLine();
	                    }
	                    System.out.println("! Serveur déconnecté !");
	                    sc.close();
	                    ps.close();
	                    client.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        recevoir.start();
	    }
}