package database;
import classes.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RankDatabase extends Database {
    public RankDatabase() {
        super();
    }

    public ArrayList<Rank> getAllRanks() {
        try {
            ArrayList<Rank> ranks = new ArrayList<Rank>();
            String sql = "SELECT * FROM officer_rank";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Rank rank = new Rank(id, name);
                ranks.add(rank);
            }
            return ranks;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Rank getRank(int id) {
        try {
            String sql = "SELECT * FROM officer_rank WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int resultId = rs.getInt("id");
                String name = rs.getString("name");
                return new Rank(resultId, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
