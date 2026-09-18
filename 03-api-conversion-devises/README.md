# API de Conversion de Devises

API REST back-end pure (Spring Boot) qui convertit un montant d'une devise à une autre en utilisant des taux de change récupérés dynamiquement auprès d'une API externe. **Aucune base de données** — conformément aux exigences du projet.

## Stack technique
- Java 21, Spring Boot 3.3.4
- Spring WebClient (WebFlux) pour l'appel à l'API externe
- Swagger / OpenAPI (springdoc)

## Architecture (MVC)
```
controller/ -> CurrencyConversionController
service/    -> CurrencyConversionService / Impl (logique métier + appel externe)
client/     -> ExchangeRateApiResponse (mapping de la réponse de l'API externe)
config/     -> WebClientConfig (bean WebClient)
dto/        -> ConversionRequestDTO, ConversionResponseDTO, ApiErrorResponse
exception/  -> InvalidCurrencyException, ExternalApiException, gestion centralisée
```

## API externe utilisée
[open.er-api.com](https://www.exchangerate-api.com/docs/free) — gratuite, sans clé API requise, retourne les taux de change au format :
```json
{ "result": "success", "base_code": "USD", "rates": { "EUR": 0.92, ... } }
```
L'URL de base est configurable dans `application.properties` (`exchange.api.base-url`), ce qui permet de basculer facilement vers un autre fournisseur (ex : exchangerate-api.com avec clé).

## Prérequis
- JDK 21, Maven 3.9+
- Accès internet (pour joindre l'API externe de taux de change)

## Installation et lancement
```bash
mvn spring-boot:run
```
L'API démarre sur `http://localhost:8083`.

## Swagger UI
`http://localhost:8083/swagger-ui.html`

## Endpoints

| Méthode | URL                                                  | Description                  |
|---------|-------------------------------------------------------|-------------------------------|
| POST    | /api/currency/convert  (body JSON)                    | Convertir un montant          |
| GET     | /api/currency/convert?from=USD&to=EUR&amount=100      | Convertir via query params    |

## Exemples

```bash
# Via query params
curl "http://localhost:8083/api/currency/convert?from=USD&to=EUR&amount=100"

# Via body JSON
curl -X POST http://localhost:8083/api/currency/convert \
  -H "Content-Type: application/json" \
  -d '{"from":"USD","to":"XAF","amount":50}'
```

## Gestion des erreurs
- Devise source ou cible invalide/inconnue → `400 Bad Request`
- Problème de connexion / réponse invalide de l'API externe → `502 Bad Gateway`
- Corps de requête invalide (montant manquant, négatif...) → `400 Bad Request` avec détails de validation


