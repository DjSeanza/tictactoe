import java.util.Scanner;
 
/**
 * Trieda main, ktorá spúšťa hru.
 * 
 * @author Patrik Ištvanko 
 * @version 1.0.0 
 */
public class HraMain {
    
    /**
     * Hlavná metóda, ktorá spúšťa hru.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Veľkosť hracej plochy: ");
        int velkost = Hra.kontrolaCislo(input);
        while (velkost < 3 || velkost > 9) {
            System.out.println("Musíte zadať číslo väčšie ako 2 a menšie ako 10.");
            System.out.print("Veľkosť hracej plochy: ");
            velkost = Hra.kontrolaCislo(input);
        }
        
        System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
        int pocetPolicokZaSebou = Hra.kontrolaCislo(input);
        while (pocetPolicokZaSebou < 3 || pocetPolicokZaSebou > velkost) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 2 a menšie alebo rovné ako veľkosť plochy.");
            System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
            pocetPolicokZaSebou = Hra.kontrolaCislo(input);
        }
        
        System.out.print("Počet hráčov: ");
        int pocetHracov = Hra.kontrolaCislo(input);
        while (pocetHracov < 2 || pocetHracov > velkost - 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 1 a menšie ako veľkosť plochy zmenšenej o jednu jednotku.");
            System.out.print("Počet hráčov: ");
            pocetHracov = Hra.kontrolaCislo(input);
        }
        
        System.out.print("Na koľko výherných kôl chcete hrať: ");
        int pocetVyhernych = Hra.kontrolaCislo(input);
        while (pocetVyhernych < 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 0.");
            System.out.print("Na koľko výherných kôl chcete hrať: ");
            pocetVyhernych = Hra.kontrolaCislo(input);
        }
        
        Hra hra = new Hra(velkost, pocetPolicokZaSebou, pocetHracov, pocetVyhernych);
        
        hra.menuHry();
    }
}
