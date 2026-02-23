package game;

import model.BulletType;
import model.Player;
import model.Weapon;

public final class GameState {
    private final Player currentPlayer;
    private final Player otherPlayer;
    private final Weapon weapon;
    private final int turn;

    public GameState(Player currentPlayer, Player otherPlayer, Weapon weapon, int turn) {
        this.currentPlayer = currentPlayer;
        this.otherPlayer = otherPlayer;
        this.weapon = weapon;
        this.turn = turn;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public int getRemainingBullets() {
        return weapon.getbulletcount();
    }

    public BulletType peekNextBullet() {
        return weapon.peekNextBullet();
    }
}
