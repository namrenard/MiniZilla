package serv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandeGET extends Commande {
	
	public CommandeGET(Serveur Client, PrintStream ps, String commandeStr) {
		super(Client,ps,commandeStr);
	}

	public void execute() {

		try {
		    String commande = super.commandeArgs[0];
		    String cheminDestination = socket.getcurrentDir()+"\\"+ commande;
		    Path pathDestination = Paths.get(cheminDestination).toAbsolutePath();
		    String cheminSource = CommandExecutor.racineDir+"\\"+ commande;
		    Path pathSource = Paths.get(cheminSource).toAbsolutePath();
                File myFile = new File(cheminSource);
                Paths.get(cheminDestination).toAbsolutePath();
                //System.out.println(Paths.get(cheminSource).toAbsolutePath().toString());
                if(Files.exists(pathSource)) {
                FileInputStream fis = new FileInputStream(myFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte[] buffer = new byte[(int) myFile.length()];
                bis.read(buffer, 0, buffer.length);
                Thread.sleep(100);
                Socket socketFichier = new Socket("127.0.0.1", 4002);
                OutputStream os = socketFichier.getOutputStream();
                os.write(buffer, 0, buffer.length);
                os.flush();
                bis.close();
                socketFichier.close();
                ps.println("0 - Le fichier "+super.commandeArgs[0]+" à copié sur le client");
                }
        } 
		catch (FileNotFoundException e){
        	ps.println("Le fichier est inexistant ou est un dossier");
        }catch (Exception e){
        	e.printStackTrace();
        }
		
	}

}
