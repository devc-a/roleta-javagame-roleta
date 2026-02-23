package itens;

import model.Player;

public class Cura implements Iitens {
    private final String name = "cura";
    private Player player;

    public Cura(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public IitensType getType() {
        return IitensType.CURA;
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
        player.heal(2);
        System.out.println("você usou a cura, agora você tem " + player.getLife() + " de vida");
    }

}
