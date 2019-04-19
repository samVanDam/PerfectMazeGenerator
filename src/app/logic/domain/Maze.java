package app.logic.domain;

import java.util.ArrayList;

public class Maze {

    // This is the structure to hold the maze

    private int dimX, dimY;
    private Cell[][] cells;

    public Maze(int dimX, int dimY){
        this.dimX = dimX;
        this.dimY = dimY;

        cells = new Cell[dimX][dimY];
        for (int i = 0; i < dimX; i++){
            for (int j = 0; j < dimY; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public int getDimX(){
        return dimX;
    }

    public int getDimY(){
        return dimY;
    }

    public void setVisited(int posX, int posY){
        cells[posX][posY].setVisited();
    }

    public Cell getTile(int currentX, int currentY){
        return cells[currentX][currentY];
    }

    public void breakDownWall(Cell currentCell, Cell nextCell){
        // Check for WEST
        if (currentCell.getXCoordinate()-1 == nextCell.getXCoordinate()){
            currentCell.breakDownWall(Direction.WEST);
            nextCell.breakDownWall(Direction.EAST);
        }

        // Check for EAST
        if (currentCell.getXCoordinate()+1 == nextCell.getXCoordinate()){
            currentCell.breakDownWall(Direction.EAST);
            nextCell.breakDownWall(Direction.WEST);
        }

        // Check for NORTH
        if (currentCell.getYCoordinate()-1 == nextCell.getYCoordinate()){
            currentCell.breakDownWall(Direction.NORTH);
            nextCell.breakDownWall(Direction.SOUTH);
        }

        // Check for SOUTH
        if (currentCell.getYCoordinate()+1 == nextCell.getYCoordinate()){
            currentCell.breakDownWall(Direction.SOUTH);
            nextCell.breakDownWall(Direction.NORTH);
        }


    }

    public ArrayList<Cell> getPossibleNeighbours(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();

        // WEST
        if (0 <= currentX-1 && cells[currentX-1][currentY].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX && cells[currentX+1][currentY].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1 && cells[currentX][currentY-1].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY && cells[currentX][currentY+1].isVisited().equals(false))
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }


    public ArrayList<Cell> getPossibleNeighboursToFrontier(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();

        // WEST
        if (0 <= currentX-1 && cells[currentX-1][currentY].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX && cells[currentX+1][currentY].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1 && cells[currentX][currentY-1].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY && cells[currentX][currentY+1].isVisited().equals(true))
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }

    public ArrayList<Cell> getAllNeighbours(int currentX, int currentY) {
        ArrayList<Cell> possibleNeighbours = new ArrayList<>();

        // WEST
        if (0 <= currentX-1)
            possibleNeighbours.add(cells[currentX-1][currentY]);

        // EAST
        if(currentX+1 < dimX)
            possibleNeighbours.add(cells[currentX+1][currentY]);

        // NORTH
        if(0 <= currentY-1)
            possibleNeighbours.add(cells[currentX][currentY-1]);

        // SOUTH
        if(currentY+1 < dimY)
            possibleNeighbours.add(cells[currentX][currentY+1]);

        return possibleNeighbours;
    }


    public ArrayList<Cell> getPossiblePaths(Cell prevCell, Cell curCell) {
        ArrayList<Cell> possiblePaths = new ArrayList<>();

        if( !curCell.getWall(Direction.NORTH) && 0 <= curCell.getYCoordinate()-1){
            possiblePaths.add(cells[curCell.getXCoordinate()][curCell.getYCoordinate()-1]);
        }
        if( !curCell.getWall(Direction.SOUTH) && curCell.getYCoordinate()+1 < dimY){
            possiblePaths.add(cells[curCell.getXCoordinate()][curCell.getYCoordinate()+1]);
        }
        if( !curCell.getWall(Direction.EAST) && curCell.getXCoordinate()+1 < dimX){
            possiblePaths.add(cells[curCell.getXCoordinate()+1][curCell.getYCoordinate()]);
        }
        if( !curCell.getWall(Direction.WEST) && 0 <= curCell.getXCoordinate()-1){
            possiblePaths.add(cells[curCell.getXCoordinate()-1][curCell.getYCoordinate()]);
        }
        if (prevCell != null){
            possiblePaths.remove(prevCell);
        }
        return possiblePaths;
    }
}