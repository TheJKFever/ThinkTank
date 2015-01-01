# USC CSCI 201 Final Project (Codename: TITANIUM TANK)

## Overview:
A networked, multiplayer tank game in which two teams of players compete against each other with the goal of destroying the opposing team’s base. To achieve this objective, the teams will battle each other and compete for resources, which they can use to upgrade their weapons, build defensive turrets, equip shields, and repair their own base. The game itself is played for a total of 10 minutes or until one base is destroyed, which ever comes first. If no base is completely destroyed within 10 minutes, gameplay is stopped and the winning team is determined by comparing the health of each team’s base. The team with the healthiest base wins. 

## The Map:
The game is set up on a rectangular map. Opposing bases are located on opposite ends of the map. The map is somewhat maze-like, with barriers scattered throughout it, and resources randomly spawning.

## The GUI:
When a player joins the game, they will be able to see the whole map including all tanks and bases. Also on screen will be: health meters for each team’s base, a health meter for the player’s tank, an ammunition indicator, and a chat module. Tanks may move at up-down and left-right movements. They will move at a set speed and will be able to simultaneously move and shoot.

## Defending the Base:
As mentioned above, each team will have a base, which they must protect, as the health of each team’s base determines the winner of the game. Bases are damaged by enemy tank fire. Base health is also reduced each time a tank from the corresponding team is destroyed and re-spawned, which is meant to reflect the use of a team’s resources in order to create a new tank. Base repair kits can be purchased with acquired resources to restore a small amount of health to a base, although considerable effort is required to do so. 

## Gathering Resources:
During the game, in addition to seeking out enemy property to destroy, tanks will be able to collect resources. These resources can be acquired from a pool in the middle of the map. To acquire resources, a player must drive their tank into the resource pool and hold the 'm' for some duration. While holding 'm', resources are extracted from the pool at a finite rate, so the longer the player stays and collects resources, the more resources she will obtain. 

## Using Resources to Purchase Upgrades:
Resources can be use to purchase upgrades such as more powerful ammunition, shields, defensive turrets, and base repair kits.  Upgraded weapons can be purchased and used on the spot once a tank has enough resources. These upgraded weapons include high-powered bullets, double-damage bullets, etc. These upgrades are consumable and finite (e.g. 100 double-strength bullets). Similarly, shields can be purchased and applied immediately to the player,

Turrets:
Turrets are AI-controlled and automatically attack any opposing tank within a small radius. Turrets have finite health, so they may be destroyed by enemy tanks once they are built. In order to build a turret, a tank must first gather enough resources to purchase the turret, and then it must travel to the location on the map where they would like to build the turret. Once at the desired location, the player can press the “B” key to build the turret at that site. This location must be within a team’s allotted one third of the map. That is, only defensive turrets are allowed. 

## Repair Kits:
To use a repair kit to replenish base health, a tank must first acquire enough resources to purchase the kit, and then it must return to its home base to actually purchase and apply the repair kit. 

## Chat:
Throughout the game, communication between tanks is made possible through a chatting function. Global chat and team chat will both be enabled. Team chat will be particularly effective with large teams where coordinating strategy is crucial.

## Statistics:
The game will keep track of a number of gameplay statistics for each player, including total number of kills, number of bases destroyed, number of games won, number of games played, average number of kills per life, and others.