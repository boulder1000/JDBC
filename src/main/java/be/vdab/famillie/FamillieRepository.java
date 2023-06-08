package be.vdab.famillie;

import javax.sql.StatementEventListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FamillieRepository extends AbstractRepository{

    public void gezinToevoegen(List<String> persoonList,String NaamMama,String NaamPapa) throws SQLException {

        var sql = """
                insert into personen(voornaam),personen(papaId),personen(mamaID),personen(vermogen))
                values (?,?,?,?)
                """;

        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setString(2, NaamMama);
            statement.setString(3,null);
            statement.setString(4,null);
            statement.setInt(5,900);
            statement.addBatch();
            statement.setString(2, NaamPapa);
            statement.setString(3,null);
            statement.setString(4,null);
            statement.setInt(5,600);
            statement.addBatch();
            statement.executeUpdate();
            var result = statement.getGeneratedKeys();
            result.next();
            var nieuweId = result.getLong(1);
            for (String naam : persoonList) {
                statement.setString(2, naam);
                statement.setLong(3,nieuweId);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        }
    }


}
