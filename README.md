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
This project is about a software solution for players and administrators of the game Human vs. Zombies (HvZ). We have designed and implemened a software solution for managing the state and communication of one or more concurrent games of HvZ. The main components of the system are as follows:
- A static, single-page, front-end using react. This documentation can be found here: [Human vs. Zombies Front End](https://github.com/JakobDenGode/hvz-experis-fe)
- RESTful  API  service,  through  which  the  front  end  may  interact  with  the database. 
- PostGres database. See ER-diagram for details. 

This repository contains our RESTful API service, through which the front end may interact with the database. The deployed API can be found here [Human vs. Zombies API](https://hvz-api-noroff.herokuapp.com/)

The deployed front-end application can be found here: [Human vs. Zombies App](https://hvz-fe-noroff.herokuapp.com/)

### API Documentation
A full description of the API Documentation can be found on Swagger here: [Human vs. Zombies API Documentation](https://hvz-api-noroff.herokuapp.com/swagger-ui/index.html#/)  

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
