import java.util.ArrayList; 
import java.util.Scanner;
 
/**
 * Táto trieda slúži na základnú prácu s hrou. Vytvára inštanciu hry, určuje výhry, 
 * pričítava body hráčom a obsahuje základné nastavenia hry.
 * 
 * @author Patrik Ištvanko
 * @version 1.0.0
 */
public class Hra {
    private ArrayList<Hrac> hraci;
    private ArrayList<Hrac> vyherci;
    private HraciaPlocha hraciaPlocha;
    private int pocetHracov;
    private int pocetVyhernych;
    private int pocetPolicokZaSebou;
    private boolean koniecHry;
    private Hrac vyhercaKola;
    
    private Scanner input = new Scanner(System.in);
    
    /**
     * Inicializujeme si základné hodnoty atribútov,
     * vytvoríme hraciu plochu a následne sa hráčov opýtame
     * aké znaky chcú používať pri hre (berie sa vždy len 
     * prvý znak, ktorý zadajú).
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     * @param pocetPolicokZaSebou počet políčok, ktoré musia obsahovať znak hráča
     * a následovať hneď za sebou pre výhru
     * @param pocetHracov počet hráčov
     * @param pocetVyhernych určuje na koľko výherných bodov sa hrá
     * 
     * @TODO - pridať private metódy, napríklad na menu a takto
     */
    public Hra(int velkost, int pocetPolicokZaSebou, int pocetHracov, int pocetVyhernych) { 
        if (pocetHracov < 2 || pocetHracov > velkost - 1) {
            this.pocetHracov = 2;
        } else {
            this.pocetHracov = pocetHracov;
        }
        
        if (pocetVyhernych < 0) {
            this.pocetVyhernych = 3;
        } else {
            this.pocetVyhernych = pocetVyhernych;
        }
        
        this.pocetPolicokZaSebou = pocetPolicokZaSebou;
        
        this.hraci = new ArrayList<Hrac>();
        this.vyherci = new ArrayList<Hrac>();
        
        this.hraciaPlocha = new HraciaPlocha(velkost, pocetPolicokZaSebou);
        this.hraciaPlocha.setPolicka();        
        
        for (int i = 0; i < this.pocetHracov; i++) {
            boolean jePridany = this.pridajHraca(true);
            while (!jePridany) {
                jePridany = this.pridajHraca(true);
            }
        }
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
    
    /**
     * Metóda pridáva hráčov do hry.
     * 
     * @param jeVKonstruktore určuje, či sa pridávanie hráčov nachádza v konštruktore
     * @return boolean vráti hodnotu true ak sa hráč úspešne pridal
     */
    public boolean pridajHraca(boolean jeVKonstruktore) {
        /* 
         * this.hraciaPlocha.getVelkostPlochy() - 2
         * pretože do veľkosti plochy sa rátajú aj stĺpce a riadky s číslami
         * a hráčov má byť aspoň o jedného menej ako je veľkosť plochy.
        */
        if (this.hraci.size() >= this.hraciaPlocha.getVelkostPlochy() - 2) {
            System.out.println("Príliš veľa hráčov, nedá sa pridať viac.");
            return false;
        }
        
        System.out.print("Znak hráča " + (this.hraci.size() + 1) + ": ");
        char znak = this.input.next().charAt(0);
        boolean jeZnakRovnaky = false;
        
        for (int i = 0; i < this.hraci.size(); i++) {
            if (znak == this.hraci.get(i).getZnak()) {
                System.out.println("Takýto znak má už iný hráč. Zadajte nový.");
                jeZnakRovnaky = true;
            }
        }
        
        if (!jeZnakRovnaky) {
            if (!jeVKonstruktore) {
                this.pocetHracov++;
            }
            return this.hraci.add(new Hrac(znak));
        }
        
        return false;
    }
    
    /**
     * Metóda odstraňuje hráčov z hry.
     * 
     * @return boolean vracia hodnotu true ak sa podarí odstrániť hráča
     */
    public boolean odstranHraca() {
        if (this.pocetHracov <= 2) {
            System.out.println("Musia byť minimálne dvaja hráči.");
            return false;
        }
        
        System.out.println("Znak hráča, ktorého chcete odstrániť: ");
        char znak = this.input.next().charAt(0);
        boolean jeOdstraneny = false;
        
        for (int i = 0; i < this.hraci.size(); i++) {
            if (znak == this.hraci.get(i).getZnak()) {
                this.hraci.remove(i);
                this.pocetHracov--;
                System.out.println("Hráč odstránený.");
                jeOdstraneny = true;
            }
        }
        
        if (!jeOdstraneny) {
            System.out.println("Neznámy znak. Hráča sa nepodarilo odstrániť.");
        }
        return jeOdstraneny;
    }
    
    /**
     * Opýtame sa hráča na riadok a stĺpec, do ktorého chce znak
     * napísať a následne ho tam napíšeme pomocou metódy setPolicko()
     * v triede HraciaPlocha. Metóda sa opakuje dokým sa nezadá 
     * korektné číslo riadku a stĺpca.
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme nastaviť 
     * políčko na hodnotu znaku, ktorý si zadal
     */
    public void setPolickoPreHraca(int hrac) {
        System.out.println("Kolo hráča " + (hrac + 1) + " (" + this.hraci.get(hrac).getZnak() + ").");
        
        /* 
         * kontroluje či je zadané číslo a či je zadané správne
         * riadok musí byť definovaný takto v podmienke, aby sa podmienka nezacyklila
         * takto sa stále bude pýtať na nové číslo a bude ho kontrolovať
         */
        System.out.println("Riadok: ");
        int riadok = Hra.kontrolaCislo(this.input);
        while (riadok < 1 || riadok > this.hraciaPlocha.getVelkostPlochy() - 1) {
            System.out.println("Musíte zadať číslo, ktoré bude väčšie ako 0\na zároveň nebude väčšie ako je veľkosť hracej plochy.");
            System.out.println("Riadok: ");
            riadok = Hra.kontrolaCislo(this.input);
        }
        
        /* 
         * kontroluje či je zadané číslo a či je zadané správne
         * stĺpec musí byť definovaný takto v podmienke, aby sa podmienka nezacyklila
         * takto sa stále bude pýtať na nové číslo a bude ho kontrolovať
         */
        System.out.println("Stĺpec: ");
        int stlpec = Hra.kontrolaCislo(this.input);
        while (stlpec < 1 || stlpec > this.hraciaPlocha.getVelkostPlochy() - 1) {
            System.out.println("Musíte zadať číslo, ktoré bude väčšie ako 0\na zároveň nebude väčšie ako je veľkosť hracej plochy.");
            System.out.println("Stĺpec: ");
            stlpec = Hra.kontrolaCislo(this.input);
        }
        
        this.hraciaPlocha.setPolicko(riadok, stlpec, this.hraci.get(hrac));
        this.hraciaPlocha.vypisPlochu();
        
        /*
         * ak nebude políčko zadané správne (t.j. ak už sa na danom mieste niečo nachádza
         * alebo je zle zadaný stĺpec alebo riadok), tak sa vykoná táto metóda znovu.
         */
        if (!this.hraciaPlocha.jePolickoZadaneSpravne()) {
            this.setPolickoPreHraca(hrac);
        }
    }
    
    /**
     * Po každom kole kontroluje riadky a stĺpce, či nevyhral niektorí z hráčov.
     * 
     * @TODO - fujky metóda, treba ju upraviť
     */
    public void vyhraKola() {
        for (Hrac aktualny: this.hraci) {
            for (int i = 0; i < this.hraciaPlocha.getVelkostPlochy(); i++) {
                if (!this.koniecHry) {
                    // true značí, že ide o kontrolu riadku
                    this.koniecHry = this.hraciaPlocha.vyhraRiadokStlpec(aktualny, i, i, true);
                    this.vyhercaKola = aktualny;
                }
                if (!this.koniecHry) {
                    // false značí, že ide o kontrolu stĺpca
                    this.koniecHry = this.hraciaPlocha.vyhraRiadokStlpec(aktualny, i, i, false);
                    this.vyhercaKola = aktualny;
                }
                if (!this.koniecHry) {
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, 0, i, true);
                    this.vyhercaKola = aktualny;
                }
                if (!this.koniecHry) {
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, i, 0, true);
                    this.vyhercaKola = aktualny;
                }
                if (!this.koniecHry) {
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, 0, i, false);
                    this.vyhercaKola = aktualny;
                }
                if (!this.koniecHry) {
                    /* 
                     * this.hraciaPlocha.getVelkostPlochy() - 1 
                     * je to kvôli tomu, aby sa kontrolovalo stále od posledného stĺpca
                     */
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, i, this.hraciaPlocha.getVelkostPlochy() - 1, false);
                    this.vyhercaKola = aktualny;
                }
            }
        }
    }
    
    /**
     * Metóda určuje, či niekto vyhral a ak áno tak kto konkrétne vyhral.
     */
    public void vyhra() {
        for (int j = 0; j < this.vyherci.size(); j++) {
            this.vyherci.remove(0);
        }
        
        for (Hrac aktualny: this.hraci) {
            if (aktualny.getPocetVyhier() == this.pocetVyhernych) {
                this.vyherci.add(aktualny);
                this.vypisVyhry();
                this.koniecHry = true;
                this.vyhercaKola = aktualny;
                this.menuHry();
            }
        }
        
    }
    
    /**
     * Metóda nastaví koniec hry na true a výhercu na null.
     */
    public void remiza() {
        if (this.hraciaPlocha.jeZaplnena()) {
            this.koniecHry = true;
            this.vyhercaKola = null;
        }
    }
    
    /**
     * Vypíše kto vyhrál a taktiež body všetkých hráčov.
     */
    public void vypisVyhry() {
        if (this.vyhercaKola == null && this.vyherci.size() == 0) {
            System.out.format("Je to remíza.%n");
            System.out.println("------------------------------------");
            
            for (int i = 0; i < this.pocetHracov; i++) {
                System.out.format("Hráč so znakom %s: %d%n", this.hraci.get(i).getZnak(), this.hraci.get(i).getPocetVyhier());
            }
            
            System.out.println("------------------------------------");
        } else {
            
            if (this.vyhercaKola != null) {
                this.vyhercaKola.pridajVyhru();
                System.out.println("------------------------------------");
                System.out.format("Kolo vyhral hráč so znakom %s.%n", this.vyhercaKola.getZnak());
            }
            
            for (int i = 0; i < this.pocetHracov; i++) {
                System.out.format("Hráč so znakom %s: %d%n", this.hraci.get(i).getZnak(), this.hraci.get(i).getPocetVyhier());
            }
            
            System.out.println("------------------------------------");
        }
            
        if (this.vyherci.size() != 0) {
            System.out.print("Hru vyhral hráč/hráči so znakom/znakmi: ");
            for (int i = 0; i < this.vyherci.size(); i++) {
                System.out.format("%s", this.vyherci.get(i).getZnak());
                if (this.vyherci.size() > 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            System.out.println("------------------------------------");
        }
    }
    
    /**
     * Metóda ukončí hru a vypíše konečného výhercu podľa počtu vyhratých kôl.
     */
    public void ukoncitHru() {
        this.vyhercaKola = null;
        for (int i = 0; i < this.vyherci.size(); i++) {
            this.vyherci.remove(0);
        }
        
        Hrac vyherca = this.hraci.get(0);
        for (int i = 0; i < this.pocetHracov; i++) {
            
            if (vyherca.getPocetVyhier() == this.hraci.get(i).getPocetVyhier()) {
                this.vyherci.add(this.hraci.get(i));
            }
            
            if (vyherca.getPocetVyhier() < this.hraci.get(i).getPocetVyhier()) {
                vyherca = this.hraci.get(i);
                for (int j = 0; j < this.vyherci.size(); j++) {
                    this.vyherci.remove(0);
                }
                this.vyherci.add(vyherca);
            }
            
        }
    }
    
    /**
     * Vypíše základné menu hry.
     * (Nová hra, Pokračovať v uloženej hre, Nastavenia, Ukončiť hru)
     */
    public void menuHry() {
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Nová hra (n)\nPokračovať v uloženej hre (c)\nNastavenia (o)\nUkončiť hru (e)\n");
        char znak = this.input.next().charAt(0);
        
        switch (znak) {
            case 'n':
                for (int i = 0; i < this.pocetHracov; i++) {
                    this.hraci.get(i).resetujVyhry();
                }
                this.zacniHru();
                break;
            case 'c':
                /**
                 * @TODO - načítanie zo súboru
                 */
                break;
            case 'o':
                this.nastaveniaHry();
                break;
            case 'e':
                System.exit(0);
                break;
            default:
                this.menuHry();
                break;
        }
    }
    
    /**
     * Obsahuje základné nastavenia hry
     * (Pridať hráča, Odstrániť hráča, Zmeniť počet výherných
     * 
     * @TODO - pridať možnosť toho aby používateľ videl aké sú konkrétne nastavenia
     */
    public void nastaveniaHry() {
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Pridať hráča (a)\nOdstrániť hráča (d)\nZmeniť počet výherných (p)\nZmeniť počet políčok za sebou (s)\nZmeniť veľkosť plochy (v)\nSpäť (b)\n");
        char znak = this.input.next().charAt(0);
        
        switch (znak) {
            case 'a':
                this.pridajHraca(false);
                this.nastaveniaHry();
                break;
            case 'd':
                this.odstranHraca();
                this.nastaveniaHry();
                break;
            case 'p':
                boolean jeZadaneSpravne = false;
                do {
                    System.out.print("Zadajte číslo väčšie ako 0: ");
                    int novyPocetVyhernych = this.input.nextInt();
                    if (novyPocetVyhernych > 0) {
                        this.pocetVyhernych = novyPocetVyhernych;
                        jeZadaneSpravne = true;
                    }
                } while (!jeZadaneSpravne);
                this.nastaveniaHry();
                break;
            case 's':
                System.out.print("Zadajte číslo od 3 po veľkosť hracej plochy: ");
                int novyPocetPolicokZaSebou = this.input.nextInt();
                this.pocetPolicokZaSebou = novyPocetPolicokZaSebou;
                this.hraciaPlocha = new HraciaPlocha(this.hraciaPlocha.getVelkostPlochy() - 1, this.pocetPolicokZaSebou);
                this.hraciaPlocha.setPolicka();
                this.nastaveniaHry();
                break;
            case 'v':
                System.out.print("Zadajte číslo od 3 do 13: ");
                int velkostPlochy = this.input.nextInt();
                this.hraciaPlocha = new HraciaPlocha(velkostPlochy, this.pocetPolicokZaSebou);
                this.hraciaPlocha.setPolicka();
                this.nastaveniaHry();
                break;
            case 'b':
                this.menuHry();
                break;
            default:
                this.nastaveniaHry();
                break;
        }
    }
    
    /**
     * Po každom kole vypíše možnosti, z ktorých si používateľ vyberie jednu.
     * (Pokračovať, Pridať hráča, Odstrániť hráča, Uložiť, Ukončiť)
     */
    public void moznostiHry() {
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Pokračovať (c)\nUložiť (s)\nVrátiť sa do menu (m)\nUkončiť (e)\n");
        char znak = this.input.next().charAt(0);
        
        switch (znak) {
            case 'c':
                this.zacniHru();
                break;
            case 's':
                /**
                 * @TODO uložiť do súboru
                 */
                break;
            case 'm':
                this.menuHry();
                break;
            case 'e':
                this.ukoncitHru();
                this.vypisVyhry();
                System.exit(0);
                break;
            default:
                System.out.println("Niečo ste zadali zle.");
                this.moznostiHry();
        }
    }
    
    /**
     * Základná metóda, ktorá spúšťa celú hru. Hrá sa dokým sa atribút
     * koniecHry nerovná true. Po tom ako hráč vyhrá, vypíše sa kto
     * vyhral a hra sa ukončí.
     * 
     * @TODO - aby kolá šli náhodne, nie aby vždy začínal prvý hráč. Každé kolo nech sa dá shuffle a nech sa ide podľa toho
     */
    public void zacniHru() {
        this.hraciaPlocha.setPolicka();
        this.hraciaPlocha.vypisPlochu();
        this.koniecHry = false;
        this.vyhercaKola = null;
        
        // vymazanie výhercov
        for (int i = 0; i < this.vyherci.size(); i++) {
            this.vyherci.remove(0);
        }
        
        while (!this.koniecHry) {
            for (int i = 0; i < this.pocetHracov; i++) {
                this.vyhra();
                
                if (!this.koniecHry) {
                    this.setPolickoPreHraca(i);
                }
                this.vyhraKola();
                this.remiza();
            }
        }
        
        this.vypisVyhry();
        this.moznostiHry();
    }
}
