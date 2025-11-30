public class FootballPlayer {
    String name;
    int price;
    boolean isSold;

    public FootballPlayer(String name, int price) {
        this.name = name;
        this.price = price;
        this.isSold = false;
    }

    @Override
    public String toString() {
        return name + " (" + price + " млн €)";
    }
}