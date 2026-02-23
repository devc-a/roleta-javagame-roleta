package game;

import bot.BotAi;
import bot.BotAction;
import itens.ItemFactory;
import model.Player;
import model.Weapon;
import itens.IitensType;
import model.BulletType;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Weapon weapon;
    private BotAi botAi;

    private Player currentPlayer;
    private Player otherPlayer;

    // controla qual round estamos jogando
    private int round = 1;
    private final Random rnd = new Random();

    public Game(Player player1, Player player2, Weapon weapon) {
        this.player1 = player1;
        this.player2 = player2;
        this.weapon = weapon;
        this.botAi = new BotAi();

        this.currentPlayer = player1;
        this.otherPlayer = player2;
    }

    public void start() {
        System.out.println("🎮 JOGO INICIADO");
        // configura condição inicial dos jogadores
        // (as chamadas de construtores no Main devem passar 5, mas garantimos aqui)
        player1.heal(0); // faz clamp para MAX_LIFE caso alguém passe valor maior
        player2.heal(0);

        // carrega balas do round 1 e distribui itens iniciais
        weapon.loadInitialRound();
        giveRandomItemsToBoth(4);

        Scanner scanner = new Scanner(System.in);

        while (player1.isAlive() && player2.isAlive()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println("➡ Vez de: " + currentPlayer.getName()
                    + " (vida: " + currentPlayer.getLife() + ")");
            // mostra itens do jogador atual + vida
            showInventory(currentPlayer);

            executeTurn(scanner);
        }

        System.out.println("\n🏆 FIM DE JOGO");
        System.out.println("Vencedor: " +
                (player1.isAlive() ? player1.getName() : player2.getName()));
    }

    private void executeTurn(Scanner scanner) {
        BotAction action;

        if (currentPlayer.getName().equalsIgnoreCase("bot")) {
            GameState state = new GameState(
                    currentPlayer,
                    otherPlayer,
                    weapon, 0
            );
            action = botAi.decideAction(state);
        } else {
            // turno humano: perguntar ação
            action = askPlayerAction(scanner);
        }

        executeAction(action, scanner);
    }

    private void executeAction(BotAction action, Scanner scanner) {
        switch (action) {
            case USE_ITEM:
                IitensType itemToUse = selectItemForCurrent(scanner);
                if (itemToUse != null) {
                    currentPlayer.useItem(itemToUse);
                } else {
                    System.out.println(currentPlayer.getName() + " não possui item para usar");
                }
                // não troca turno
                break;

            case SHOOT:
                System.out.println(currentPlayer.getName() + " atirou em SI MESMO");
                BulletType bullet = weapon.shoot();
                reportShot(bullet, currentPlayer);
                // não troca turno
                break;

            case SHOOT_ENEMY:
                System.out.println(currentPlayer.getName() + " atirou no INIMIGO");
                BulletType bullet2 = weapon.shoot();
                reportShot(bullet2, otherPlayer);
                switchTurn();
                break;
        }

        // após qualquer ação que possa consumir balas, verificamos se precisamos
        // iniciar um novo round
        maybeAdvanceRound();
    }

    private void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    // helpers --------------------------------------------------------------

    /**
     * entrega randomicamente até `count` novos itens para ambos os jogadores,
     * respeitando o limite de 6 itens por jogador.
     */
    private void giveRandomItemsToBoth(int count) {
        giveRandomItems(player1, count);
        giveRandomItems(player2, count);
    }

    private void giveRandomItems(Player p, int count) {
        int before = p.getItens().size();
        for (int i = 0; i < count; i++) {
            if (p.getItens().size() >= 6) {
                break;
            }
            p.addItem(ItemFactory.createRandomItem(weapon, p));
        }
        int added = p.getItens().size() - before;
        System.out.println(p.getName() + " recebeu " + added + " itens (total: " +
                p.getItens().size() + ")");
    }

    /**
     * se não há mais balas, inicia o round seguinte gerando novos tiros e
     * novos itens.
     */
    private void maybeAdvanceRound() {
        if (weapon.getbulletcount() == 0) {
            round++;
            System.out.println("\n🔄 Round " + round + " começando – todas as balas acabaram");
            // rodadas subsequentes usam distribuição totalmente aleatória
            weapon.loadRandomRound(6);
            giveRandomItemsToBoth(4);
        }
    }

    private void showInventory(Player player) {
        List<IitensType> types = player.getItens().stream()
                .map(i -> i.getType())
                .collect(java.util.stream.Collectors.toList());
        System.out.println("Vida: " + player.getLife() + " | Itens em posse: "
                + (types.isEmpty() ? "nenhum" : types));
    }

    private BotAction askPlayerAction(Scanner scanner) {
        System.out.println("Escolha ação: 1) atirar em si 2) atirar no inimigo" +
                " 3) usar item");
        int opt = -1;
        try {
            opt = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            // ignora
        }
        switch (opt) {
            case 1:
                return BotAction.SHOOT;
            case 2:
                return BotAction.SHOOT_ENEMY;
            case 3:
                return BotAction.USE_ITEM;
            default:
                System.out.println("Opção inválida, atirando no inimigo por padrão");
                return BotAction.SHOOT_ENEMY;
        }
    }

    private IitensType selectItemForCurrent(Scanner scanner) {
        // se for bot, escolhe automaticamente seguindo prioridades simples
        if (currentPlayer.getName().equalsIgnoreCase("bot")) {
            if (currentPlayer.getIitensByType(IitensType.CURA) != null) {
                return IitensType.CURA;
            }
            if (currentPlayer.getIitensByType(IitensType.LUPA) != null) {
                return IitensType.LUPA;
            }
            if (currentPlayer.getIitensByType(IitensType.CERROTE) != null) {
                return IitensType.CERROTE;
            }
            if (currentPlayer.getIitensByType(IitensType.DESCARTEBALAS) != null) {
                return IitensType.DESCARTEBALAS;
            }
            return null;
        }

        // humano: mostrar opções dos itens disponíveis
        List<IitensType> available = currentPlayer.getItens().stream()
                .map(i -> i.getType())
                .collect(java.util.stream.Collectors.toList());
        if (available.isEmpty()) {
            return null;
        }
        System.out.println("Escolha um item para usar: " + available);
        String line = scanner.nextLine().toUpperCase().trim();
        try {
            return IitensType.valueOf(line);
        } catch (IllegalArgumentException ex) {
            System.out.println("Item inválido");
            return null;
        }
    }

    private void reportShot(BulletType bullet, Player target) {
        if (bullet == null) {
            System.out.println("A arma não tinha mais balas");
            return;
        }
        if (bullet == BulletType.TRUE) {
            int dmg = weapon.consumeDamage();
            target.ataqueDamage(dmg);
            System.out.println("Tiro verdadeiro! " + dmg + " de dano. "
                    + target.getName() + " agora tem " + target.getLife() + " de vida");
        } else {
            System.out.println("Tiro em branco");
        }
    }
}
