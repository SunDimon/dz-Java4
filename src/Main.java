import java.util.*;
import java.util.concurrent.*;
public class Main {
    public static void main(String[] args) {
        List<FootballPlayer> players = List.of(
                new FootballPlayer("Лионель Месси", 50),
                new FootballPlayer("Криштиану Роналду", 45),
                new FootballPlayer("Килиан Мбаппе", 180),
                new FootballPlayer("Эрлинг Холанн", 170),
                new FootballPlayer("Винисиус Жуниор", 120),
                new FootballPlayer("Кевин Де Брёйне", 80),
                new FootballPlayer("Мохаммед Салах", 90),
                new FootballPlayer("Роберт Левандовски", 60),
                new FootballPlayer("Неймар", 70),
                new FootballPlayer("Харри Кейн", 100)
        );
        Store market = new Store(players);
        List<FootballClub> clubs = Arrays.asList(
                new FootballClub("Реал Мадрид", 300, market),
                new FootballClub("Барселона", 200, market),
                new FootballClub("Манчестер Сити", 400, market),
                new FootballClub("ПСЖ", 250, market),
                new FootballClub("Бавария", 180, market),
                new FootballClub("Челси", 150, market)
        );

        ExecutorService executor = Executors.newFixedThreadPool(clubs.size());

        System.out.println("Начало торгов\n");
        for (FootballClub club : clubs) {
            executor.execute(club);
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Торги закончены\n");

        printFinalResults(clubs, market);
    }
    private static void printFinalResults(List<FootballClub> clubs, Store market) {
        System.out.println("Итоги\n");

        for (FootballClub club : clubs) {
            System.out.println(club.name + ":");
            System.out.println("Потрачено: " + getSpentBudget(club) + " млн €");
            System.out.println("Остаток: " + club.budget + " млн €");
            System.out.println("Куплено игроков: " + club.squad.size());
            for (FootballPlayer player : club.squad) {
                System.out.println("    - " + player.name + " (" + player.price + " млн €)");
            }
        }

        List<FootballPlayer> availablePlayers = market.getAvailablePlayers();
        System.out.println("\nОстались непроданными:");
        if (availablePlayers.isEmpty()) {
            System.out.println("  Все игроки проданы!");
        } else {
            for (FootballPlayer player : availablePlayers) {
                System.out.println("  - " + player.name + " (" + player.price + " млн €)");
            }
        }
    }

    private static int getSpentBudget(FootballClub club) {
        int spent = 0;
        for (FootballPlayer player : club.squad) {
            spent += player.price;
        }
        return spent;
    }
}