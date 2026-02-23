package itens;
import model.Player;
import itens.IitensType;
import itens.Iitens;

public class Algema implements Iitens {
    private final String name = "algema";
    private Player owner;      // quem possui o item
    private Player target;     // quem será algemado

    public Algema(Player owner) {
        this.owner = owner;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IitensType getType() {
        return IitensType.ALGEMA;
    }

    @Override
    public void use() {
        if (target == null) {
            System.out.println("Nenhum alvo para algemar.");
            return;
        }
        // aplica algema ao jogador alvo; ele ficará preso até que o dono
        // dispare duas vezes contra ele.
        target.applyHandcuffs(2, owner);
        System.out.println(owner.getName() + " usou a algema em " + target.getName() + "!");
    }
    
}
