package project;

public class Field {

    private boolean isMine;
    private boolean isVisible;
    private Integer numberOfNeighbourMines;

    private Field(boolean isMine, boolean isVisible, Integer numberOfNeighbourMines) {
        this.isMine = isMine;
        this.isVisible = isVisible;
        this.numberOfNeighbourMines = numberOfNeighbourMines;
    }

    public static Field constructRegularField(int numberOfNeighbourMines) {
        return new Field(false, false, numberOfNeighbourMines);
    }

    public static Field constructMineField() {
        return new Field(true, false, null);
    }

    public boolean isMine() {
        return isMine;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible() {
        isVisible = true;
    }
    public Integer getNumberOfNeighbourMines() {
        return numberOfNeighbourMines;
    }

}
