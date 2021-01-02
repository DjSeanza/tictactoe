import java.util.ArrayList; 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Trieda slúži na prácu s úložnými priestormi pre hru. Určuje kde sa bude hra ukladať a z ktorého súboru sa bude hra načítavať.
 * 
 * @author Patrik Ištvanko 
 * @version 1.0.0
 */
public class UloznePriestory {
    private final int POCET_ULOZNYCH_SLOTOV = 3;
    
    private Scanner input;
    private ArrayList<Hrac> hraci;
    private HraciaPlocha hraciaPlocha;
    
    private int pocetHracov;
    private int pocetVyhernych;
    
    /**
     * Konštruktor nastaví základné atribúty triedy
     */
    public UloznePriestory() {
        this.input = new Scanner(System.in);
        this.hraci = Hra.getHraci();
    }
    
    /**
     * Metóda kontroluje, či používateľ zadal správne číslo úložného priestoru.
     * 
     * @param otazka určuje akú otázku sa chceme opýtať pred výberom úložného súboru
     * @return int vráti číselnú hodnotu úložného priestoru
     */
    private int kontrolaPocetUloznychSuborov(String otazka) {
        System.out.print(otazka + " ");
        int slot = Hra.kontrolaCislo(this.input);
        while (slot < 1 || slot > this.POCET_ULOZNYCH_SLOTOV) {
            System.out.format("Musíte zadať číslo slotu, do ktorého chcete hru uložiť.%n");
            System.out.print(otazka + " ");
            slot = Hra.kontrolaCislo(this.input);
        }
        return slot;
    }
    
    /**
     * Metóda vypisuje údaje, ktoré si načíta zo súboru
     * 
     * @param suborNaCitanie určuje súbor, z ktorého chceme dáta čítať
     */
    private void vypisZoSuboru(String suborNaCitanie) throws IOException {
        File subor = new File("saves/" + suborNaCitanie);
        Scanner scanner = new Scanner(subor);
        
        try {
            while (scanner.hasNextLine()) {
                int novaVelkost = scanner.nextInt();
                int novyPocetPolicokZaSebou = scanner.nextInt();
                int novyPocetHracov = scanner.nextInt();
                int novyPocetVyhernych = scanner.nextInt();
                System.out.format("Počet Hráčov: %s%n", novyPocetHracov);
                System.out.format("Znaky hráčov: ");
                if (scanner.hasNext()) {
                    for (int i = 0; i < novyPocetHracov; i++) {
                        char znak = scanner.next().charAt(0);
                        System.out.format("(%s), ", znak);
                    }
                }
                System.out.println();
                
                System.out.format("Výhry hráčov: ");
                if (scanner.hasNextInt()) {
                    for (int i = 0; i < novyPocetHracov; i++) { 
                        int pocetBodov = scanner.nextInt();
                        System.out.format("(%d), ", pocetBodov);
                    }
                }
                System.out.println(); 
                
                System.out.format("Počet výherných kôl pre výhru: %d%n", novyPocetVyhernych);
                System.out.format("Počet políčok za sebou pre výhru: %d%n", novyPocetPolicokZaSebou);
                System.out.format("Velkosť plochy: %d%n%n", novaVelkost);
                
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.format("Súbor sa nedá prečítať.%n%n");
        }
        
        scanner.close();
    }
    
    /**
     * Metóda ukladá práve prebiehajúcu hru do súboru.
     * 
     * @param suborNaUlozenie názov a prípona súboru do ktorého chceme dáta zapísať
     */
    private boolean ulozDoSuboru(String suborNaUlozenie) throws IOException {
        boolean vyhralHrac = false;
        for (int i = 0; i < Hra.getPocetHracov(); i++) {
            if (this.hraci.get(i).getPocetVyhier() == Hra.getPocetVyhernych()) {
                vyhralHrac = true;
            }
        }
        
        if (vyhralHrac) {
            System.out.format("Hra sa nedá uložiť. Jeden z hráčov už vyhral.%n");
            return false;
        }
        
        File subor = new File("saves/" + suborNaUlozenie);
        PrintWriter writer = new PrintWriter(subor);
        
        writer.print((Hra.getHraciaPlocha().getVelkostPlochy() - 1) + " ");
        writer.print(Hra.getHraciaPlocha().getPocetPolicokZaSebou() + " ");
        writer.print(Hra.getPocetHracov() + " ");
        writer.print(Hra.getPocetVyhernych() + " ");
        
        writer.println();
        for (int i = 0; i < Hra.getPocetHracov(); i++) {
            writer.format("%s ", this.hraci.get(i).getZnak());
        }
        
        writer.println();
        for (int i = 0; i < Hra.getPocetHracov(); i++) {
            writer.format("%s ", this.hraci.get(i).getPocetVyhier());
        }
        
        writer.close();
        return true;
    }
    
    /**
     * Metóda načítava uložené dáta zo súboru.
     * 
     * @param suborNaCitanie určuje súbor, z ktorého chceme dáta čítať
     */
    private boolean nacitajZoSuboru(String suborNaCitanie) throws IOException {
        File subor = new File("saves/" + suborNaCitanie);
        Scanner scanner = new Scanner(subor);
        
        ArrayList<Hrac> zalohaHraci = new ArrayList<Hrac>();
        int staryPocetHracov = this.hraci.size();
        for (int i = 0; i < staryPocetHracov; i++) {
            zalohaHraci.add(this.hraci.get(0));
            this.hraci.remove(0);
        }
        
        try {
            while (scanner.hasNextLine()) {
                int novaVelkost = scanner.nextInt();
                int novyPocetPolicokZaSebou = scanner.nextInt();
                int novyPocetHracov = scanner.nextInt();
                int novyPocetVyhernych = scanner.nextInt();
                
                /*
                 * Preconditions
                 */
                if (novaVelkost < 3 || novaVelkost > 9) {
                    return false;
                }
                if (novyPocetPolicokZaSebou < 3 || novyPocetPolicokZaSebou > novaVelkost) {
                    return false;
                }
                if (novyPocetHracov < 2 || novyPocetHracov > novaVelkost - 1) {
                    return false;
                }
                if (novyPocetVyhernych < 1) {
                    return false;
                }
                
                Hra.setNovaHraciaPlocha(new HraciaPlocha(novaVelkost, novyPocetPolicokZaSebou));
                Hra.setPocetHracov(novyPocetHracov);
                Hra.setPocetVyhernych(novyPocetVyhernych);
                
                if (scanner.hasNext()) {
                    for (int i = 0; i < novyPocetHracov; i++) {
                        char znak = scanner.next().charAt(0);
                        this.hraci.add(new Hrac(znak));
                    }
                }
                
                if (scanner.hasNextInt()) {
                    for (int i = 0; i < novyPocetHracov; i++) { 
                        int pocetBodov = scanner.nextInt();
                        
                        if (pocetBodov < 0) {
                            return false;
                        }
                       
                        for (int j = 0; j < pocetBodov; j++) {
                            this.hraci.get(i).pridajVyhru();
                        }
                    }
                }
                
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.format("Poškodený súbor. Hra sa nedá načítať.%n%n");
            for (int i = 0; i < staryPocetHracov; i++) {
                this.hraci.add(zalohaHraci.get(0));
                zalohaHraci.remove(0);
            }
            return false;
        }
        
        
        scanner.close();
        return true;
    }
    
    /**
     * Metóda pre výber úložného priestoru. V tejto metóde sa vypíšu všetky možné úložné sloty
     * a môžeme si vybrať do ktorého súboru budeme pokrok v hre ukladať alebo z ktorého súboru
     * budeme pokrok v hre načítavať.
     * 
     * @param idemUlozit určuje či budem do súboru ukladať (true) alebo budem zo suboru načítavať (false)
     */
    public boolean vyberSave(boolean idemUlozit) throws IOException {
        boolean podariloSa = false;
        
        if (idemUlozit) {
            for (int i = 1; i <= this.POCET_ULOZNYCH_SLOTOV; i++) {
                System.out.format("Slot %s%n", i);
                this.vypisZoSuboru("save" + i + ".txt");
            }
            
            int slot = this.kontrolaPocetUloznychSuborov("Kde si hru prajete uložiť?");
            
            podariloSa = this.ulozDoSuboru("save" + slot + ".txt");
        } else {
            for (int i = 1; i <= this.POCET_ULOZNYCH_SLOTOV; i++) {
                System.out.format("Slot %s%n", i);
                this.vypisZoSuboru("save" + i + ".txt");
            }
            
            int slot = this.kontrolaPocetUloznychSuborov("Ktorú hru si prajete načítať?");
            
            podariloSa = this.nacitajZoSuboru("save" + slot + ".txt");
        }
        
        return podariloSa;
    }
}
