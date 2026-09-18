# API de Gestion d'un Blog (Articles & Commentaires)

API REST back-end pure (Spring Boot) permettant de publier des articles et d'y ajouter des commentaires.

## Stack technique
- Java 21, Spring Boot 3.3.4
- Spring Data JPA + PostgreSQL
- Lombok
- Swagger / OpenAPI (springdoc)

## Architecture (MVC)
```
controller/ -> ArticleController (articles + commentaires)
service/    -> ArticleService / Impl
repository/ -> ArticleRepository, CommentRepository
entity/     -> Article (1) --- (*) Comment
dto/        -> DTOs de requête/réponse (jamais d'entité exposée directement)
exception/  -> Gestion centralisée des erreurs
```

Relation : un `Article` possède une liste de `Comment` (cascade `ALL`, `orphanRemoval`), un commentaire appartient à un seul article.

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
L'API démarre sur `http://localhost:8084`.

### 3. Swagger UI
`http://localhost:8084/swagger-ui.html`

## Endpoints

| Méthode | URL                              | Description                          |
|---------|-----------------------------------|----------------------------------------|
| POST    | /api/articles                     | Créer un article                       |
| GET     | /api/articles                     | Lister tous les articles               |
| GET     | /api/articles/{id}                 | Récupérer un article (avec commentaires)|
| PUT     | /api/articles/{id}                 | Mettre à jour un article               |
| DELETE  | /api/articles/{id}                 | Supprimer un article                   |
| POST    | /api/articles/{id}/comments        | Ajouter un commentaire à un article    |

## Exemples

```bash
curl -X POST http://localhost:8084/api/articles \
  -H "Content-Type: application/json" \
  -d '{"title":"Premier article","content":"Contenu de test"}'

curl -X POST http://localhost:8084/api/articles/1/comments \
  -H "Content-Type: application/json" \
  -d '{"author":"Alice","content":"Très bon article !"}'
```

