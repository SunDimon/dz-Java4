import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Store {
    private List<FootballPlayer> players;
    private ReentrantLock lock;

    public Store(List<FootballPlayer> players) {
        this.players = new ArrayList<>(players);
        this.lock = new ReentrantLock();
    }

    public FootballPlayer buyPlayer(FootballClub club) {
        lock.lock();
        try {
            for (FootballPlayer player : players) {
                if (!player.isSold && player.price <= club.budget) {
                    player.isSold = true;
                    return player;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public List<FootballPlayer> getAvailablePlayers() {
        lock.lock();
        try {
            List<FootballPlayer> available = new ArrayList<>();
            for (FootballPlayer player : players) {
                if (!player.isSold) {
                    available.add(player);
                }
            }
            return available;
        } finally {
            lock.unlock();
        }
    }
}