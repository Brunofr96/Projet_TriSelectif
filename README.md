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

