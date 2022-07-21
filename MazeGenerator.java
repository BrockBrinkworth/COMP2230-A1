/*
Author: Brock Brinkworth
ID: C3331952
Date Created: 2/10/2021

Program: Generates a random maze.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class MazeGenerator {

    static private final int BOTH_CLOSED = 0;

    static private final int RIGHT_ONLY_OPEN = 1;

    static private final int DOWN_ONLY_OPEN = 2;

    static private final int BOTH_OPEN = 3;

    private int[][] maze; // The arrays for the maze
    
    private boolean[][] hasVisited; // 

    private int nRows; // Rows of the maze (Horizontal)

    private int nCols; // Columns of the maze (Vertical)

    private int startLocation; // Start location of the maze

    private int endLocation; // End location of the maze
    
    private int distanceAwayFromStart = 0;

    private Random rand = new Random();

    private int stackCalls = 0;

    public MazeGenerator(int nRows, int nCols) { // Constructor

        this.nRows = nRows;

        this.nCols = nCols;

        this.maze = new int[nRows][nCols];

        this.hasVisited = new boolean[nRows][nCols];

        for (int row = 0; row < nRows; row++) {
            
            for (int col = 0; col < nCols; col++) {

                maze[row][col] = 0;

                hasVisited[row][col] = false;

            }
        }

        startLocation = rand.nextInt(nRows*nCols);

        endLocation = startLocation;

        CellGenerator(startLocation);
        
    }

    public void CellGenerator(int cell) {

        stackCalls++;

        int row = cell / nCols;

        int col = cell % nCols;

        hasVisited[row][col] = true;

        // System.out.println("[" + row + "," + col + "]"); testing

        boolean hasRight = true;

        boolean hasDown = true;

        boolean hasLeft = true;

        boolean hasUp = true;


        if ((col+1 >= nCols) || (hasVisited[row][col+1])) {

            hasRight = false;

        }

        if ((row+1 >= nRows) || (hasVisited[row+1][col])) {

            hasDown = false;

        }

        if ((row-1 < 0) || hasVisited[row-1][col]) {

            hasUp = false;

        }

        if ((col-1 < 0) || (hasVisited[row][col-1])) {

            hasLeft = false;

        }

        if (!(hasRight || hasDown || hasUp || hasLeft) ) {

            if (distanceAwayFromStart < stackCalls) {

                distanceAwayFromStart = stackCalls;

                endLocation = cell;

            }
        }

        while ((hasRight || hasDown || hasLeft || hasUp)) {

            int random = rand.nextInt(4);

            if (random == 0 && hasRight) {

                if (maze[row][col] == 0) {

                    maze[row][col] = RIGHT_ONLY_OPEN;

                } else {

                    maze[row][col] = BOTH_OPEN;

                }
                hasRight = false;

                CellGenerator(row*nCols + col+ 1);

            } else if (random == 1 && hasDown) {

                
                if (maze[row][col] == 0) {

                    maze[row][col] = DOWN_ONLY_OPEN;

                } else {

                    maze[row][col] = BOTH_OPEN;

                }

                hasDown = false;

                CellGenerator((row+1)*nCols + col);

            } else if (random == 2 && hasLeft) {

                maze[row][col-1] = RIGHT_ONLY_OPEN;

                hasLeft = false;

                CellGenerator((row)*nCols + col-1);

            } else if (random == 3 && hasUp) {

                maze[row-1][col] = DOWN_ONLY_OPEN;

                hasUp = false;

                CellGenerator((row-1)*nCols + col);

            }

            if ((col+1 >= nCols) || (hasVisited[row][col+1])) {

                hasRight = false;

            }

            if ((row+1 >= nRows) || (hasVisited[row+1][col])) {

                hasDown = false;

            }

            if ((row-1 < 0) || hasVisited[row-1][col]) {

                hasUp = false;

            }

            if ((col-1 < 0) || (hasVisited[row][col-1])) {

                hasLeft = false;

            }

        }

        stackCalls--;

        return;

    }

	public static void main(String args[]) {

        Scanner s = new Scanner(System.in);

        int c, r;

		System.out.println("How many Columns do you want?");

        c = s.nextInt();

        System.out.println("How many Rows do you want?");

        r = s.nextInt();
        
        MazeGenerator obj = new MazeGenerator(c, r);

        System.out.println(obj.toString());

        /*try { // TESTING CODE

			obj.output();

		} catch (IOException e) {

			e.printStackTrace();
        }*/

        s.close();

	}

    private boolean containsFalse(boolean[][] a, int rows, int cols) {

        for (int row = 0; row < rows; row++) {
            
            for (int col = 0; col < cols; col++) {
                
                if (!a[row][col]) {
                    
                    return true;
                    
                }
            }
        }

        return false;

    }


	@Override
    public String toString() { // to string

		String theMaze = new String("");

        theMaze += nRows + "," + nCols + ":" + (startLocation+1) + ":" + (endLocation+1) + ":";

		for (int i = 0; i < nRows; i++) {

			for (int j = 0; j < nCols; j++) {

				theMaze += maze[i][j];
			}

		}

        String outputFile;

        Scanner s = new Scanner(System.in);

        System.out.println("Name the output file: ");

        outputFile = s.next();

        

        try {

            FileWriter writer = new FileWriter(outputFile + ".txt");

            writer.write(theMaze);

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        s.close();

		return theMaze;

	}

    public void output() throws IOException { // testing

        String[][] theMaze = new String[nRows*2+1][nCols*2+1];

        for (int i = 0; i < nRows*2+1; i++) { // testing

            for (int j = 0; j < nCols*2+1; j++) {

                theMaze[i][j] = "+";

            }
        }

        for (int i = 1; i < nRows*2; i++) { // testing

            for (int j = 1; j < nCols*2; j++) {

                if (i % 2 == 1 && j % 2 == 1) {

                    int row = (i-1)/2;

                    int col = (j-1)/2;

                    theMaze[i][j] = " ";

                    if (startLocation/ nCols == row && startLocation % nCols == col) {

                        theMaze[i][j] = "S";

                    }

                    if (endLocation/ nCols == row && endLocation % nCols == col) {

                        theMaze[i][j] = "F";

                    }

                    if ((maze[row][col] == RIGHT_ONLY_OPEN || maze[row][col] == BOTH_OPEN) && j != nCols*2 -1) {

                        theMaze[i][j+1] = " ";

                    }

                    if ((maze[row][col] == DOWN_ONLY_OPEN || maze[row][col] == BOTH_OPEN) && i != nRows*2 -1) {

                        theMaze[i+1][j] = " ";

                    }
                }
            }
        }

        for (int i = 0; i < nRows*2+1; i++) { // testing

            for (int j = 0; j < nCols*2+1; j++) {

                System.out.print(theMaze[i][j]);

            }
            
            System.out.println();

        }
    }

    private double distance(int x_1, int y_1, int x_2, int y_2) {

        int x_diff = Math.abs(x_1-x_2);

        int y_diff = Math.abs(y_1-y_2);

        return Math.sqrt(x_diff*x_diff+y_diff*y_diff);
    }
}