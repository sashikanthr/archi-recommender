This repository contains a list of plugins to implement a recommender framework for [Archi](https://github.com/archimatetool/archi). A high level architecture of Archi and our plugin is shown in the picture below.

<img src="/images/Plugin-Architecture.svg" alt="Plugin Architecture" style="width:100px;"/>

`com.archimatetool.recommender.model` - Model plugin that contains only abstract classes / interfaces

`com.archimatetool.recommender.ui` - UI plugin that contains abstract class for TreeView and Preferences

`com.archimatetool.recommender.connector` - Plugin that implements a HTTP connector to connect to external systems.

`com.archimatetool.recommender.contribution` - A Component Recommender plugin to demonstrate the framework.

The current setup works in an Archi development environment. The information to set a development environment can be found [here](https://github.com/archimatetool/archi/wiki/Developer-Documentation)

Once the development setup is complete, the plugins from this repository must be imported into Eclipse.

The `com.archimatetool.recommender.feature` contains the plugins and this feature is included in the Archi `com.archimatetool.editor.feature`. 

A feature can be included in another feature from Eclipse by updating the Included Features section of `feature.xml` or adding this element directly in the `feature.xml` file.

```
<includes id="com.archimatetool.recommender.feature" version="0.0.0"/>
```

The Component Recommender for Archi (`com.archimatetool.recommender.contribution`) can process any `json` response from an external system that adheres to the [schema](/schema/schema.json). The current implementation uses a hardcoded ip address and port in `ConnectorService.java`. This will be replaced with an `.ini` file or as a Preference in the future.

To demonstrate connection to an external system, a [word2vec recommender](https://github.com/sashikanthr/archi-component-recommender) is implemented in PyFlask. 

A list of sample Archi models can be found [here](https://github.com/borkdominik/CM2KG/tree/main/Experiments/EMF/Archi/ManyModels/repo-github-archimate/models).





