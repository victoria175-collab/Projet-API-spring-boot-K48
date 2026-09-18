# API de Gestion des Utilisateurs (avec Authentification JWT)

API REST back-end pure (Spring Boot + Spring Security) pour gérer les utilisateurs et leur authentification par token JWT.

## Stack technique
- Java 21, Spring Boot 3.3.4
- Spring Security + JJWT (jjwt 0.12.6) pour les tokens JWT
- Spring Data JPA + PostgreSQL
- BCrypt pour le hachage des mots de passe
- Swagger / OpenAPI (springdoc)

## Architecture (MVC)
```
controller/  -> AuthController (register/login), UserController (CRUD utilisateurs)
service/     -> AuthService, UserService
repository/  -> UserRepository
entity/      -> User (implémente UserDetails), enum Role
security/    -> JwtService (génération/validation du token), JwtAuthenticationFilter
config/      -> SecurityConfig, UserDetailsServiceImpl, OpenApiConfig
dto/         -> DTOs de requête/réponse, jamais l'entité User exposée directement
exception/   -> Gestion centralisée des erreurs
```

## Prérequis
- JDK 21, Maven 3.9+, Docker (pour PostgreSQL)

## Installation et lancement

### 1. Démarrer PostgreSQL
```bash
docker-compose up -d
```

### 2. Lancer l'application
```bash
mvn spring-boot:run
```
L'API démarre sur `http://localhost:8082`.

### 3. Swagger UI
`http://localhost:8082/swagger-ui.html`

## Tester l'authentification

### 1. Créer un compte
```bash
curl -X POST http://localhost:8082/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Jean Dupont","email":"jean@example.com","password":"motdepasse123"}'
```
Réponse : un objet contenant le `token` JWT.

### 2. Se connecter
```bash
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"jean@example.com","password":"motdepasse123"}'
```

### 3. Appeler un endpoint protégé
```bash
curl http://localhost:8082/api/users \
  -H "Authorization: Bearer <TOKEN_RECU>"
```

Dans Swagger UI, cliquez sur le bouton **Authorize** et collez `Bearer <TOKEN>` pour tester les endpoints protégés directement depuis l'interface.

## Endpoints

| Méthode | URL                 | Auth requise | Description                          |
|---------|---------------------|--------------|---------------------------------------|
| POST    | /api/auth/register  | Non          | Créer un compte                       |
| POST    | /api/auth/login     | Non          | Se connecter, obtenir un JWT          |
| GET     | /api/users          | Oui          | Lister les utilisateurs               |
| GET     | /api/users/{id}      | Oui          | Récupérer un utilisateur              |
| PUT     | /api/users/{id}      | Oui          | Mettre à jour un utilisateur          |
| DELETE  | /api/users/{id}      | Oui          | Supprimer un utilisateur              |

## Sécurité
- Mots de passe stockés hachés avec BCrypt (jamais en clair).
- Sessions stateless : chaque requête protégée doit porter l'en-tête `Authorization: Bearer <token>`.
- Le secret JWT (`app.jwt.secret`) est en clair dans `application.properties` pour la démo ; en production, le fournir via une variable d'environnement.

