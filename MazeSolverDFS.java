/*
Author: Brock Brinkworth
ID: C3331952
Date Created: 2/10/2021

Program: Solves the random maze using depth first search.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeSolverDFS {

    static private final int BOTH_CLOSED = 0;

    static private final int RIGHT_ONLY_OPEN = 1;

    static private final int DOWN_ONLY_OPEN = 2;

    static private final int BOTH_OPEN = 3;

    private int[][] maze; // The arrays for the maze
    
    private int nRows; // Rows of the maze (Horizontal) niggers

    private boolean[][] hasVisited;

    private int nCols; // Columns of the maze (Vertical)

    private int startLocation; // Start location of the maze

    private int endLocation; // End location of the maze

    private List<Integer> sol;

    public MazeSolverDFS(String sample) {

        String[] ssample = sample.trim().split(":");

        String[] sstring = ssample[0].split(",");

        nRows = Integer.parseInt(sstring[0]);

        nCols = Integer.parseInt(sstring[1]);

        startLocation = Integer.parseInt(ssample[1]) - 1;

        endLocation = Integer.parseInt(ssample[2]) - 1;

        String mazess = ssample[3];

        System.out.println(mazess);

        maze = new int[nRows][nCols];

        hasVisited = new boolean[nRows][nCols];

        for (int row = 0; row < nRows; row++) {
            
            for (int col = 0; col < nCols; col++) {

                maze[row][col] = Integer.parseInt(mazess.substring(row*nCols + col, row*nCols + col + 1));
                
                hasVisited[row][col] = false;

            }
        }

        //output(); // testing
        
        sol = new ArrayList<Integer>();

        final long startTime = System.currentTimeMillis();

        CellSolverDFS(startLocation);
        
        final long endTime = System.currentTimeMillis();

        
        Collections.reverse(sol);


        System.out.println("(" + sol.stream().map(o -> Integer.toString(o)).collect(Collectors.joining(",")) + ")");
        
        System.out.println(sol.size());

        System.out.println(endTime-startTime);

    }

    public boolean CellSolverDFS(int cell) {

        int row = cell / nCols;

        int col = cell % nCols;

        hasVisited[row][col] = true;

        if (cell == endLocation) {

            sol.add(cell);

            return true;

        }

        boolean isSol_1 = false;

        boolean isSol_2 = false;

        boolean isSol_3 = false;

        boolean isSol_4 = false;


        if (!(col+1 >= nCols) && !hasVisited[row][col+1] && (maze[row][col] == RIGHT_ONLY_OPEN || maze[row][col] == BOTH_OPEN)) {
            
            isSol_1 = CellSolverDFS(row*nCols + col + 1);

        }

        if (!(row+1 >= nRows) && !hasVisited[row+1][col] && (maze[row][col] == DOWN_ONLY_OPEN || maze[row][col] == BOTH_OPEN)) {
            
            isSol_2 = CellSolverDFS((row+1)*nCols + col);

        }

        if (!(row-1 < 0) && !hasVisited[row-1][col] && (maze[row-1][col]  == DOWN_ONLY_OPEN || maze[row -1][col] == BOTH_OPEN)) {

            isSol_3 = CellSolverDFS((row-1)*nCols + col);

        }

        if (!(col-1 < 0) && !hasVisited[row][col-1] &&  (maze[row][col-1] == RIGHT_ONLY_OPEN || maze[row][col-1] == BOTH_OPEN)) {

           isSol_4 = CellSolverDFS(row*nCols + col -1);

        }

        if (isSol_1 || isSol_2 || isSol_3 || isSol_4) {
            
            sol.add(cell);

            return true;

        }

        return false;

    }

    public static void main(String[] args) throws Exception {
        
        String arg = new String(args[0]);

        try {

            File text = new File(arg);

            Scanner sc = new Scanner(text);

            String inputs = sc.next();

            MazeSolverDFS solver = new MazeSolverDFS(inputs);

            sc.close();

        } catch (Exception e) {

            System.out.println("Incorrect file name.");
                
        }
        
    }

     public void output() {

        String[][] theMaze = new String[nRows*2+1][nCols*2+1];

        for (int i = 0; i < nRows*2+1; i++) {

            for (int j = 0; j < nCols*2+1; j++) {

                    theMaze[i][j] = "+";
                    
            }
        }

        for (int i = 1; i < nRows*2; i++) {

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

        for (int i = 0; i < nRows*2+1; i++) {

            for (int j = 0; j < nCols*2+1; j++) {

                    System.out.print(theMaze[i][j]);

            }

            System.out.println();

        }
    }
}