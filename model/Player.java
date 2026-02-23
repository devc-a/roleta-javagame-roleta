package model;

import itens.IitensType;
import itens.Iitens;
import itens.Algema;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int MAX_LIFE = 10;    // built‑in maximum life
    private static final int MAX_ITEMS = 6;     // máximo de itens que um jogador pode carregar

    private String name;
    private int life;
    private List<Iitens> itens = new ArrayList<>();

    /* when true the player is considered imprisoned/handcuffed */
    private boolean handcuffed;  // default false
    private int handcuffShotsRemaining;
    private Player cuffedBy;

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

    /*
     * Return true if the player is currently handcuffed / imprisoned.
     * the game can call this to decide whether the player may act.
     */
    public boolean isHandcuffed() {
        return handcuffed;
    }

    /**
     * Opposite of {@link #isHandcuffed()}; /the/ “am I free?” check.
     */
    public boolean isFreeOfPrison() {
        return !handcuffed;
    }

    /**
     * Mark the player as handcuffed (or release them).
     */
    public void setHandcuffed(boolean value) {
        this.handcuffed = value;
        if (!value) {
            handcuffShotsRemaining = 0;
            cuffedBy = null;
        }
    }

    /**
     * Apply handcuffs coming from another player; the target remains
     * restrained until the specified number of shots from `by` are fired
     * at them.
     */
    public void applyHandcuffs(int shots, Player by) {
        this.handcuffed = true;
        this.handcuffShotsRemaining = shots;
        this.cuffedBy = by;
        System.out.println(name + " foi algemado por " + by.getName() + "!"
                + " precisa receber " + shots + " tiros para se libertar.");
    }

    /**
     * Called when `shooter` fires at this player; if the shooter is the one
     * who applied the cuffs we decrement the remaining counter and release
     * when it hits zero.
     */
    public void notifyShotReceived(Player shooter) {
        if (handcuffed && cuffedBy == shooter) {
            handcuffShotsRemaining--;
            if (handcuffShotsRemaining <= 0) {
                handcuffed = false;
                cuffedBy = null;
                System.out.println(name + " foi liberado das algemas!");
            } else {
                System.out.println(name + " ainda continua algemado (" +
                        handcuffShotsRemaining + " tiros restantes).");
            }
        }
    }

    // accessors for handcuff state -------------------------------------------------
    public int getHandcuffShotsRemaining() {
        return handcuffShotsRemaining;
    }

    public Player getCuffedBy() {
        return cuffedBy;
    }

    public boolean isCuffedBy(Player p) {
        return handcuffed && cuffedBy == p;
    }

    // overload of useItem to allow an opponent context
    public void useItem(IitensType type, Player other) {
        Iitens item = getIitensByType(type);
        if (item == null) {
            System.out.println(name + " não possui o item " + type + ";");
            return;
        }
        // if item needs to know about other player, give it now
        if (item instanceof Algema) {
            ((Algema) item).setTarget(other);
        }
        item.use();
        // remover o objeto, não o enum
        itens.remove(item);
    }
}