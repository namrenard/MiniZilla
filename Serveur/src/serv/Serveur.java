package serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread {

	private static int serverPort = 2121;
	
    private Socket socket;
    private String user;
    private Boolean userOk;
    private Boolean pwdOk;
    private String currentDir;
    
    public Serveur(Socket socket) {
        this.socket = socket;
        this.user = "rien";
        this.userOk = false;
        this.pwdOk = false;
        this.currentDir = "";
    }
    
    public void setUserOk(boolean userOk) {
        this.userOk = userOk;
    }
    public void setpwdOk(boolean pwdOk) {
        this.pwdOk = pwdOk;
    }
    public void setuser(String user) {
        this.user = user;
    }
  
    public void setcurrentDir(String currentDir) {
        this.currentDir = currentDir ;
    }
    public Boolean getUserOk() {
        return this.userOk;
    }
    public Boolean getPwdOk() {
       return this.pwdOk;
    }
    public String getcurrentDir() {
    	return this.currentDir;
    }
    public String getuser() {
        return this.user;
     }
    

    
    
	public static void main(String[] args) throws Exception {
		System.out.println("Le Serveur FTP");
		
		try (ServerSocket serveurFTP = new ServerSocket(serverPort)) {
			// Boucle infinie pour gérer les connexions entrantes
			while (true) {
				Socket socket = serveurFTP.accept();
				
				System.out.println("Connexion entrante de l'adresse IP : " + socket.getInetAddress());
				
				// Création d'un nouveau thread pour gérer la connexion
				Serveur clientServeur = new Serveur(socket);
				clientServeur.start();
			}
		}
	}


	

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintStream ps = new PrintStream(this.socket.getOutputStream());
			ps.println("1 Bienvenue ! ");
			ps.println("1 Serveur FTP Personnel.");
			ps.println("0 Authentification : ");
			
			String commande = "";
			
			// Attente de reception de commandes et leur execution
			while(!(commande=br.readLine()).equals("bye")) {
				if(commande.equals("bye"))
					break;
				if(!this.getuser().equals("rien")) {
					ps.println(this.getuser()+" : ");
				}
				System.out.println(">> "+ commande);
				CommandExecutor.executeCommande(this, ps, commande);
				
			}
			
			this.socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}