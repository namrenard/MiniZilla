package serv;


import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}

	public void execute() {

		    String commande = super.commandeArgs[0];
		    String chemin = "user_" + commande;
		    Path user = Paths.get(chemin).toAbsolutePath();
		    if(Files.isDirectory(user) && Files.exists(user)) {
		    	socket.setUserOk(true);
		    	socket.setuser(commande);
		    	//
		    	socket.setcurrentDir(user.toString());  
		    	//CommandExecutor.racineDir = user.toString();
		    	//CommandExecutor.currentDir = CommandExecutor.racineDir;
		    	//
		    	ps.println("0 Commande user OK");
		    	ps.println("CD&"+user.toString());
		    } else {
		    	ps.println("2 - Le user \"" + commandeArgs[0] + "\" n'existe pas");
		    }

	}
}
