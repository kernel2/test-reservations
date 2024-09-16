# Online Bus Ticket Reservation System

## Description

Ce projet est un système de réservation de billets de bus en ligne développé avec **Spring Boot** (Backend) et **Angular** (Frontend). Il utilise une base de données **H2** en mémoire pour simplifier le développement et les tests.

---

## Prérequis

Assurez-vous d'avoir les outils suivants installés sur votre machine :

- **Java 21**
- **Maven 3.x**
- **Node.js 16.20.2**
- **Angular 16**
- **Bootstrap**

---

## Installation et Démarrage

### Backend (Spring Boot)

1. Ouvrez un terminal ou une invite de commande.

2. Accédez au dossier backend du projet avec la commande suivante :

   ```bash
   cd back/reservations/
3. Démarrez le serveur Spring Boot avec Maven :
   
   ```bash
   mvn spring-boot:run

4. Accédez à la base de données H2 via le lien suivant après avoir démarré le serveur :

   ```URL
   http://localhost:8080/h2-console/login.jsp

## Base de données H2
La base de données H2 est une base en mémoire qui est automatiquement configurée par Spring Boot. Après le démarrage de l'application, vous pouvez y accéder via :

URL : H2 Console

Les informations de connexion sont les suivantes :
   ```text
        JDBC URL : jdbc:h2:mem:testdb
        User : sa
        Password : Laisser vide
   ```
## API Tests (Postman)
Pour tester les API, utilisez Postman. Nous avons fourni une collection Postman prête à l'emploi. Voici comment procéder :

1.  Utilisation de Postman.
2.  Importez la collection disponible dans le dossier CollectionsPostman :
      
    ```sql
    Online Bus Ticket System V1.0.0.postman_collection.json

Cette collection contient des requêtes API que vous pouvez utiliser pour tester les fonctionnalités du système.

## Frontend (Angular)
1. Ouvrez un terminal et accédez au dossier du frontend :

      ```bash
       cd front/
       
2. Installez les dépendances Node.js avec la commande suivante :

      ```bash
      npm install

3. Démarrez l'application Angular en mode développement avec la commande suivante :
    
      ```bash
       npm run dev

4. Accédez à l'application Angular dans votre navigateur à l'URL suivante :
      ```URL 
       http://localhost:4200

## Structure du projet
- **Backend** : Spring Boot (dans le dossier back/reservations/)
- **Frontend**: Angular (dans le dossier front/
- **Base de données**: H2 (en mémoire)
- **Tests d'API**: Postman (fichier de collection Online Bus Ticket System V1.0.0.postman_collection.json dans le dossier CollectionsPostman)