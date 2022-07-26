package database;
import classes.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class OfficerDatabase extends Database {
    public OfficerDatabase() {
        super();
    }

    public ArrayList<Officer> getAllOfficers() {
        try {
            ArrayList<Officer> officers = new ArrayList<Officer>();
            String sql = "SELECT * FROM officer";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int rank = rs.getInt("officer_rank");
                Officer officer = new Officer(id, name, rank);
                officers.add(officer);
            }
            return officers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Officer getOfficer(int id) {
        try {
            String sql = "SELECT * FROM officer WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int resultId = rs.getInt("id");
                String name = rs.getString("name");
                int rank = rs.getInt("officer_rank");
                return new Officer(resultId, name, rank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Officer createOfficer(String name, int rank) {
        try {
            String sql = "INSERT INTO officer (name, officer_rank) VALUES (?, ?)";
            PreparedStatement insert = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, name);
            insert.setInt(2, rank);
            boolean rowInserted = insert.executeUpdate() > 0;
            if (rowInserted) {
                // Get the id of the created ship and return that
                ResultSet generatedKeys = insert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = (int) generatedKeys.getLong(1);
                    return getOfficer(id);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Officer updateOfficer(Officer officer) {
        try {
            String sql = "UPDATE officer SET name = ?, officer_rank = ? WHERE id = ?";
            PreparedStatement update = con.prepareStatement(sql);
            update.setString(1, officer.name);
            update.setInt(2, officer.rank);
            update.setInt(3, officer.id);
            boolean rowInserted = update.executeUpdate() > 0;
            if (rowInserted) {
                return getOfficer(officer.id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOfficer(Officer officer) {
        try {
            String sql = "DELETE FROM officer WHERE id = ?";
            PreparedStatement delete = con.prepareStatement(sql);
            delete.setInt(1, officer.id);
            return delete.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

