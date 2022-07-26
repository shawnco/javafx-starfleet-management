package classes;

public class Rank {
    public int id;
    public String name;

    public Rank(int _id, String _name) {
        id = _id;
        name = _name;
    }

    @Override
    public String toString() {
        return name;
    }
}
