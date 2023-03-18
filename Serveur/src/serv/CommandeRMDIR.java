package serv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandeRMDIR extends Commande{
/**
 * constructeur de la méthode RMDIR
 * @param ps
 * @param commandeStr
 */
	public CommandeRMDIR(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}
/**
 * Commande d'éxécution de rmdir
 */
	@Override
	public void execute() {
		
		String  commande =  super.commandeArgs[0];
		Path path = Paths.get(socket.getcurrentDir()+"/"+commande);
		
		try {
					Files.delete(path);
					ps.println("0 - Le dossier est effacé");
				} catch (NotDirectoryException e) {
					ps.println("2 - Ce n'est pas un dossier");
				} catch (NoSuchFileException te) {
					ps.println("2 - Le dossier est introuvable");
                } catch (DirectoryNotEmptyException te) {
                	ps.println("2 - Le dossier n'est pas vide");
                } catch (IOException e) {
					e.printStackTrace();
				}
			
		}
	//}
}

