package itens;

import model.Weapon;
import model.BulletType;

public class DescarteBalas implements Iitens {
    private final String name = "descarte de balas";
    private Weapon weapon;

    public DescarteBalas(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public IitensType getType() {
        return IitensType.DESCARTEBALAS;
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub
        BulletType bullet = weapon.deletNextBullet();
        if (bullet == null) {
            System.out.println("não há mais tiros na arma para descartar");
        }
        System.out.println("você usou o descarte de balas, agora você descartou o próximo tiro da arma"
                + bullet);
    }

}