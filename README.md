# Human vs. Zombies Backend - Experis/Noroff 2022

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

## Table of Contents

- [About](#about)
- [Install](#install)
- [Usage](#usage)
- [Integration](#Integration)
- [Built with](#built-with)
- [Contributing](#contributing)
- [License](#license)

## About
This project is about building a Pokémon Trainer web app using the Angular Framework. The application communicates with the server through the REST API found here: https://sm-lost-in-translation-api.herokuapp.com/ to store trainers and pokemons collected. The available pokemons and information about pokemons is found from the REST API: https://pokeapi.co/

### Appendix A: Requirements for the Pokemon Trainer Application
The application allows a user to collect Pokémon received from the PokeAPI. Users must enter username before being able to collect any Pokémon. Users are also able to view the Pokémon that have been collected. The application contains three main screens: 
#### Landing page
The first thing the user can see is the “Login page” where the user must enter their “Trainer” name. There is a button that saves the Trainer name to the Trainer API. The app then redirects to the main page, the Pokémon Catalogue page.
#### Pokémon Catalogue Page
A user can only view this page if they are currently logged into the app. The user is  redirected back to the login page if now active login session exists in the browser storage.
The Catalogue page lists the Pokémon name and avatar* of the original 151 pokemons. There is a button on each Pokémon that, when clicked, adds the Pokémon to the trainer’s collection. This also updates the Trainer API with the collected Pokémon.
#### Profile page
A user may only view this page if there is a Trainer name that exists. It redirects the user back to the Landing page if they do not have a Trainer name stored in localStorage. 
The Trainer page lists the Pokémons that the trainer has collected. For each collected Pokémon,the Pokémon name and image are displayed. A user is also able to remove a Pokémon from their collection from the Trainer page. The user can also log out from this page. 
### API Documentation
A full description of the API Documentation can be found on Swagger by this link: [Human vs. Zombies API Documentation] (https://hvz-api-noroff.herokuapp.com/swagger-ui/index.html#/)  

## Install
- Open PgAdmin
- Create database "HvZ" locally
- Clone repo and open in intellj
- Initialize SDK
- Build project with Gradle

## Usage
- Run project. The application will run standard on localhost port 8080. 

## Integration
The application is hosted on heroku: please follow this link. 
[Human vs. Zombies API](https://hvz-api-noroff.herokuapp.com/)

Also, you can visit the deployed front end application: 
[Human vs. Zombies App](https://hvz-fe-noroff.herokuapp.com/)

## Built with

- IDE: Intellij Ultimate
- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Auth0
- PgAdmin 
- PostgreSql
- Gradle
- Swagger
- GitHub
- Heroku

## Contributing
- [Lars-Inge Gammelsæter Jonsen](https://github.com/Kaladinge)
- [Sondre Mæhre](https://github.com/Sondrema)
- [Truls Ombye Hafnor](https://github.com/TrulsHafnor)
- [Jakob Slyngstadli](https://github.com/JakobDenGode)
- [Carl Markus Malde](https://github.com/CarlMarkus)

PRs accepted.

## License

UNLICENSED
