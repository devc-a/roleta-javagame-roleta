import game.Game;
import model.Player;
import model.Weapon;

public class Main {
    public static void main(String[] args) {

        // cada jogador começa com 5 de vida (máximo 10)
        Player bot = new Player("bot", 5);
        Player player = new Player("player", 5);
        Weapon weapon = new Weapon();

        Game game = new Game(bot, player, weapon);
        game.start();
    }
}