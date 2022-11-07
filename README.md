This repository contains a list of plugins to implement a recommender framework for [Archi](https://github.com/archimatetool/archi). A high level architecture of Archi and our plugin is shown in the picture below.

<img src="https://github.com/sashikanthr/archi-recommender/blob/master/images/Plugin-Architecture.svg" alt="Plugin Architecture" style="width:100px;"/>

`com.archimatetool.recommender.model` - A model plugin that contains only abstract classes/interfaces

`com.archimatetool.recommender.ui` - UI plugin that contains an abstract class for TreeView and Preferences

`com.archimatetool.recommender.connector` - Plugin that implements an HTTP connector to connect to external systems.

`com.archimatetool.recommender.contribution` - A Component Recommender plugin to demonstrate the framework.

The current setup works in an Archi development environment. The information to set up a development environment can be found [here](https://github.com/archimatetool/archi/wiki/Developer-Documentation)

### Note: This setup was done from a previous version of Archi. It is recommended to clone the forked repository [here](https://github.com/sashikanthr/archi-recommender)

- Once Eclipse and the environment for Archi were set up for development, the plugins from this repository must be imported into Eclipse. This can be done by cloning the repository to another location, and using 'Add Existing Projects to Workspace'.

- After the project is imported, add below entries in `archi.target` file. These are the dependencies to setup the HTTP connector.

```
      <location includeDependencyDepth="direct" includeDependencyScopes="compile" includeSource="true" missingManifest="generate" type="Maven">
              <dependencies>
                      <dependency>
                              <groupId>javax.ws.rs</groupId>
                              <artifactId>javax.ws.rs-api</artifactId>
                              <version>2.1.1</version>
                              <type>jar</type>
                      </dependency>
              </dependencies>
      </location>
      <location includeDependencyDepth="direct" includeDependencyScopes="compile" includeSource="true" missingManifest="generate" type="Maven">
              <dependencies>
                      <dependency>
                              <groupId>com.google.code.gson</groupId>
                              <artifactId>gson</artifactId>
                              <version>2.8.5</version>
                              <type>jar</type>
                      </dependency>
              </dependencies>
      </location>
```

- Add the below entry in the `feature.xml` file under `com.archimatetool.editor.feature`.

```
<includes id="com.archimatetool.recommender.feature" version="0.0.0"/>
```

The Component Recommender for Archi (`com.archimatetool.recommender.contribution`) can process any `json` response from an external system that adheres to the [schema](/schema/schema.json). The current implementation uses a hardcoded ip address and port in `ConnectorService.java`. This will be replaced with an `.ini` file or as a Preference in the future.

To demonstrate connection to an external system, a [word2vec recommender](https://github.com/sashikanthr/archi-component-recommender) is implemented in PyFlask.

A list of sample Archi models can be found [here](https://github.com/borkdominik/CM2KG/tree/main/Experiments/EMF/Archi/ManyModels/repo-github-archimate/models).

## Running the JUnit Tests

- Add the recommender plugins as dependencies to `com.archimatetool.tests`
- Add the below entry in `AllTests.java` in the appropriate line

```
suite.addTest(getTest("com.archimatetool.recommender.contribution.AllTests"));
```
