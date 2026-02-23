package itens;

import model.Weapon;
import model.BulletType;

public class Lupa implements Iitens {
    private final String name = "lupa";
    private Weapon weapon;

    public Lupa(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public IitensType getType() {
        return IitensType.LUPA;
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
        BulletType bullet = weapon.peekNextBullet();
        if (bullet == null) {
            System.out.println("não há mais tiros na arma para ver");
            return;
        }
        System.out.println("você usou a lupa, agora você pode ver o próximo tiro da arma" + bullet);
    }

}