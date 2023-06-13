package be.vdab.Bank;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankRepository extends AbstractRepository {
    public void nieuweRekening(Rekening rekening) throws SQLException {
        if (rekeningControleGeldig(rekening.getRekeningNummer()) && rekeninControleBestaat(rekening.getRekeningNummer())) {
            var sql = """
                    insert into bank.rekeningen(nummer, saldo)
                    values (?, ?)
                    for update
                      """;
            try (var connection = super.getConnection();
                 var statement = connection.prepareStatement(sql)) {
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                connection.setAutoCommit(false);
                statement.setString(1, rekening.getRekeningNummer());
                statement.setLong(2, rekening.getSaldo());
                statement.executeQuery();
                connection.commit();

            }
        } else {
            throw new IllegalArgumentException("fout rekeningnummer");
        }
    }

    public boolean rekeninControleBestaat(String rekeningnummer) throws SQLException {
        var sql = """
                select nummer from bank where nummer = ?
                for update
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            var result = statement.executeQuery();
            statement.setString(1, rekeningnummer);
            connection.commit();
            return result.getString("nummer") != "";
        }
    }

    public boolean rekeningControleGeldig(String rekeningnummer) {
        int controleGetal = Integer.parseInt(rekeningnummer.substring(2, 4));
        int controleGetal2 = Integer.parseInt(rekeningnummer.substring(4, 16) + "1114" + controleGetal);
        if (rekeningnummer.length() != 16) {
            return false;
        } else if (!(rekeningnummer.startsWith("BE"))) {
            return false;
        } else if (!(controleGetal >= 2 && controleGetal <= 98)) {
            return false;
        } else if ((controleGetal2 % 97) != 1) {
            return false;
        } else {
            return true;
        }
    }

    public long findByID(String nummer) throws SQLException {
        long saldo;
        var sql = """
                select saldo
                from bank
                where nummer = ?
                for update
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setString(1, nummer);
            var result = statement.executeQuery();
            result.next();
            saldo = result.getLong("nummer");
            connection.commit();
            return saldo;

        }
    }

    public void overschrijven(Rekening vanRekening, Rekening naarRekening, long bedrag) throws SQLException {
        try (var connection = super.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            if (Objects.equals(vanRekening.getRekeningNummer(), naarRekening.getRekeningNummer())) {
                throw new IllegalArgumentException("van en naar zijn hetzelfde");
            }
            if (bedrag <= 0) {
                throw new IllegalArgumentException("bedrag moet groter zijn dan 0");
            }
            if (!rekeninControleBestaat(vanRekening.getRekeningNummer())) {
                throw new IllegalArgumentException("van rekening bestaat niet");
            }
            if (!rekeninControleBestaat(naarRekening.getRekeningNummer())) {
                throw new IllegalArgumentException("naar rekening bestaat niet");
            }
            verhoogSaldo(naarRekening.getRekeningNummer(),connection,bedrag);
            verlaagSaldo(vanRekening.getRekeningNummer(),connection,bedrag);
            connection.commit();
        }

    }

    private void verhoogSaldo(String id, Connection connection, long bedrag)
            throws SQLException {
        var sql = """
                    update bank.rekeningen
                    set saldo = saldo + ?
                    where nummer = ?
                    for update
                    """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, bedrag);
            statement.setString(2, id);
            statement.executeUpdate();
        }
    }
    private void verlaagSaldo(String id, Connection connection, long bedrag)
            throws SQLException {
        var sql = """
                    update bank.rekeningen
                    set saldo = saldo - ?
                    where nummer = ?
                    for update
                    """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, bedrag);
            statement.setString(2, id);
            statement.executeUpdate();
        }
    }

}