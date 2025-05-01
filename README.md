# Projet_TriSelectif

## Pour cloner le repository !! 

Dans Eclipse :

    Installe le plugin EGit (si ce n’est pas déjà le cas)

    File → Import → Git → Projects from Git

    Choisis “Clone URI” et colle l’URL du repo GitHub

    Importe dans Eclipse en tant que General > Project with existing sources

# Structure du projet : 

```
TriSelectif/
├── src/
│   ├── model/          → Toutes les classes métier (générées depuis StarUML)
│   ├── test/           → Classes de tests manuels pour chaque classe métier
│   ├── main/           → Classe MainTest.java qui lance tous les tests
├── .project
├── README.md
```

# créer un token qui sera le mdp qu'il faudra mettre au moment où on se connecte au repo sur Eclipse :
https://github.com/settings/personal-access-tokens

donner ça comme droit au repo : 
![WhatsApp Image 2025-04-19 à 15 03 22_e6808a27](https://github.com/user-attachments/assets/a1eeecaf-b89c-4d7f-b093-e3e57374e8a9)


Quand tu fais Team > Push pour la première fois :

    Username : le nom GitHub

    Password : le token que tu viens de générer (et pas le mot de passe GitHub)

Et il faut cocher "Save credentials" pour ne pas le retaper.


Pour lancer les IHM : 

D'abord faire un git pull origin master pour récup mes dernires modifications

Ensuite clique droit sur le projet > Build path > configure Build path ...

Sur l'onglet Librairie : 

Sélectionnez Classpath > Add Librairy

Il y a deux choses a faire importer : 
	
	- javafx (qui se trouve dans User Librairy)
	- JavaFX SDK (dans JavaFX SDK) 

Vérifier que le MySQL connector est bien connecté lors des pull il se peut qu'il soit pas valide et du coup faut redonner le chemin

Ensuite Apply and Close

Après ça vérifier bien que vous avez les dossiers Controlleurs et IHM 

Enfin pour lancer L'IHM vous allez dans le dossier Main, main.java et vous le lancer 

Et normalement tout marche !!


