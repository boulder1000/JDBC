package be.vdab.famillie;

public class Persoon {
    private int id,papaId,mamaId;
    private double decimal;
    private String voornaam;

    public Persoon(int id, int papaId, int mamaId, double decimal, String voornaam) {
        this.id = id;
        this.papaId = papaId;
        this.mamaId = mamaId;
        this.decimal = decimal;
        this.voornaam = voornaam;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Persoon{" +
                "id=" + id +
                ", papaId=" + papaId +
                ", mamaId=" + mamaId +
                ", decimal=" + decimal +
                ", voornaam='" + voornaam + '\'' +
                '}';
    }
}
