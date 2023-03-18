package serv;

import java.io.PrintStream;

public abstract class Commande {
	
	protected PrintStream ps;
	protected String commandeNom = "";
	protected String [] commandeArgs ;
	Serveur socket;
	public Commande(Serveur Client, PrintStream ps, String commandeStr) {
		this.ps = ps ;
		socket = Client;
		String [] args = commandeStr.split(" ");
		commandeNom = args[0];
		commandeArgs = new String[args.length-1];
		for(int i=0; i<commandeArgs.length; i++) {
			commandeArgs[i] = args[i+1];
		}
	}
	
	public abstract void execute();

}
