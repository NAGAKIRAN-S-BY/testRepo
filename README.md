![master](https://github.com/JDA-Product-Development/exec-ecom-exec-ud-daas-etl-service/workflows/master/badge.svg) ![Performance Tests](https://github.com/JDA-Product-Development/exec-ecom-exec-ud-daas-etl-service/workflows/Performance%20Tests%20(Staging)/badge.svg)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/18cb24631a414edc925a2c10c33c94e8)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JDA-Product-Development/exec-ecom-exec-ud-daas-etl-service&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/18cb24631a414edc925a2c10c33c94e8)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JDA-Product-Development/exec-ecom-exec-ud-daas-etl-service&amp;utm_campaign=Badge_Coverage)

# exec-ecom-exec-ud-daas-etl-service

## Table of Contents
- [Artifactory Requirements](#artifactory)
- [Building](#building)
- [Run Check](#runcheck)
- [Running Service](#running)
- [Calling an API](#apis)

## Starting a new service?

See [CHECKLIST](CHECKLIST.md) for steps to create a new service.

<a name="artifactory"></a>
## Artifactory Requirements

JFrog artifactory server requires authentication to consume artifacts.  Therefore, developers must configure local credentials to artifactory for gradle to use. See the
 [Onboarding Doc](https://jda365.sharepoint.com/sites/a_ExecutionECommerce919/SitePages/E-Commerce-Internal-Onboarding.aspx#artifactorysetup) for details. 

<a name="ide"></a>
## IDE Requirements

This project uses [Lombok](https://projectlombok.org/) to automatically generate boilerplate such as POJO getters + setters. Lombok uses Annotation Processing, which
requires some [IntelliJ configuration](https://www.baeldung.com/lombok-ide).

<a name="building"></a>
## Building

This project uses gradle wrapper to prevent users from having to install gradle at a global level.  Make sure you use `gradlew` for execution calls _not_ `gradle`.  This also ensures that the same versions of the tooling are being used across deployments.

To build the project execute the following from the project root:

```
./gradlew build
```

<a name="runcheck"></a>
## Run check

Simply run:
```
./gradlew clean check.
```

The integration tests relies on docker being installed in your machine to startup a mssql container and runs the test in `dev-sql` profile.
If docker is not installed, the integration tests can be run against an H2 database by changing `integrationTestProfile` to `dev` in [gradle.properties](./gradle.properties) file or by running:
```
./gradlew clean check -PintegrationTestProfile=dev
```

<a name="running"></a>
## Running the service

This service depends on connecting to Kafka and by default connects on `localhost:9092`.  
If you don't already have kafka running, you can easily start it through the docker-compose config
included in the project

```
docker-compose up -d kafka
``` 

The distribution service is a spring boot app and can be started using gradlew.

```
./gradlew bootrun
```

The default app location is `localhost:8080`.

<a name="apis"></a>
## Calling an API.

This project defines APIs using the [OpenAPI Specification](http://spec.openapis.org/oas/v3.0.3). Java code and HTML documentation is [generated](https://openapi-generator.tech/) from the specification found [here](./api/api.yaml).

After successfully building the project, the generated documentation will be in `./api/docs`.

To call the `createFoo` API use the http client of your choice to send the following request:

```
POST http://localhost:8080/v1/foos
Content-Type: application/json
{
  "bar": "some value"
}
```
To see the available `foo`s send this request:
```
GET http://localhost:8080/v1/foos
```
