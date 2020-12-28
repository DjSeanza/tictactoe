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
        int velkost = HraMain.kontrolaCislo(input);
        while (velkost < 3 || velkost > 9) {
            System.out.println("Musíte zadať číslo väčšie ako 2 a menšie ako 10.");
            System.out.println("Veľkosť hracej plochy: ");
            velkost = HraMain.kontrolaCislo(input);
        }
        
        System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
        int pocetPolicokZaSebou = HraMain.kontrolaCislo(input);
        while (pocetPolicokZaSebou < 3 || pocetPolicokZaSebou > velkost) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 2 a menšie alebo rovné ako veľkosť plochy.");
            System.out.println("Koľko políčok za sebou musí byť v rade pre výhru: ");
            pocetPolicokZaSebou = HraMain.kontrolaCislo(input);
        }
        
        System.out.print("Počet hráčov: ");
        int pocetHracov = HraMain.kontrolaCislo(input);
        while (pocetHracov < 2 || pocetHracov > velkost - 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 1 a menšie ako veľkosť plochy zmenšenej o jednu jednotku.");
            System.out.println("Počet hráčov: ");
            pocetHracov = HraMain.kontrolaCislo(input);
        }
        
        System.out.print("Na koľko výherných kôl chcete hrať: ");
        int pocetVyhernych = HraMain.kontrolaCislo(input);
        while (pocetVyhernych < 1 || pocetVyhernych > velkost) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 0 a menšie alebo rovné ako veľkosť plochy.");
            System.out.println("Na koľko výherných kôl chcete hrať: ");
            pocetVyhernych = HraMain.kontrolaCislo(input);
        }
        
        Hra hra = new Hra(velkost, pocetPolicokZaSebou, pocetHracov, pocetVyhernych);
        
        hra.menuHry();
    }
    
    /**
     * Metóda slúži na kontrolu zadaného inputu. Ak je zadaný input číslo, tak
     * toto číslo nám vráti
     * 
     * @param input berie input, ktorý hráč zadal
     * @return int je číslo, ktoré berieme z inputu používateľa
     */
    private static int kontrolaCislo(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Musíte zadať číslo.");
            input.next();
        }
        return input.nextInt();
    }
}
