# MiniZilla
Une petite application en multithreading mimant une partie de Fillezilla.<br>
L'application est en mode cli-command.


# Getting Started
Placez dans le serveur, le dossier Serveur.<br>
Coté **serveur**<br>
Pour du multi-threading, choisir **Serveur.java**.<br>
<br>
Coté **client**, <br>
Pour du multi-threading, choisir **Client.java**.<br>
<br>
####Les commandes :
 - pour se connecter : 
    - connexion dans un dossier utilisateur uniquement. L'utilisateur doit avoir un dossier avec un nom de type "user_login" ou "login" est le nom de l'utilisateur.<br>
    Un fichier texte .txt sous le format pwd.txt doit etre dans ce même dossier et contenir le mot de passe.
    - les commandes sont dans l'ordre **user login** puis **pass pwd**.
 - Les autre commndes sont les suivantes : 
    - pwd : pour voir le dossier courant.
    - cd : pour se déplacer dans les dossiers. NOTE : le déplacement est simulé dans une variable mais pas physiquement. Par la création/suppression de dossier et le listing fonctionne bien dans les dossiers courants simulés.
    - ls : lister les éléments d'un répertoire.
    - mkdir : création d'un dossier vide.
    - rmdir : suppression d'un dossier **vide**.
    - get : récupérer un fichier du dossier courant sur le serveur vers le client. (AKA : téléchargement.)
    - stor : récupérer un fichier du client vers le dossier courant sur le serveur. (AKA : téléversement.)
    - bye : pour quitter et fermer la connexion au serveur.

# Version

1.0

# Feature possible

Une IHM est à envisager.


#Auteurs : 
- Jérémy F. -> [JF Github](https://github.com/xel0x)
- Nicolas R. 
