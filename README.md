# Kees' Beer Smash

Beer Smash is an interactive physics-based game where players launch a ball to hit beer mug targets while avoiding bombs. 
The game features realistic ball physics, trajectory prediction, and difficulty levels.

## Overview

Target Shooting - Hit beer cups to score points.
Bombs - Avoid bombs that deduct your score!
Difficulty - Choose between easy, medium and hard modes.
Game State - Save your progress and resume whenever you wish to.
Score Tracking - Displays your score in real-time with feedback.
Timer - You must race against the clock to get the highest score possible!
Trajectory - Predicts your ball's path before launch.

## Game Controls

UP Arrow - Pull ball back, increase power.
DOWN Arrow - Release ball forward, decrease power.
SPACE - Launch ball.
Ctrl + S - Save game state.
Ctrl + L - Load game state.

Scoring System:
Hit a beer mug - +10 points
Hit a bomb - -5 points

Levels:
Easy (90 secs) - For begginers
Medium (60 secs) - For a challenge
Easy (30 secs) - Fast-paced expert mode

### Dependencies

* Java - Version 8 or higher
* Screen Resolution - Min. 1024x768

### Executing program

game-directory/
├── Main.java
├── Welcome.java
├── AnimationsAndObjects.java
├── BallCalculations.java
├── Targets.java
├── TimerAndScore.java
├── GameState.java
├── GameStateManager.java
└── Pictures/
    ├── welcome_page.png
    ├── Pub_Interior_Image.jpeg
    ├── sling.png
    ├── beer.png
    └── bomb.png

Compile all the .java files and run the game

## Major Features

* Object-oriented programs
* JSON-based system to save the game state for serialization and deserialization of game components
* Event-driven programming using Swing
* Physics-like simulation

### Developer Notes

* Build with Java Swing for compatibility.
* Uses Jackson library for JSON serialization of game states.

## Authors

Contributors names

ex. Namita Binu
ex. Swarit Misra
