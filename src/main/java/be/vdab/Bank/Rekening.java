package be.vdab.Bank;

public class Rekening {
    private final String rekeningNummer;
    private final long saldo;

    public Rekening(String rekeningNummer, long saldo) {
        this.rekeningNummer = rekeningNummer;
        this.saldo = saldo;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public long getSaldo() {
        return saldo;
    }
}
