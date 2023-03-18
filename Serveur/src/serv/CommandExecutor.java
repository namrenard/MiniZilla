package serv;

import java.io.PrintStream;

public class CommandExecutor {

	public static String  racineDir = System.getProperty("user.dir");
			
	public static void executeCommande(Serveur Client, PrintStream ps, String commande) {
		if(commande.split(" ")[0].equals("cd") || commande.split(" ")[0].equals("get") || 
				   commande.split(" ")[0].equals("ls") || commande.split(" ")[0].equals("pwd") || 
				   commande.split(" ")[0].equals("stor") || commande.split(" ")[0].equals("mkdir") || 
				   commande.split(" ")[0].equals("rmdir") || commande.split(" ")[0].equals("pass") || 
				   commande.split(" ")[0].equals("user")) {
			if( (commande.split(" ").length > 1 && commande.split(" ").length <=2) || (commande.split(" ")[0].equals("ls")) || (commande.split(" ")[0].equals("pwd"))) {
				if(Client.getPwdOk() && Client.getUserOk()) {
					
					// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
					if(commande.split(" ")[0].equals("cd")) (new CommandeCD(Client, ps, commande)).execute();
			
					// Telecharger un fichier
					if(commande.split(" ")[0].equals("get")) (new CommandeGET(Client, ps, commande)).execute();
					
					// Afficher la liste des fichiers et des dossiers du repertoire courant
					if(commande.split(" ")[0].equals("ls")) (new CommandeLS(Client, ps, commande)).execute();
				
					// Afficher le repertoire courant
					if(commande.split(" ")[0].equals("pwd")) (new CommandePWD(Client, ps, commande)).execute();
					
					// Envoyer (uploader) un fichier
					if(commande.split(" ")[0].equals("stor")) (new CommandeSTOR(Client, ps, commande)).execute();
					
					// Creation d'un dossier dans le répertoire courant
					if(commande.split(" ")[0].equals("mkdir")) (new CommandeMKDIR(Client, ps, commande)).execute();
					
					// Suppression d'un dossier dans le repertoire courant
					if(commande.split(" ")[0].equals("rmdir")) (new CommandeRMDIR(Client, ps, commande)).execute();
				}
				else {
					if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
						// Le mot de passe pour l'authentification
						if(commande.split(" ")[0].equals("pass")) (new CommandePASS(Client, ps, commande)).execute();
			
						// Le login pour l'authentification
						if(commande.split(" ")[0].equals("user")) (new CommandeUSER(Client, ps, commande)).execute();
					}
					else
						ps.println("2 Vous n'êtes pas connecté !");
				}
			} else {
				ps.println("Entrez un paramètre !");
			}
		} else {
			ps.println("Commande non reconnue : "+commande.split("")[0]);
		}
	}

}
