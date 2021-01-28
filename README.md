# Introduction

This application lets the user create and solve 9x9 Sudoku puzzles interactively. 
Currently, there are given the following possibilities:

* Create 9x9 Sudoku puzzles via an algorithm
* Enter existing 9x9 Sudoku puzzles (e.g. from a puzzle magazine)
* Solve the Sudoku puzzles interactively
* Restart the current Sudoku puzzle
* Show the next solution step or the whole solution for the current Sudoku puzzle
* Print the current Sudoku puzzles
* Print multiple Sudoku puzzles with a selectable difficulty
* Adjust the font size for smaller or bigger numbers in the Sudoku puzzle
* Compare the current state of the Sudoku puzzle with the solution
* Import and export Sudoku puzzles (e.g. to continue playing later)

# Structure:

The package `main` includes the `Start` class which is the entry point of the application.

The main logic of the application is handled inside the `controller` package. 
The single controllers create the GUI elements (which are placed in the `view` package) 
and the model elements (which are placed in the `model` packages). 
They also perform the synchronization between view and model so that view and model do not need to know each other.

To structure the logic when handling user inputs, separate classes are placed in the `eventHandling` package.
Those classes are used for the logical behaviors of the GUI elements and can be seen as part of the controllers.
Their separation from the `controller` package should make the responsibility of the different application elements 
more clear.
Thus, the modification of the Sudoku board is handled through the `CtrlBoard` in collaboration with the single affected
event handling classes.

The `computing` package contains classes e.g. for solving and creating the Sudoku puzzles.

Some helpful classes which may not be strictly limited to the logic of a Sudoku puzzle are placed inside the `utils` 
package. This package also includes the class `LanguageBundle` which is needed for supporting different languages for 
the user interface (right now the GUI is available in German and English).

# Build and start JAR:

1. Execute the Maven goal `package`
2. The JAR file _./target/Sudoku-1.0-SNAPSHOT-jar-with-dependencies.jar_ includes all dependencies and can directly be 
   started with a double click if Java 11 is installed on the device