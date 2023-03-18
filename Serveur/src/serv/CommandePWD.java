package serv;

import java.io.PrintStream;

public class CommandePWD extends Commande {
	/**
	 * Constructeur de la méthode CommandePWD
	 * 
	 * @param ps
	 * @param commandeStr
	 */
	public CommandePWD(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}
	
	/**
	 * Méthode d'éxecution de la commande PWD
	 * <p>
	 * Elle affiche le chemin absolu du dossier courant.
	 * 
	 */
	public void execute() {
		ps.println("0 " + socket.getcurrentDir());
	}
}
	
