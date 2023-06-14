package be.vdab.Bank;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class BankRepository extends AbstractRepository {
    public void nieuweRekening(Rekening rekening) throws SQLException {
        if (rekeningControleGeldig(rekening.getRekeningNummer()) && rekeninControleBestaat(rekening.getRekeningNummer())) {
            var sql = """
                    insert into bank.rekeningen(nummer, saldo)
                    values (?, ?)
                      """;
            try (var connection = super.getConnection();
                 var statement = connection.prepareStatement(sql)) {
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                connection.setAutoCommit(false);
                statement.setString(1, rekening.getRekeningNummer());
                statement.setBigDecimal(2, rekening.getSaldo());
                statement.executeUpdate();
                connection.commit();

            }
        } else {
            throw new IllegalArgumentException("fout rekeningnummer");
        }
    }

    public boolean rekeninControleBestaat(String rekeningnummer) throws SQLException {
        boolean bool = true;
        var sql = """
                select nummer from bank.rekeningen where nummer = ?
                for update
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setString(1, rekeningnummer);
            var result = statement.executeQuery();
            result.next() ;
            if (result.next()){
                bool = false;
            }


            connection.commit();
            return bool;

        }
    }

    public boolean rekeningControleGeldig(String rekeningnummer) {
        int controleGetal = Integer.parseInt(rekeningnummer.substring(2, 4));
        long controleGetal2 = Long.parseLong(rekeningnummer.substring(4, 16) + "1114" + controleGetal);
        if (rekeningnummer.length() != 16) {
            return false;
        } else if (!(rekeningnummer.startsWith("BE"))) {
            return false;
        } else if (!(controleGetal >= 2 && controleGetal <= 98)) {
            return false;
        } else return (controleGetal2 % 97) == 1;
    }

    public BigDecimal findByID(String nummer) throws SQLException {
        BigDecimal saldo;
        var sql = """
                select saldo
                from bank.rekeningen
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
            saldo = result.getBigDecimal("saldo");
            connection.commit();
            return saldo;

        }
    }

    public void overschrijven(String vanRekening, String naarRekening, BigDecimal bedrag) throws SQLException {
        try (var connection = super.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            if (Objects.equals(vanRekening, naarRekening)) {
                throw new IllegalArgumentException("van en naar zijn hetzelfde");
            }
            if (bedrag.signum() <= 0) {
                throw new IllegalArgumentException("bedrag moet groter zijn dan 0");
            }
            if (!rekeninControleBestaat(vanRekening)) {
                throw new IllegalArgumentException("van rekening bestaat niet");
            }
            if (!rekeninControleBestaat(naarRekening)) {
                throw new IllegalArgumentException("naar rekening bestaat niet");
            }
            verhoogSaldo(naarRekening,connection,bedrag);
            verlaagSaldo(vanRekening,connection,bedrag);
            connection.commit();
        }

    }

    private void verhoogSaldo(String id, Connection connection, BigDecimal bedrag)
            throws SQLException {
        var sql = """
                    update bank.rekeningen
                    set saldo = saldo + ?
                    where nummer = ?
                    """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, bedrag);
            statement.setString(2, id);
            statement.executeUpdate();
        }
    }
    private void verlaagSaldo(String id, Connection connection, BigDecimal bedrag)
            throws SQLException {
        var sql = """
                    update bank.rekeningen
                    set saldo = saldo - ?
                    where nummer = ?
                    """;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, bedrag);
            statement.setString(2, id);
            statement.executeUpdate();
        }
    }

}