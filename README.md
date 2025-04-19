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
