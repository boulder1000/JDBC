package be.vdab.Bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var bankrepository = new BankRepository();
        var rekening1 = new Rekening("BE72891012240116", 200);

        System.out.println("1. nieuwe rekening");
        System.out.println("2. Saldo consulteren");
        System.out.println("3. Overschrijven");
        System.out.println("0. Om te stoppen");
        var scanner = new Scanner(System.in);
        while (true) {
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("geef rekeningnummer in");
                    try {
                        bankrepository.nieuweRekening(new Rekening(scanner.next(), 0));
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
                    long bedrag = Long.parseLong(scanner.next());
                    try {
                        bankrepository.overschrijven(van, naar, bedrag);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                case 0 -> {
                    System.exit(0);
                    try {

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }}
