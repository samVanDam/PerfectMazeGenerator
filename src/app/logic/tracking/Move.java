package app.logic.tracking;

// Class for saving a move as an operation.
public class Move extends Operation {

    private int xCoordinate, yCoordinate;

    public Move(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate(){
        return xCoordinate;
    }

    public int getyCoordinate(){
        return yCoordinate;
    }

    @Override
    public String toString(){
        return "Moving to (" + xCoordinate + "," + yCoordinate + ")";
    }
}
