package bot;
import game.GameState;
import itens.IitensType;
import model.BulletType;

public class BotAi {
    private final String name = "bot";

    public String getName() {
        return name;
    }

    public BotAction decideAction(GameState gameState) {
        // 1️⃣ Vida baixa → tentar curar
        if (gameState.getCurrentPlayer().getLife() <= 3) {
            if (gameState.getCurrentPlayer()
                    .getIitensByType(IitensType.CURA) != null) {
                return BotAction.USE_ITEM;
            }
        }

        // 2️⃣ Tem lupa → pode analisar a bala
        if (gameState.getCurrentPlayer()
                .getIitensByType(IitensType.LUPA) != null) {

            BulletType nextBullet = gameState.peekNextBullet();

            // bala verdadeira
            if (nextBullet == BulletType.TRUE) {

                // se tiver cerrote, fortalece o tiro
                if (gameState.getCurrentPlayer()
                        .getIitensByType(IitensType.CERROTE) != null) {
                    return BotAction.USE_ITEM;
                }

                return BotAction.SHOOT_ENEMY;
            }
        }

        // 3️⃣ Caso contrário, joga seguro
        return BotAction.SHOOT_ENEMY;
    }
}
