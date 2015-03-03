## Synopsis
A quick example of using Apache Camel to ingest csv and YAML files to create nodes in a Neo4J database.
This example assumes a client requirement of simply importing the data as no relationships were formally introduced as a Use Case.

## Code Example
This is a [Apache Maven](http://maven.apache.org/) based project. Maven must be installed on the local machine. Additionally, a Neo4J database is assumed to be running locally. You can change the location of the Neo4J by changing the neo4j.server.root.uri.

## Installation
Executing this following at the command line from the root directory of this project with result in files being processed and sent to the Neo4J instance.

mvn clean install

## Feature requests
This solution, while robust, will require further enhancements to meet scalability requirements. Additional fleshing out of the required Use Case / Queries / Relationships will help further the development of the graph model. An attempt at data model including these elements based entirely on an educated guess is located here:
 [Gist Data Model]
