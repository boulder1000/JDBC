package be.vdab.bieren;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BierRepository bier = new BierRepository();
        try {
            System.out.println(bier.verwijderBieren());
            System.out.println(bier.berekenGemiddelde(bier.gemiddelde()));
            bier.groterDanGemiddelde(bier.gemiddelde()).forEach(System.out::println);
            var scanner = new Scanner(System.in);
            System.out.println("van");
            var van = scanner.next();
            System.out.println("tot");
            var tot = scanner.next();
            bier.vanTot(van, tot).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}