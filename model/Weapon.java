package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Weapon {
    private List<BulletType> bulletTypes;
private int damage = 1;

    public Weapon() {
        bulletTypes = new ArrayList<>();
    }

    /**
     * Remove all bullets currently loaded in the weapon.
     */
    public void resetBullets() {
        bulletTypes.clear();
    }

    /**
     * Load bullets for the very first round: exactly 6 shots where
     * 4 são falsas e 2 são verdadeiras, embaralhadas.
     */
    public void loadInitialRound() {
        resetBullets();
        List<BulletType> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(BulletType.FALSE);
        }
        for (int i = 0; i < 2; i++) {
            list.add(BulletType.TRUE);
        }
        Collections.shuffle(list);
        bulletTypes.addAll(list);
    }

    /**
     * Load a given number of bullets each chosen at random (true/false).
     * Used on subsequent rounds when the distribution is completely random.
     */
    public void loadRandomRound(int count) {
        resetBullets();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            bulletTypes.add(rnd.nextBoolean() ? BulletType.TRUE : BulletType.FALSE);
        }
    }

    public void addBullet(BulletType bullet) {
        bulletTypes.add(bullet);
    }
public void setDamage(int damage){
    this.damage = damage;
}

public int consumeDamage(){
    int dmg = damage;
    damage = 1;
    return dmg;
}
    public BulletType shoot() {
        if (bulletTypes.isEmpty()) {
            return null;
        }
        return bulletTypes.remove(0);
    }

    public BulletType peekNextBullet() {
        if (bulletTypes.isEmpty()) {
            return null;
        }
        return bulletTypes.get(0);
    }

    public BulletType deletNextBullet() {
        return shoot();
    }
    public int getbulletcount(){
        return bulletTypes.size();
    }
}