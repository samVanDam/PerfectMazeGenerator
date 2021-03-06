package app.logic.tracking;


// Class for saving the backtrack as an operation.
public class BackTrack extends Operation {

    private int xCoordinate, yCoordinate;

    public BackTrack(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public String toString(){
        return "Backtracking to (" + xCoordinate + "," + yCoordinate + ")";
    }
}
