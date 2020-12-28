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
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int velkost;
        int pocetPolicokZaSebou;
        int pocetHracov;
        int pocetVyhernych;
        
        System.out.print("Veľkosť hracej plochy: ");
        while ((velkost = HraMain.kontrolaCislo(input)) < 3 || velkost > 9) {
            System.out.println("Musíte zadať číslo väčšie ako 2 a menšie ako 10.");
            System.out.println("Veľkosť hracej plochy: ");
        }
        
        System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
        while ((pocetPolicokZaSebou = HraMain.kontrolaCislo(input)) < 3 || pocetPolicokZaSebou > velkost) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 2 a menšie alebo rovné ako veľkosť plochy.");
            System.out.println("Koľko políčok za sebou musí byť v rade pre výhru: ");
        }
        
        System.out.print("Počet hráčov: ");
        while ((pocetHracov = HraMain.kontrolaCislo(input)) < 2 || pocetHracov > velkost - 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 1 a menšie ako veľkosť plochy zmenšenej o jednu jednotku.");
            System.out.println("Počet hráčov: ");
        }
        
        System.out.print("Na koľko výherných kôl chcete hrať: ");
        while ((pocetVyhernych = HraMain.kontrolaCislo(input)) < 3 || pocetVyhernych > velkost) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 2 a menšie alebo rovné ako veľkosť plochy.");
            System.out.println("Na koľko výherných kôl chcete hrať: ");
        }
        
        Hra hra = new Hra(velkost, pocetPolicokZaSebou, 2, 3);
        
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
