package serv;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandeLS extends Commande {

	public CommandeLS(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}

	public void execute() {

		Path path = Paths.get(socket.getcurrentDir());
		File dir  = new File(path.toString());	
		File[] liste = dir.listFiles();
	      if(liste.length == 0) {
	    	  ps.println("0 - Le dossier est vide.\n");
	      }else {
		      for(File item : liste){
		        try {
		        	if(item.isFile())
					{ 
					  ps.format("1 - Fichier: %s%n", item.getName());
					} 
					else
					{
					  ps.format("1 - Répertoire: %s%n", item.getName()); 
					}
					
				} catch (Exception e) {
					ps.println("2 - Erreur le dossier n'existe pas");
				} 
		        
		      }	
	      }
	      ps.println("0 - Listing terminé");
	}
}
