# Software Security Demo App

## Web Application section

### Requirements

* Java (1.8 or higher)
* Maven (2 or higher)

### Install

Install Java packages and database using:

```shell
mvn install
```

### Run

Launch the server using:

```shell
mvn jetty:run
```

Go to http://localhost:8080 and have fun.

## Single-Page Application (SPA)

### Requirements

* NodeJS v13.5.0 or higher. It's reccommended to use
[NVM](https://github.com/nvm-sh/nvm) for environment isolation.

### Install

Go to the angular-app and install packages:

```shell
cd angular-app/
npm install
```

### Run

Launch the web application (se the previous section), and then launch the SPA:

```shell
ng serve
```

Go to http://localhost:4200 and have fun.
