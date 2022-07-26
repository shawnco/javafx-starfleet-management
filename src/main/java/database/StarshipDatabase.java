package database;
import classes.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class StarshipDatabase extends Database {
    public StarshipDatabase() {
        super();
    }

    public ArrayList<Starship> getAllStarships() {
        try {
            ArrayList<Starship> starships = new ArrayList<Starship>();
            String sql = "SELECT * FROM starships";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String registry = rs.getString("registry");
                Starship ship = new Starship(id, name, registry);
                starships.add(ship);
            }
            return starships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Starship getStarship(int id) {
        try {
            int resultId;
            String name;
            String registry;

            String sql = "SELECT * FROM starships WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                resultId = rs.getInt("id");
                name = rs.getString("name");
                registry = rs.getString("registry");
                return new Starship(resultId, name, registry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Starship createStarship(String name, String registry) {
        try {
            String sql = "INSERT INTO starships (name, registry) VALUES (?, ?)";
            PreparedStatement insert = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, name);
            insert.setString(2, registry);
            boolean rowInserted = insert.executeUpdate() > 0;
            if (rowInserted) {
                // Get the id of the created ship and return that
                ResultSet generatedKeys = insert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = (int) generatedKeys.getLong(1);
                    return getStarship(id);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Starship updateStarship(Starship ship) {
        try {
            String sql = "UPDATE starships SET name = ?, registry = ? WHERE id = ?";
            PreparedStatement update = con.prepareStatement(sql);
            update.setString(1, ship.name);
            update.setString(2, ship.registry);
            update.setInt(3, ship.id);
            boolean rowInserted = update.executeUpdate() > 0;
            if (rowInserted) {
                return getStarship(ship.id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteStarship(Starship ship) {
        try {
            String sql = "DELETE FROM starships WHERE id = ?";
            PreparedStatement delete = con.prepareStatement(sql);
            delete.setInt(1, ship.id);
            return delete.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
