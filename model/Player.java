package model;

import itens.IitensType;
import itens.Iitens;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int MAX_LIFE = 10;    // built‑in maximum life
    private static final int MAX_ITEMS = 6;     // máximo de itens que um jogador pode carregar

    private String name;
    private int life;
    private List<Iitens> itens = new ArrayList<>();

    public Player(String name, int life) {
        this.name = name;
        this.life = Math.min(life, MAX_LIFE);
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void ataqueDamage(int damage) {
        this.life -= damage;
        if (life < 0) {
            life = 0;
        }
    }

    public void heal(int healAmount) {
        this.life = Math.min(MAX_LIFE, this.life + healAmount);
    }

    public void addItem(Iitens item) {
        if (itens.size() >= MAX_ITEMS) {
            System.out.println(name + " já possui o máximo de " + MAX_ITEMS + " itens – o item não foi adicionado");
            return;
        }
        itens.add(item);
    }

    // expose items so caller can iterate over them (não permite modificar diretamente)
    public List<Iitens> getItens() {
        return itens;
    }

    public Iitens getIitensByType(IitensType type) {
        return itens.stream().filter(i -> i.getType() == type).findFirst().orElse(null);
    }

    public void useItem(IitensType type) {
        Iitens item = getIitensByType(type);
        if (item == null) {
            System.out.println("você não tem esse item para usar");
            return;
        }
        item.use();
        // remover o objeto, não o enum
        itens.remove(item);
    }
}