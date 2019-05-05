package app.logic;

import app.logic.Tracking.KnockDownWall;
import app.logic.Tracking.OperationTracker;
import app.logic.Tracking.UnMark;
import app.logic.domain.Cell;
import app.logic.domain.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class BottomUpAlgo {
    // This algorithm takes a random walk from the starting point to the ending point of the maze to create the solution,
    // and afterwards build the maze around the solution with Prim's algorithm.
    private OperationTracker opTracker;
    private Maze maze;
    private int currentX, currentY;
    private ArrayList<Cell> solution;
    private ArrayList<Cell> frontiers;

    public BottomUpAlgo(Maze maze){
        this.maze = maze;
        opTracker = new OperationTracker();
        solution = new ArrayList<>();
        frontiers = new ArrayList<>();
        currentX = 0;
        currentY = 0;
    }

    void runBottomUpAlgo(){
        ArrayList<Cell> neighbourCells;
        // Set the ending cell as visited.
        Cell start = new Cell(currentX, currentY), nextCell, neighbour;
        maze.setVisited(maze.getDimX()-1, maze.getDimY()-1);

        // Do random walk to create a solution.
        HashMap<Cell, Cell> walk = Method.performRandomWalk(start, maze);

        // Add it too the maze and save all the frontiers
        addWalkToMaze(walk, start);

        // Run Prim's algorithm to finish the maze.
        do{
            // Track visited
            if(!maze.getTile(currentX, currentY).isVisited())
                maze.setVisited(currentX, currentY);

            // Add neighbours of the current cell to the frontiers.
            addFrontiers(currentX, currentY);

            if(frontiers.isEmpty())
                break;

            // Choose next cell and remove it from frontiers
            nextCell = Method.getRandom(frontiers);
            frontiers.remove(nextCell);

            // Get a random cell which is visited and a neighbour to the chosen frontier
            neighbourCells = maze.getPossibleNeighboursToFrontier(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            neighbour = Method.getRandom(neighbourCells);

            // Break down wall and track the operation
            maze.breakDownWall(maze.getTile(neighbour.getXCoordinate(), neighbour.getYCoordinate()), nextCell);
            opTracker.add(new KnockDownWall(neighbour.getXCoordinate(), neighbour.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));

            // Set next cell to current cell
            currentX = nextCell.getXCoordinate();
            currentY = nextCell.getYCoordinate();
        } while(true);

    }

    OperationTracker getOpTracker() {
        return opTracker;
    }


    private void addWalkToMaze(HashMap<Cell, Cell> walk, Cell startingCell) {
        Cell nextCell, currentCell = startingCell;
        do {
            frontiers.remove(currentCell);
            //System.out.println("Removing (" + currentCell.getXCoordinate() + ", " + currentCell.getYCoordinate() + ")");

            nextCell = walk.get(currentCell);

            maze.breakDownWall(currentCell, nextCell);
            opTracker.add(new KnockDownWall(currentCell.getXCoordinate(), currentCell.getYCoordinate(), nextCell.getXCoordinate(), nextCell.getYCoordinate()));
            addFrontiers(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            if (nextCell.isVisited())
                break;

            maze.setVisited(nextCell.getXCoordinate(), nextCell.getYCoordinate());

            currentCell = nextCell;
        } while (true);
        currentX = nextCell.getXCoordinate();
        currentY = nextCell.getYCoordinate();
    }

    private void addFrontiers(int currentX, int currentY){
        ArrayList<Cell> neighbours = maze.getPossibleNeighbours(currentX, currentY);
        for (Cell cell : neighbours){
            if (!frontiers.contains(cell)) {
                frontiers.add(cell);
                //System.out.println("Adding (" + cell.getXCoordinate() + ", " + cell.getYCoordinate() + ")");
            }
        }
    }
}
