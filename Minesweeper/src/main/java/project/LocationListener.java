package project;

public class LocationListener implements Listener {

    private final Location location;

    public LocationListener(Location location) {
        this.location = location;
    }

    @Override
    public void locationClicked(GameMap gameMap) {
        gameMap.calculateGameMapChange(this.location);
    }

}