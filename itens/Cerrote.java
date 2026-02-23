package itens;

import model.Weapon;

public class Cerrote implements Iitens {
    private final String name = "cerrote";
    private Weapon weapon;

    public Cerrote(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IitensType getType() {
        return IitensType.CERROTE;
    }

    @Override
    public void use() {
        weapon.setDamage(2);
        System.out.println("você usou o cerrote, o próximo tiro causará 2 de dano");
    }
}