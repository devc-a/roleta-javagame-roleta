package itens;

import model.Weapon;
import java.util.Random;
import model.Player;
import itens.Algema;

public class ItemFactory {
    private static final Random random = new Random();

    public static Iitens createRandomItem(Weapon weapon, Player player) {
        int pick = random.nextInt(IitensType.values().length);
        IitensType itemType = IitensType.values()[pick];
        switch (itemType) {
            case LUPA:
                return new Lupa(weapon);
            case CURA:
                return new Cura(player);
            case DESCARTEBALAS:
                return new DescarteBalas(weapon);
            case CERROTE:
                return new Cerrote(weapon);
            case ALGEMA:
                return new Algema(player); // target will be set later when used
            default:
                throw new IllegalArgumentException("Tipo de item desconhecido: " + itemType);
        }
    }
}
