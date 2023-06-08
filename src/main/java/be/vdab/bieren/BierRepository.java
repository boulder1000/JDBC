package be.vdab.bieren;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BierRepository extends AbstractRepository {
    public int verwijderBieren() throws SQLException {
        var sql = """ 
                delete from bieren.bieren
                where alcohol is null
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        }
    }

    public ArrayList<Brouwer> gemiddelde() throws SQLException {
        var brouwers = new ArrayList<Brouwer>();
        var sql = """ 
                select id,naam,omzet from bieren.brouwers
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }

    }
    private Brouwer naarBrouwer (ResultSet result) throws SQLException {
        return new Brouwer(result.getInt("id"),result.getInt("omzet"),result.getString("naam"));
    }
public double berekenGemiddelde(List<Brouwer> list){
        double gemiddelde=0;
    for (var omzet:list) {
        gemiddelde += omzet.getOmzet();
    }
    return gemiddelde/list.size();
}
public List<Brouwer> groterDanGemiddelde(List<Brouwer> list){
        List<Brouwer> output = new ArrayList<>();
    for (var groter:list) {
        if (groter.getOmzet() > berekenGemiddelde(list)){
            output.add(groter);
        }
    }
    return output;
}
public List<Brouwer> vanTot(String van,String tot){

    var brouwers = new ArrayList<Brouwer>();
    var sql = """ 
                select id,naam,omzet from bieren.brouwers where omzet >= ? and omzet <= ? 
                """;
    try (var connection = super.getConnection();
         var statement = connection.prepareStatement(sql)) {
        statement.setString(1,van);
        statement.setString(2,tot);
        for (var result = statement.executeQuery(); result.next(); ) {
            brouwers.add(naarBrouwer(result));
        }
        return brouwers;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
