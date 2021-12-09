# share-your-recipes

## Technologies Used

- Backend: Java Spring with JPA ORM to PostgreSQL
- Front-end: JQuery, HTML, CSS, Bootstrap

## Main features

- Role-based Authentication & Authorization
- Restful API for CRUD Recipes, Comments, Votes, Admin Management
- Account verification & password reset using mail service
- Basic sign up or using `Google OAuth`
- Redis-store session

## How to run

1. Specific environment profile `Dev` or `Prod` at `application.properties`
2. Run this command
   ```
   mvn spring-bot::run
   ```

## Deployment

[here](https://cookwithamee.herokuapp.com/)
