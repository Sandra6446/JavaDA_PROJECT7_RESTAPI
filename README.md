# JavaDA_PROJECT7_RESTAPI

Présentation du repository
-----------------------------------

```diff
- La branche master contient le code initial. 
- La branche develop contient le travail final. 
```
-----------------------
Sur la branche develop
-----------------------

Le dossier contient :
  * Le rapport Jacoco
  * La documentation Javadoc
  * Le projet Poseidon (qui contient le README avec tous les prérequis et la notice d'installation)

Pour installer l'application :
 - Vérifier le chemin et le port de la base de donnée et rectifier si nécessaire l'url dans le fichier src/main/resources/application.properties
 - Renseigner le mot de passe de l'utilisateur
 - Ouvrir un terminal dans pmb-backend :
 - > mvn install
 - > java -jar "fichier .jar généré dans target"

Deux utilisateurs par défaut sont utilisables en plus de ceux présents dans le script de création :
 * USER : springuser / spring123
 * ADMIN : springadmin / admin123
