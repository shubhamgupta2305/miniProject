BATTLESHIP:


This Java application is built using Java SE 11 (will work with version 8 or later) based upon the provided similar HTML/CSS/JavaScript application documentation.

The GUI is built using the standard Swing components.

The ZIP folder has the five Java classes (source code), this READ_ME.txt, and three image files (two of which were provided). The image files are used in the application to populate the cells of the JTable dutring the game play.

There are five Java classes:

1) Ship - This represents a battleship.
2) Controller - The controller class based upon the HTML / JavaScript code.
3) Model - The model class based upon the HTML / JavaScript code. 
4) GameWindow - This is the main GUI for the application. This also serves as the starter program.
5) GameTableModel - This is the table model for the JTable in the GameWindow class. The JTable uses the table model object to manage the actual table data.

The three image files:
1) ship.png - This is placed in the game table cell when there is "hit".
1) miss.png - This is placed in the game table cell when there is "miss".
1) blank.png - This is used to populate the game table cells initially.


Compile and run the application from operating system command prompt as:

> javac *.java
> java -cp . GameWindow

In the game window enter a two character string (first letter and a number - these correspond to the row and column) and press the <Fire> button. Continue until the game is over when all the battleships are hit.

There is an option to reset the game and play again, if you choose to - use the menu option Game -> Reset.


*
*