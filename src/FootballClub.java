import java.util.*;

public class FootballClub implements Runnable {
    public String name;
    public int budget;
    public Store market;
    public List<FootballPlayer> squad;

    public FootballClub(String name, int budget, Store market) {
        this.name = name;
        this.budget = budget;
        this.market = market;
        this.squad = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println(name + " начал покупки с бюджетом " + budget + " млн €");

        while (budget > 0) {
            FootballPlayer player = market.buyPlayer(this);
            if (player == null) {
                break;
            }

            if (player.price <= budget) {
                squad.add(player);
                budget -= player.price;
                System.out.println(name + " купил " + player.name + " за " +
                        player.price + " млн €. Остаток бюджета: " + budget + " млн €");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n " + name + " завершил трансферы");
        System.out.println("Остаток бюджета: " + budget + " млн €");
        System.out.println("Состав:");
        for (FootballPlayer player : squad) {
            System.out.println("  - " + player);
        }
        System.out.println();
    }
}