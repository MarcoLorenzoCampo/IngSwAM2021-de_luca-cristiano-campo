

## Software Engineering Final Examination (2020/2021)

### Project Description

The object of this project is to provide a playable Java implementation
of the game [Masters Of Renaissance](https://craniointernational.com/products/masters-of-renaissance/), following the Model-View-Controller
pattern.

### Libraries and Plugins

We used Maven as the main project manager tool, Junit5 and Mockito as 
testing frameworks.
```
- Maven 
- Junit5
- Mockito
```

### Functionalities

Here's a list of all the features we've implemented:

```
- Online multiplayer game.
- Online single player game.
- Command Line Interface.
- Graphical User Interface.
- Socket connection.
```

Advanced Functionalities:

```
- Offline single player game.
- Player reconnection.
```

## Executing the Jar file



The [executable Jar file](shade/AM38.jar) has been compiled using the _Shade_ Maven plugin.
It can be run from its folder as both Client and Server as shown below.

### Launching the Server

To launch the server, the Server main class needs to be specified.

```bash
java -cp AM38.jar it.polimi.ingsw.network.server.Server
```

The server runs on a default port specified in the [server_config.json](src/main/resources/server_config.json),
a different server port can be specified by editing the command line
arguments as shown below.

```bash
java -cp AM38.jar it.polimi.ingsw.network.server.Server -port 12345
```

_Waits for connections on port 12345_

Keep in mind port values under 1024 won't be accepted.

### Launching the Client

The client can be launched by running the Jar file as it is.

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
shown), if the connection succeeded you will be asked to input
a valid nickname.

### Authors

[Marco Lorenzo Campo](https://github.com/MarcoLorenzoCampo), \
[Alessandro De Luca](https://github.com/AlessandroDL), \
[Mario Cristiano](https://github.com/Mario-CR)
