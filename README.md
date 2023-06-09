# Delta-DKT-Documentation
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=coverage)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)

# DKT - Group project

This repository features the game DKT, for which an android application has been created in the group phase of the Software Engineering II course. While each group pick a desired game, some requirements had to be met, which were as follows:

- multi-player
- server has to run on one of the clients
- cheat functionality
- use of sensors
- must a bit complex, thus not games like *Uno*, *Schlangen und Leitern* or *Mensch Ärgere dich nicht*

Based on these prerequisites, all members agreed on DKT.

## Proceedings

As part of this group phase a game had to be developed using [SCRUM](https://www.scrum.org/). This means that the development has been split into 3 segments or so called sprints. The goal was to assign tasks to each member, for each sprint, that had to be finished at the of the sprint. Applying this strategy to all 3 sprints should result in a finished game.

### Sprint Tasks

In order to reduce merge-conflicts at the end of a sprint, we as a group tried to define tasks that are independent from each other. As a result we focussed on the basics of the game first, such as the logic-class-structure, server, client and the UI at first.

In the second sprint we defined specific features to each member, such as charging rent, buying a property, moving on the map, join a game and enter the game from the lobby. Note that, each feature involved all three layers of the app. That means, as part of a feature, one had to work on the server and client side as well as the UI.

Finally, in the third sprint our focus was set on the implementation of missing features, optimization and bug fixes. This involved features like, action cards, leaving the game, cheating functionalities, round/time limit, multi-language support.

## Project Metrics
This chapter shows the project metrics, which are generated by [SonarCloud](https://sonarcloud.io/). They are updatically updated by sonarcloud when the master branch is updated.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=bugs)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=coverage)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=uni-aau_delta-dkt&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=uni-aau_delta-dkt)

# Features of the game

When the game is started a player nickname must be entered to continue with the game. Once a valid nickname is entered and the button is pressed the MainMenu will be shown. 
<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/MainActivity.png?raw=true" alt="Main Activity"/>
</p>

<hr>

In this menu the player can choose whether he wants to host a new game or join an existing game in his current network.

<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/MainMenu.png?raw=true" alt="Host Game - Menu"/>
</p>

### Joining existing game

For this to work a game has to be hosted by another player that is on the same network as the player that wants to join a game. If those requirements are met, then all available game servers will be shown in a list. Then one of the servers can be selected by clicking on it. Once a selection has been made the player is able to press the Join button at the bottom right corner. Pressing the Join button will connect to the server as a client and show a new activity / screen, called the LobbyView.

<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/FindHostActivity.png?raw=true" alt="Host Game - Menu"/>
</p>

### Host a new game

In order to host a new game, the ‘Host Game’ button is to be pressed. After the button has been pressed a new dialog will pop up, that will ask the user to configure the game server. The options include the server name, max. amount of players allowed, whether the game is to be timed either by rounds or time spent and the amount of rounds or minutes until the game is finished by the system. Once the parameters have been entered the server can be started and the screen could like this:

<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/ServerConfig.png?raw=true" alt="Host Game - Config Menu"/>
</p>

### Game Lobby

Hosting a new game server will update the players view  so that he is the Lobby. Whenever a new player joins a server the view of each player will be updated so that all the players of a game are inside the Lobby. Each player, even the host of the game, is able to leave the lobby at all times. In case a players leaves the view of the others will be simply updated, so that the player, who left, is no longer displayed. However, if the hosts decides to leave and therefore decides to abandon the lobby, will close the lobby, thus move all the players back to the MainMenu of the game.

The host has the option to start the game at any time by clicking on the Start button in the bottom right-hand corner while waiting until the number of players is reached. When this button is clicked, the view of each player is updated so that each player is in the so-called GameView.
<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/LobbyView.png?raw=true" alt="LobbyView"/>
</p>

## GameView

The game is played within the GameView. As part of the game, players can roll the dice to make a move, buy properties and pay rent to property owners. Note that only the host is able to roll the dice at the beginning of the game. When he finishes his turn, the turn of the next player begins. This cycle continues until either there is only one player left or until the round or time limit is reached.

<p align="center">
  <img style="width: 75%; border-radius: 24px;" src="/assets/GameView.png?raw=true" alt="GameView"/>
</p>

### Player Timeout - Kicked from Game

It is also important to know that if a player does not complete his turn within a given time of currently 20 seconds, he will be informed of this and removed from the game if he continues to wait. In this case, the next player takes his turn.

### Cheating

During the game, each player has the opportunity to gain an advantage by cheating. This can be done by a player covering his light senor of the device and then rolling the dice. By doing so, this player activates cheating for his turn and receives an enhanced dice that rolls a number between 7 and 9.

<aside>
❗ However, it should be noted that each player has the option to report other players at any time if he believes that a player has cheated.

</aside>

Players can do this by pressing the "Report Cheater" button, which opens a dialogue.

<p align="center">
  <img style="width: 45%; border-radius: 24px;" src="/assets/ReportCheater.png?raw=true" alt="Report Cheat Menu"/>
</p>

In this dialogue, a player may select another player from the list to report. Please note that you will lose $200 if you falsely report a player, whereas a player who is caught cheating will lose $500.

---
Alternatively, this menu can also be closed again without reporting a player.

### Losing

Should a player not be able to pay a rent or the fine for cheating or something like that, because he does not have enough money at hand, he loses and can no longer participate in the game. When a player loses, all the properties are returned to the bank and can be bought by other players.


---
At the end of a game, each player sees a ranking list of the players, sorted by their wealth.

<p align="center">
  <img style="width: 75%; border-radius: 24px;" src="/assets/WinnersRanking.png?raw=true" alt="Host Game - Menu"/>
</p>