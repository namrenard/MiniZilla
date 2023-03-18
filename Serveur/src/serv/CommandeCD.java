package serv;

import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	
	
	/**
	 * Constructeur de la commande CD
	 * @param ps
	 * @param commandeStr
	 */
	public CommandeCD(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}
	
	/**
	 * Commande d'éxécution de CD
	 */
	public void execute() {		
		
		// recupération chemin absolu du dossier courant.
		String[] path = commandeArgs;
		if((path.length) == 0){
			ps.println("2 La commande est vide.");
		} else {
			File currentDir = new File(socket.getcurrentDir()); // le dossier courant transformer en file
			File futurDir; // le futur dossier courant
			String newpath = path[0]; // la commande
			if(newpath.equals(".")) {
				ps.print("2 Vous n'avez pas bougé.");
			} else if (newpath.equals("/")){
				socket.setcurrentDir(CommandExecutor.racineDir);
				ps.print("0 Déplacement dans le dossier racine.");
				ps.println("CD&"+CommandExecutor.racineDir.toString());
			} else if (newpath.equals("..")) {
				if(currentDir.toString().equals(CommandExecutor.racineDir)) {
					ps.print("2 Vous êtes dans le dossier racine.\n Vous n'avez pas les droit pour remonter plus haut.");
				}else {
					futurDir = new File (currentDir.getParentFile().getAbsolutePath());
					socket.setcurrentDir(futurDir.toString());
					ps.println("CD&"+futurDir.toString());
				}  
			} else {
				if(newpath.startsWith("/")) { // chemin absolu
					futurDir = new File (newpath);
				}else {
					futurDir = new File (currentDir.getAbsolutePath()+ "/" + newpath);
					//DEBUG
					ps.print("DEBUG 1: "+ futurDir.toString() 
					+ "\n");
					//
					
				}
				if (!(futurDir.exists())) {
					ps.println("2 Erreur le dossier n'existe pas.");
				}else if (!(futurDir.exists())) {
					ps.println("2 Erreur, ce n'est pas un dossier.");
				}else {
					socket.setcurrentDir(futurDir.toString());
					ps.println("0 Déplacement effectué.");
					ps.println("CD&"+futurDir.toString());
					
				}// chemin relatif
				
			}
		} // le chemin de CD
	}

}
