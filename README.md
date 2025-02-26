# A Modular Experience and API Framework Prototype

This application is a prototype for the development of a new modular experience and API framework. This application will serve as a new landing page to replace the current ARIES system and provide workers and clients a unified place to create and manage applications, cases, and client information. It will also provide links to other applications such as Unified Search, Autorenew Dashboard, and legacy Worker Portal.

The intent of this prototype is the develop methods for providing new user experiences to existing data sources in a more streamlined and user friendly way.

The application uses Spring Boot JPA to interact with the existing Worker Portal DB using business object entities to represent applications and persons. This application also exposes an API for interacting with the existing Postgres database.

Prototype goals:

- demo [modular experience](https://github.com/akhealth/EIS-Modernization/blob/master/modular-experience.md)
- create doumented API
- test an implementation of the [USWDS framework](https://designsystem.digital.gov/)
- prefer containerized development environment

## Getting Started

This prototype was developed using VS Code and Spring Boot Tools

- [Install Spring Boot Tools](https://spring.io/tools)

Additionally, we recommend Podman or Docker be installed on your system.

- [Install Podman](https://podman.io/getting-started/installation)

### Building the Project using Podman

This project contains Dockerfiles and a `compose.yaml` file to stand up the application, a simplified PostgreSQL db, and a PGAdmin instance.

Copy the example `.env` file and build and run the application using the compose command. 

```
> cp .env.example .env
> podman compose build
> podman compose up
```

After running compose to build and run the application, you should be able to access services on the following URLS with the default configuration:

| Service             | Address                       |
| :------------------ | :---------------------------- |
| ModExp Login Page   | http://localhost:8081/        |
| API                 | http://localhost:8081/api     |
| API Documentation   | http://localhost:8081/swagger |
| PGAdmin Login       | http://localhost:9876

Sample data and users are available:

- Search for Client: 1
- Search for Application: T00000001
- PGAdmin User: admin@example.gov 
- PGAdmin Password: adminpass
- DB User: admin
- DB Pass: adminpass

When finished, the pod can be taken down and containers removed.

```
> podman compose down
```

## Contributing

Contributions are welcome, and appreciated! See [CONTRIBUTING.md](CONTRIBUTING.md) for more info about how to contribute.

## License

As a work of the State of Alaska, this project is in the public domain within the United States.

Additionally, we waive copyright and related rights in the work worldwide through the CC0 1.0 Universal public domain dedication.

See [LICENSE.md](LICENSE.md) for the complete license text.
