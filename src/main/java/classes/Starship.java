package classes;

public class Starship {
    public int id;
    public String name;
    public String registry;
    public Starship(int _id, String _name, String _registry) {
        id = _id;
        name = _name;
        registry = _registry;
    }

    public void warp() {
        System.out.println("Engage!");
    }
}