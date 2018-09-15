game
====

This project implements the game of Breakout.

Name: 

### Timeline
   * Starts: Sep 1
   * Ends: Sep 9
   * Total Hours Spent:
        * Planning: 3 hrs
        * Reading tutorials provided: 4 hrs
        * Reading example codes provided: 1 hrs
        * Implementation: 12 hrs
        * Documenting: 0.5 hrs

### Resources Used
* images:
    * buffs and ball: credit to Professor Robert C. Duvall
    * players and enermies:
        * https://www.deviantart.com/a2nil/art/Fred-Brazilian-Soccer-Player-Vector-Art-459164120
        * https://www.cartoonstock.com/directory/f/famous_footballer.asp
    * background images:
        * https://www.vectorstock.com/royalty-free-vector/green-soccer-field-background-vector-6620088
        * https://pngtree.com/freebackground/flat-white-background_442279.html
        * http://fpvforums.org/black-hole-galaxy-wallpaper.html
* background musics:
    * https://www.mp3juices.cc/
    * Shakira - Waka Waka

### Running the Program

* Main class:

* Data files needed: 
	files in the resources directory

* Key/Mouse inputs:
    * digit 1: first player move left
    * digit 2: first player move right
    * keynode.left: second player move left
    * keynode.right: second player move right


* Cheat keys:
    * number 7 go to the next level
    * number 8 reduce the opponent number
    * number 9 frozen the opponent
* Known Bugs:
    1. The ball can get stuck with the gate at a certain angle, leading to unreasonable increment in scores
    2. The ball can stick with a player for a while. This doesn't affect the game a lot and it's even funny, but for future detail adding we need to solve this
    3. Each time the game finishes with the player winning, the program would return an Exception saying that it is looking for another new music file "song3.mp3" but I haven't prepared any
    
### Notes
* Please add more field images and musics when you want to add more levels.
### Impressions
* Some physics in animation still need to be refined
* Graphics need improvement
* Animation codes requires some cleaning



