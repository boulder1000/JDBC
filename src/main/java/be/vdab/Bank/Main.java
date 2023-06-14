package be.vdab.Bank;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var bankrepository = new BankRepository();
        var scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. nieuwe rekening");
            System.out.println("2. Saldo consulteren");
            System.out.println("3. Overschrijven");
            System.out.println("0. Om te stoppen");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("geef rekeningnummer in");
                    try {
                        bankrepository.nieuweRekening(new Rekening(scanner.next(), BigDecimal.ZERO));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                case 2 -> {
                    System.out.println("geef rekeningnummer in");
                    try {
                        System.out.println(bankrepository.findByID(scanner.next()));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                case 3 -> {
                    System.out.println("van rekening");
                    String van = scanner.next();
                    System.out.println("naar rekening");
                    String naar = scanner.next();
                    System.out.println("bedrag");
                    BigDecimal bedrag = scanner.nextBigDecimal();
                    try {
                        bankrepository.overschrijven(van, naar, bedrag);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                case 0 -> System.exit(0);
            }
        }
    }
}