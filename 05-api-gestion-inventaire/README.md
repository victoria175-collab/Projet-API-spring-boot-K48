# API de Gestion d'un Inventaire de Produits

API REST back-end pure (Spring Boot) pour gérer un inventaire de produits avec suivi des stocks et alerte de stock bas.

## Stack technique
- Java 21, Spring Boot 3.3.4
- Spring Data JPA + PostgreSQL
- Lombok
- Swagger / OpenAPI (springdoc)

## Architecture (MVC)
```
controller/ -> ProductController
service/    -> ProductService / Impl
repository/ -> ProductRepository (findByQuantityLessThan)
entity/     -> Product (nom, prix, quantité, updatedAt)
dto/        -> ProductRequestDTO, ProductResponseDTO (avec flag lowStock), ApiErrorResponse
exception/  -> Gestion centralisée des erreurs
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
L'API démarre sur `http://localhost:8085`.

### 3. Swagger UI
`http://localhost:8085/swagger-ui.html`

## Endpoints

| Méthode | URL                                   | Description                                   |
|---------|-----------------------------------------|-------------------------------------------------|
| POST    | /api/products                           | Créer un produit                                |
| GET     | /api/products                           | Lister tous les produits                        |
| GET     | /api/products/{id}                       | Récupérer un produit                            |
| PUT     | /api/products/{id}                       | Mettre à jour un produit (prix, quantité...)    |
| DELETE  | /api/products/{id}                       | Supprimer un produit                            |
| GET     | /api/products/low-stock?threshold=5      | Lister les produits en stock bas (< seuil)      |

Chaque produit renvoyé porte un champ `lowStock` (booléen, vrai si quantité < 5) pour une alerte visible directement dans la réponse standard.

## Exemples

```bash
curl -X POST http://localhost:8085/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Clavier mécanique","price":49.99,"quantity":3}'

# Produits en stock bas (seuil par défaut : 5)
curl http://localhost:8085/api/products/low-stock
```


