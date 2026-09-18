# API de Gestion de Tâches (To-Do List)

API REST back-end pure (Spring Boot) permettant de créer, lire, mettre à jour, supprimer et filtrer des tâches par statut.

## Stack technique
- Java 21, Spring Boot 3.3.4
- Spring Data JPA + PostgreSQL
- Lombok
- Bean Validation (Jakarta)
- Swagger / OpenAPI (springdoc)

## Architecture (MVC)
```
controller/   -> Endpoints REST (TaskController)
service/      -> Logique métier (TaskService / TaskServiceImpl)
repository/   -> Accès aux données (Spring Data JPA)
entity/       -> Entité JPA Task, enum TaskStatus
dto/          -> TaskRequestDTO, TaskResponseDTO, ApiErrorResponse
exception/    -> Gestion centralisée des erreurs (@RestControllerAdvice)
```

## Prérequis
- JDK 21
- Maven 3.9+
- Docker (pour lancer PostgreSQL rapidement) ou une instance PostgreSQL locale

## Installation et lancement

### 1. Démarrer la base de données PostgreSQL
```bash
docker-compose up -d
```
Cela lance PostgreSQL sur `localhost:5432` avec la base `taches_db` (user: `postgres`, password: `postgres`).

Sans Docker, créez simplement une base `taches_db` dans votre PostgreSQL local et adaptez `src/main/resources/application.properties` si besoin.

### 2. Lancer l'application
```bash
mvn spring-boot:run
```
L'API démarre sur `http://localhost:8081`.

### 3. Tester via Swagger UI
Ouvrir : `http://localhost:8081/swagger-ui.html`

## Endpoints

| Méthode | URL                          | Description                          |
|---------|------------------------------|---------------------------------------|
| POST    | /api/tasks                   | Créer une tâche                       |
| GET     | /api/tasks                   | Lister les tâches                     |
| GET     | /api/tasks?status=EN_COURS   | Filtrer par statut                    |
| GET     | /api/tasks/{id}               | Récupérer une tâche                   |
| PUT     | /api/tasks/{id}               | Mettre à jour une tâche               |
| DELETE  | /api/tasks/{id}               | Supprimer une tâche                   |

Statuts possibles : `A_FAIRE`, `EN_COURS`, `TERMINE`.

## Exemple de requête

```bash
curl -X POST http://localhost:8081/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Préparer la démo","description":"Slides + script","status":"A_FAIRE"}'
```
