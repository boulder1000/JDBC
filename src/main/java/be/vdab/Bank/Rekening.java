package be.vdab.Bank;

import java.math.BigDecimal;

public class Rekening {
    private final String rekeningNummer;
    private final BigDecimal saldo;

    public Rekening(String rekeningNummer, BigDecimal saldo) {
        this.rekeningNummer = rekeningNummer;
        this.saldo = saldo;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
