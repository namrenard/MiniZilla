package serv;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandePASS extends Commande {
	
	public CommandePASS(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}

	public void execute() {
		String commande = super.commandeArgs[0];
	    String chemin = socket.getcurrentDir()+"/pwd.txt";
	    Path user = Paths.get(chemin).toAbsolutePath();
	    ps.println(commande);
	    String pwd;
	    if(socket.getUserOk()) {
		try {
			pwd = Files.readString(user);
		
		    if (pwd.equals(commande)) {
		    	socket.setpwdOk(true);
		    	ps.println("1 - Commande pass OK");
				ps.println("0 - Vous êtes bien connecté sur notre serveur");
		    } else {
		    	ps.println("2 - Le mode de passe est faux");
		    }
	    
		} catch (IOException e) {
			ps.println("erreur");
		}
	    } else {
	    	ps.println("2 - Entrez le user en premier");
	    }
	}

}
