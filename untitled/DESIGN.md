CompSci 308: Game DESIGN
===================

> This is the link to the assignment: [Game](http://www.cs.duke.edu/courses/compsci308/current/assign/01_game/)

### Goal
This project is a world-up themed variation of the classical game, Breakout. The design goal is to implement a multi-level
and multi-player football game based on Breakout, where players use the paddles to try to kick the ball into the opposing gate.

At different level, the players will expect different numbers of opponents, different difficulty in the form of ball speed and door sizes, 
and different power ups. When NPC are hit, the power ups will drop down, which can create amazing effects : )

Finally, this game is also inspired by those time I spent playing games with my brother.

### How to add new features into the game
1. Add more images into the resource file to allow for new opponents and NPCs
2. Modify the step() function to allow for new collision features, new power up effects
3. Modify the handleKeyInput() function to allow for new keys

### Decision Justification and Tradeoffs
1. I considered using more parameters to avoid too many private variables
    * Pro: less private variables will be used, and the functions will have less "strange dependencies" (like suddenly referring to a variable that the reader has to look for)
    * Con: the logic will be much more complicated, with variables being passed here and there and easily lost. It would also be more complicated to change a certain important value, like ball direction.
    * from the designer perspective, I choose using private variables because it just simplifies logic. For readability I would also use it to avoid messing up functions
2. I considered using more functions to capture my actions in the step function (which is animation)
    * Pro: cleaner code and extendability
    * Con: very hard to implement because there are many cases. When new need arises, in addition, the current functions might not work any more
    * For readability and extendability I would try to capture the animation logic with methods if I had the time
    
### Assumptions or decisions made to simplify or resolve ambiguities
* I assumed that the level of games would never go above 3. If not, we are going to need to add more pictures, audios, etc. to allow for higher levels
* I assumed that the faster the ball is and the shorter our paddle is, the harder it is for the users to win
* I separated the object creation, default values and game logic into three files so as to avoid confusion and avoid repeated codes.
 
