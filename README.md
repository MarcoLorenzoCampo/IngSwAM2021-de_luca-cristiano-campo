

## Software Engineering Final Examination (2020/2021)

### Project Description

The objective of this project is to provide a playable and 
distributed Java implementation
of the game [Masters Of Renaissance](https://craniointernational.com/products/masters-of-renaissance/), following the Model-View-Controller
pattern.

### UML

UML files have been made to document the progression of the project.  
  
● [Initial UML](): Basic *model* implementation made during the first _design_ phase;  

● [Final UML](): Complete UML to capture the whole structure of the implementation;

###Javadocs

Javadocs have been written for the most important methods/attributes
of the classes. 
Read them [here]().

### Libraries and Plugins

```
● Maven: Main project manager tool.

● Junit5: Main testing framework.

● Mockito: Used to mock expensive objects in tests.
```

### Functionalities

Here's a list of all the features we've implemented:

```
● Online multiplayer game;

● Online single player game;

● Command Line Interface;

● Graphical User Interface;

● Socket connection;

● 2 Advanced Functionalities;

        ● Offline single player game;
        
        ● Player reconnection;
```

## Executing the Jar file



The [executable Jar file](shade/AM38.jar) has been compiled 
using the _Shade_ Maven plugin.

```
mvn clean package
```

It can be run from its folder as both Client and Server.

### Launching the Server

To launch the server, the Server main class needs to be specified.

```bash
java -cp AM38.jar it.polimi.ingsw.network.server.Server
```

The server runs on a default port specified in the [server_config.json](src/main/resources/server_config.json), 
a different one can be specified by editing the command line
arguments as shown below.

```bash
java -cp AM38.jar it.polimi.ingsw.network.server.Server -port 12345
```

_Waits for connections on port 12345_

Keep in mind port values under _1024_ won't be accepted.

### Launching the Client

The Client can be launched simply by executing the jar.

```bash
java -jar AM38.jar
```

The default run configuration is a _Command Line Interface_ (CLI) but
it can be run with a _Graphical User Interface_ (GUI) by adding _-gui_
to the command line parameters.

```bash
java -jar AM38.jar -gui
```

### Playing the Game

Once the Client is started, you will be prompted to chose an 
online or offline game.

#### Offline game:

Once selected, you will be asked to chose a nickname and a 
single player game will start.  
Everything is run locally, no internet connection is required.

#### Online game:
Once selected, you will be asked to specify a valid 
IP Address and Server Port to connect to (default values will be 
shown), if the connection is successful you will be asked to input
a valid nickname.  
You will be then asked to chose the number of 
players, every value between 1 and 4 is accepted.
  
Once the number is reached, the game will start.

### Authors

● [Campo Marco Lorenzo](https://github.com/MarcoLorenzoCampo), \
● [De Luca Alessandro](https://github.com/AlessandroDL), \
● [Cristiano Mario](https://github.com/Mario-CR).
