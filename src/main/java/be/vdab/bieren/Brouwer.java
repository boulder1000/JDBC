package be.vdab.bieren;

public class Brouwer {
    private int id,omzet;
    private String adres,naam,postcode,gemeente;

    public Brouwer(int id, int omzet, String adres, String naam, String postcode, String gemeente) {
        this.id = id;
        this.omzet = omzet;
        this.adres = adres;
        this.naam = naam;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }

    public Brouwer(int id,int omzet, String naam) {
        this.omzet = omzet;
        this.naam = naam;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Brouwer{" +
                "id=" + id +
                ", omzet=" + omzet +
                ", adres='" + adres + '\'' +
                ", naam='" + naam + '\'' +
                ", postcode='" + postcode + '\'' +
                ", gemeente='" + gemeente + '\'' +
                '}';
    }

    public int getOmzet() {
        return omzet;
    }
}
