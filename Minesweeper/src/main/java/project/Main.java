package project;

public class Main {

    public static void main(String[] args) {

        GameMap gameMap = new GameMap(10, 10);
        printGameMap(gameMap);

    }

    private static void printGameMap(GameMap gameMap) {
        int dimension = gameMap.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Field field = gameMap.getField(i, j);
                if (field.isMine()) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" " + field.getNumberOfNeighbourMines() + " ");
                }
            }
            System.out.println();
        }
    }

}