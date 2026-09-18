# PROJET_API — 5 API Back-End Java Spring Boot

Ce dépôt regroupe 5 API REST indépendantes, développées en Java Spring Boot, conformément au document *Résumé des Exigences Globales* :

| # | Projet                                   | Dossier                        | Port  | Base de données |
|---|-------------------------------------------|---------------------------------|-------|------------------|
| 1 | API de Gestion de Tâches (To-Do List)      | `01-api-gestion-taches`         | 8081  | PostgreSQL       |
| 2 | API de Gestion des Utilisateurs (JWT)      | `02-api-gestion-utilisateurs`   | 8082  | PostgreSQL       |
| 3 | API de Conversion de Devises               | `03-api-conversion-devises`     | 8083  | Aucune (API externe) |
| 4 | API de Gestion d'un Blog                   | `04-api-gestion-blog`           | 8084  | PostgreSQL       |
| 5 | API de Gestion d'un Inventaire de Produits | `05-api-gestion-inventaire`     | 8085  | PostgreSQL       |

Chaque projet est **totalement indépendant** : son propre `pom.xml`, sa propre base de données, son propre port, son propre `README.md` détaillé et son propre `docker-compose.yml` (sauf le projet 3, qui n'a pas besoin de base de données).

## Exigences globales respectées

- ✅ Pur back-end (aucune interface utilisateur, uniquement des API REST)
- ✅ Documentation Swagger / OpenAPI, testable via Swagger UI, sur chaque projet
- ✅ README par projet expliquant l'installation et les tests
- ✅ Architecture MVC (Model / View / Controller) — ici déclinée en couches `controller / service / repository / entity / dto`
- ✅ PostgreSQL pour les 4 projets qui nécessitent une persistance ; l'API de conversion de devises n'utilise aucune base, conformément au résumé des exigences

## Stack technique commune

- **Java 21** / **Spring Boot 3.3.4** / **Maven**
- **Spring Data JPA** + **PostgreSQL** (projets 1, 2, 4, 5)
- **Spring Security + JWT** (projet 2)
- **Spring WebClient** pour l'appel à une API externe (projet 3)
- **Lombok** pour réduire le boilerplate (getters/setters/builders)
- **Bean Validation (Jakarta)** sur tous les DTOs d'entrée
- **springdoc-openapi** pour générer Swagger UI automatiquement
- Gestion d'erreurs centralisée par projet via `@RestControllerAdvice`
- **Docker Compose** pour démarrer PostgreSQL en une commande

## Démarrage rapide d'un projet

```bash
cd 0X-nom-du-projet
docker-compose up -d      # si le projet utilise une base de données
mvn spring-boot:run
```

Puis ouvrir `http://localhost:PORT/swagger-ui.html` pour tester l'API interactivement.

> Voir le `README.md` de chaque sous-dossier pour les détails complets (endpoints, exemples curl, particularités).


