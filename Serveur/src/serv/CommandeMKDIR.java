package serv;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CommandeMKDIR extends Commande {
	private Path path;
	
	/**
 * constructeur de la méthode MKDIR
 * @param ps
 * @param commandeStr
 */
	public CommandeMKDIR(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}
	/**
	 * Commande d'éxécution de mkdir
	 */
	@Override
	public void execute() {

		String  commande =  super.commandeArgs[0];
		path = Paths.get(socket.getcurrentDir()+"/"+commande);
		try {
			Files.createDirectories(path);
			File file = path.toFile();
			file.setExecutable(true);
			System.out.println(file.canExecute());
			ps.println("0 - Dossier créé.");
		} catch (FileAlreadyExistsException e) {
			ps.println("2 - Le dossier existe déjà");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
