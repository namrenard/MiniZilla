package serv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}
	public void execute() {

	    try {
		    // Récupérer le nom du fichier à stocker
		    String nomFichier = super.commandeArgs[0];
		    String cheminSource = socket.getcurrentDir()+"/"+nomFichier;
		    String cheminDestination = CommandExecutor.racineDir+"/"+nomFichier;
		    Path pathDestination = Paths.get(cheminDestination).toAbsolutePath();
		    Path pathSource = Paths.get(cheminSource).toAbsolutePath();
		    //ps.println("cheminDestination");
		    if(Files.exists(pathSource)) {
		    	File myFile = new File(cheminSource);
	            ServerSocket serverSocket = new ServerSocket(4010);
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
	    		ps.println("2 - fichier introuvable");
	    	}
	    } catch (IOException e) {
			e.printStackTrace();
		} 
	       
	        ps.println("0 - Le fichier "+super.commandeArgs[0]+" à été copié sur le serveur");

	}
	

}
