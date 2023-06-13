package be.vdab.Bank;

public class Main {
    public static void main(String[] args) {
       var bankrepository = new BankRepository();
        var rekening1 = new Rekening("BE72891012240116",200);

try{
bankrepository.nieuweRekening(rekening1);
    }
catch (Exception ex){
    System.out.println(ex);
}
}
}
