# exec-ecom-exec-ud-daas-etl-service Performance Testing

The files in this directory serve as a starting point for implementing performance testing.

## Requirements
Running the performance test requires [Taurus](https://gettaurus.org/install/Installation/) and docker compose to be installed.

### Kafka plugin
When running the tests via gradle or ```bzt```, JMeter and the required JMeter Kafka plugin will be installed automatically.
However, if you are updating a test and running the jmx file manually in JMeter, you must install the 
Kafka plugin yourself. To do this, download the jar from ```https://ecomblobstorage.blob.core.windows.net/jmeter-plugins/pepper-box-1.0.jar``` 
and drop this jar into your local ```<JMETER_DIR>/lib/ext``` directory.

## Setup Checklist
##### Gradle Properties Update
Update the property values in [gradle.properties](performance/gradle.properties) for the service. 
##### Docker Compose Setup
Make any needed changes to the [docker-compose.yaml](docker-compose.yaml) for the service.
##### Database Initialization Setup
Update the bztInit task in the performance [build.gradle](performance/build.gradle) to properly initialize the database for the performance tests.
##### JMeter Script Integration
After jmeter scripts and supporting files have been created, place them into the [files](performance/files) directory and create a bzt scenario file
for those tests.  See [test-foo.yaml](performance/test-foo.yaml) as an example.
##### Github Workflow Setup
Update the `baseUrl` property in the `staging.yaml` configuration file and set up the [post deployment notify trigger](https://github.com/JDA-Product-Development/exec-ecom-azure-deployment#post-deploy-notify) 
for your service.  Do not forget to initialize the staging database before enabling the staging performance tests.

## Running
For local development, the performance tests are setup to run against the service deployed to a docker compose 
stack.  There are gradle tasks available to bring up the docker compose stack, initialize the database, run the 
tests and shutdown the docker compose stack.

To start the docker compose stack and initialize the database, in the root directory of the project, 
run ```gradlew bztInit```.

To run all performance tests, run ```gradlew bztAll``` See the table below for additional gradle tasks to run individual tests.

To stop the docker compose stack and remove all the containers, run ```gradlew composeDown```.


| Command                    | Description                                                                     |
|----------------------------|---------------------------------------------------------------------------------|
| gradlew composeUp          | Starts the docker compose stack.                                                |
| gradlew bztInit            | Starts the docker compose stack and initializes the load testing DB.            |
| gradlew bztDBUpload        | Saves docker compose database to file and uploads to Azure storage account.     |
| gradlew bztDBRefresh       | Restores the database for staging from the backup on the Azure storage account. |
| gradlew bztAll             | Runs all available performance tests.                                           |
| gradlew bztTestFoo         | Runs the test-foo.yaml performance test.                                   |
| gradlew composeDown        | Stops the docker compose stack and removes all containers                       |

In addition, the following gradle properties are available to customize the test execution:

| Property    | Description                                                                                                                  |
| ----------- | ---------------------------------------------------------------------------------------------------------------------------- |
| bztTenant   | Overrides the tenant used for the database and the performance tests.  Default is perftest.                                  |
| bztEnvFile  | Overrides the environment file that customizes the execution for different environments. Default is dev.yaml.                |

## Database Refresh
For a typical performance test run in the staging environment, the database is not rebuilt between runs.  If there 
is a need to rebuild the database because the schema has changed in a non-upgradable way or to add new test data,
there are 2 gradle tasks that can be used to refresh the database for the staging environment.

To refresh the database for staging, first login to the az cli:
```az login```

Then execute the following gradle tasks:
```gradlew composeDown bztInit bztDBUpload```
This will create a new database, export it to a bacpac file and upload it to the Azure storage account.

Next, to refresh the database in the staging environment, run:
```gradlew bztDBRefresh```
