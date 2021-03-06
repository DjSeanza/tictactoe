import java.util.ArrayList; 
import java.io.IOException;
import java.util.Random; 
import java.util.Scanner; 

/**
* Táto trieda slúži na základnú prácu s hrou. Vytvára inštanciu hry, určuje výhry, 
* pričítava body hráčom a obsahuje základné nastavenia hry.
* 
* @author Patrik Ištvanko
* @version 1.0.0
*/
public class Hra {
    private static Hra instanciaHry = null;
    
    private ArrayList<Hrac> hraci;
    private ArrayList<Hrac> vyherci;
    
    private HraciaPlocha hraciaPlocha;
    private Hrac vyhercaKola;
    private Scanner input;
    
    private int pocetHracov;
    private int pocetVyhernych;
    private boolean koniecHry;
    
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
     * @TODO - urobiť bota
     * @TODO - skontrolovať kontroly v konštruktore
     */
    private Hra(int velkost, int pocetPolicokZaSebou, int pocetHracov, int pocetVyhernych) {
        if (pocetHracov < 1 || pocetHracov > velkost - 1) {
            this.pocetHracov = 1;
        } else {
            this.pocetHracov = pocetHracov;
        }

        if (pocetVyhernych < 0) {
            this.pocetVyhernych = 3;
        } else {
            this.pocetVyhernych = pocetVyhernych;
        }
        
        this.input = new Scanner(System.in);
        this.pocetHracov = pocetHracov;
        this.pocetVyhernych = pocetVyhernych;
    
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
        
        if (this.hraci.size() == 1) {
            
            if (this.hraci.get(0).getZnak() == 'x') {
                this.hraci.add(new Hrac('o', true));
            } else {
                this.hraci.add(new Hrac('x', true));
            }
            
            this.pocetHracov++;
        }
    }
    
    /**
     * Metóda pre získanie inštancie hry. Ak ešte žiadna nie je, tak sa vytvorí nová.
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     * @param pocetPolicokZaSebou počet políčok, ktoré musia obsahovať znak hráča
     * a následovať hneď za sebou pre výhru
     * @param pocetHracov počet hráčov
     * @param pocetVyhernych určuje na koľko výherných bodov sa hrá
     * 
     * @return Hra vracia inštanciu hry
     */
    public static Hra zacniHru(int velkost, int pocetPolicokZaSebou, int pocetHracov, int pocetVyhernych) {
        if (Hra.instanciaHry == null) {
            Hra.instanciaHry = new Hra(velkost, pocetPolicokZaSebou, pocetHracov, pocetVyhernych);
        }
        return Hra.instanciaHry;
    }
    
    /**
     * Metóda vráti inštanciu hry. Ak žiadna nie je vráti null.
     * 
     * @return Hra vráti inštanciu hry
     */
    public static Hra getInstancia() {
        return Hra.instanciaHry;
    }
    
    /**
     * Metóda slúži na kontrolu zadaného inputu. Ak je zadaný input číslo, tak
     * toto číslo nám vráti
     * 
     * @param input berie input, ktorý hráč zadal
     * @return int je číslo, ktoré berieme z inputu používateľa
     */
    public static int kontrolaCislo(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Musíte zadať číslo.");
            input.next();
        }
        return input.nextInt();
    }
    
    /**
     * @return int vráti počet hráčov.
     */
    public int getPocetHracov() {
        return this.pocetHracov;
    }
    
    /**
     * @return int vráti počet výherných kôl
     */
    public int getPocetVyhernych() {
        return this.pocetVyhernych;
    }
    
    /**
     * @return HraciaPlocha vráti hraciu plochu
     */
    public HraciaPlocha getHraciaPlocha() {
        return this.hraciaPlocha;
    }
    
    /**
     * @return ArrayList<Hrac> vráti všetkých hráčov
     */
    public ArrayList<Hrac> getHraci() {
        return this.hraci;
    }
    
    /**
     * Metóda nastavuje hodnotu počtu výherných kôl.
     */
    private void setPocetVyhernychKol() {
        System.out.print("Na koľko výherných kôl chcete hrať: ");
        int novyPocetVyhernych = Hra.kontrolaCislo(this.input);
        while (novyPocetVyhernych < 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 0.");
            System.out.print("Na koľko výherných kôl chcete hrať: ");
            novyPocetVyhernych = Hra.kontrolaCislo(this.input);
        }
        this.pocetVyhernych = novyPocetVyhernych;
    }
    
    /**
     * Metóda nastavuje koľko políčok od hráča následujúcich za sebou musí byť pre výhru kola.
     */
    private void setPocetPolicokZaSebou() {
        System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
        int novyPocetPolicokZaSebou = Hra.kontrolaCislo(this.input);
        while (novyPocetPolicokZaSebou < 3 || novyPocetPolicokZaSebou > this.hraciaPlocha.getVelkostPlochy() - 1) {
            System.out.println("Musíte zadať číslo, ktoré je väčšie ako 2 a menšie alebo rovné ako veľkosť plochy.");
            System.out.print("Koľko políčok za sebou musí byť v rade pre výhru: ");
            novyPocetPolicokZaSebou = Hra.kontrolaCislo(this.input);
        }
        this.hraciaPlocha = new HraciaPlocha(this.hraciaPlocha.getVelkostPlochy() - 1, novyPocetPolicokZaSebou);
        this.hraciaPlocha.setPolicka();
    }
    
    /**
     * Metóda nastavuje veľkosť hracej plochy.
     */
    private void setVelkostPlochy() {
        System.out.print("Veľkosť hracej plochy: ");
        int novaVelkostPlochy = Hra.kontrolaCislo(this.input);
        while ((novaVelkostPlochy < 3 || novaVelkostPlochy > 9) || (this.hraciaPlocha.getPocetPolicokZaSebou() > novaVelkostPlochy)) {
            if (this.hraciaPlocha.getPocetPolicokZaSebou() > novaVelkostPlochy && novaVelkostPlochy > 2) {
                System.out.format("Konkrétna hodnota počtu výherných políčok následujúcich za sebou je: %s%n", this.hraciaPlocha.getPocetPolicokZaSebou());
            }
            System.out.format("Musíte zadať číslo väčšie ako 2 a menšie ako 10%n a zároveň číslo nesmie byť menšie ako počet výherných políčok za sebou.%n");
            System.out.print("Veľkosť hracej plochy: ");
            novaVelkostPlochy = Hra.kontrolaCislo(this.input);
        }
        this.hraciaPlocha = new HraciaPlocha(novaVelkostPlochy, this.hraciaPlocha.getPocetPolicokZaSebou());
        this.hraciaPlocha.setPolicka();
    }
    
    /**
     * Metóda pridáva hráčov do hry. Ak je hráč len jeden automaticky pridá bota.
     * 
     * @param jeVKonstruktore určuje, či sa pridávanie hráčov nachádza v konštruktore
     * @return boolean vráti hodnotu true ak sa hráč úspešne pridal
     */
    private boolean pridajHraca(boolean jeVKonstruktore) {
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
            if (this.hraci.size() > 1 && this.hraci.get(1).getJeBot()) {
                this.pocetHracov--;
                this.hraci.remove(1);
            }
            return this.hraci.add(new Hrac(znak, false));
        }
    
        return false;
    }
    
    /**
     * Metóda odstraňuje hráčov z hry.
     * 
     * @return boolean vracia hodnotu true ak sa podarí odstrániť hráča
     */
    private boolean odstranHraca() {
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
     * napísať a následne ho tam napíšeme.
     * Metóda sa opakuje dokým sa nezadá 
     * korektné číslo riadku a stĺpca.
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme nastaviť 
     * políčko na hodnotu znaku, ktorý si zadal
     */
    private void setPolickoPreHraca(int idHrac) {
        System.out.println("Kolo hráča " + (idHrac + 1) + " (" + this.hraci.get(idHrac).getZnak() + ").");
        
        /*
         * Ak je kolo bota, tak sa vykoná metóda setPolickoPreBota a následne sa táto metóda ukončí.
         */
        if (this.hraci.get(idHrac).getJeBot()) {
            this.setPolickoPreBota();
            return;
        }
    
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
    
        this.hraciaPlocha.setPolicko(riadok, stlpec, this.hraci.get(idHrac));
        this.hraciaPlocha.vypisPlochu();
    
        /* 
         * ak nebude políčko zadané správne (t.j. ak už sa na danom mieste niečo nachádza
         * alebo je zle zadaný stĺpec alebo riadok), tak sa vykoná táto metóda znovu.
         */
        if (!this.hraciaPlocha.jePolickoZadaneSpravne()) {
            this.setPolickoPreHraca(idHrac);
        }
    }
    
    /**
     * Metóda slúži na zadanie políčka pre bota.
     */
    private void setPolickoPreBota() {
        Random random = new Random();
        
        do {
            int randomCisloRiadok = random.nextInt(this.hraciaPlocha.getVelkostPlochy());
            int randomCisloStlpec = random.nextInt(this.hraciaPlocha.getVelkostPlochy());
            this.hraciaPlocha.setPolicko(randomCisloRiadok, randomCisloStlpec, this.hraci.get(1));
        } while (!this.hraciaPlocha.jePolickoZadaneSpravne());
                    
        this.hraciaPlocha.vypisPlochu();
    }
    
    /**
     * Po každom kole kontroluje riadky a stĺpce, či nevyhral niektorí z hráčov.
     * 
     * @TODO - fujky metóda, treba ju upraviť
     */
    private void vyhraKola() {
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
    private void vyhra() throws IOException {
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
    private void remiza() {
        if (this.hraciaPlocha.jeZaplnena()) {
            this.koniecHry = true;
            this.vyhercaKola = null;
        }
    }
    
    /**
     * Vypíše kto vyhrál a taktiež body všetkých hráčov.
     */
    private void vypisVyhry() {
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
     * Metóda na výpis práve nastavených hodnôt hry.
     */
    private void vypisNastavenychHodnot() {
        System.out.format("Počet Hráčov: %s%n", this.pocetHracov);
        System.out.format("Znaky hráčov: ");
        for (int i = 0; i < this.pocetHracov; i++) {
            System.out.format("(%s), ", this.hraci.get(i).getZnak());
        }
        System.out.println();
        
        System.out.format("Je hráč bot: ");
        for (int i = 0; i < this.pocetHracov; i++) {
            if (this.hraci.get(i).getJeBot()) {
                System.out.format("(á), ");
            } else {
                System.out.format("(n), ");
            }
            
        }
        System.out.println();
        
        System.out.format("Počet výherných kôl pre výhru: %d%n", this.pocetVyhernych);
        System.out.format("Počet políčok za sebou pre výhru: %d%n", this.hraciaPlocha.getPocetPolicokZaSebou());
        System.out.format("Velkosť plochy: %d%n%n", this.hraciaPlocha.getVelkostPlochy() - 1);
    }
    
    /**
     * Metóda ukončí hru a vypíše konečného výhercu podľa počtu vyhratých kôl.
     */
    private void ukoncitHru() {
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
     * Obsahuje základné nastavenia hry
     * (Pridať hráča, Odstrániť hráča, Zmeniť počet výherných, Zmeniť počet políčok za sebou, Zmeniť veľkosť plochy)
     */
    private void nastaveniaHry() throws IOException {
        this.vypisNastavenychHodnot();
    
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Pridať hráča (a)\nOdstrániť hráča (d)\nZmeniť počet výherných (p)\nZmeniť počet políčok za sebou (s)\nZmeniť veľkosť plochy (v)\nSpäť (b)\n");
        char znak = this.input.next().charAt(0);
        znak = Character.toLowerCase(znak);
    
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
                this.setPocetVyhernychKol();
                this.nastaveniaHry();
                break;
            case 's':
                this.setPocetPolicokZaSebou();
                this.nastaveniaHry();
                break;
            case 'v':
                this.setVelkostPlochy();
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
    private void moznostiHry() throws IOException {
        UloznePriestory up = UloznePriestory.getUloznePriestory();
        
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Pokračovať (c)\nUložiť (s)\nVrátiť sa do menu (m)\nUkončiť (e)\n");
        char znak = this.input.next().charAt(0);
        znak = Character.toLowerCase(znak);

        switch (znak) {
            case 'c':
                this.zacniHru();
                break;
            case 's':
                if (!up.vyberSave(true)) {
                    System.out.println("Nepodarilo sa uložiť.");
                }
                this.moznostiHry();
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
                break;
        }
    }

    /**
     * Metóda zamieša poradie hráčov.
     * 
     * @return int[] vráti náhodne zamiešané poradie hráčov
     */
    private int[] zamiesatPoradieHracov() {
        int[] randomPoradie = new int[this.pocetHracov];
        Random random = new Random();
        
        for (int i = 0; i < this.pocetHracov; i++) {
            randomPoradie[i] = i;
        }
        
        for (int i = 0; i < randomPoradie.length; i++) {
            int randomCislo = random.nextInt(this.pocetHracov);
            int randomPozicia = randomPoradie[randomCislo];
            
            randomPoradie[randomCislo] = randomPoradie[i];
            randomPoradie[i] = randomPozicia;
        }
        
        return randomPoradie;
    }

    /**
     * Základná metóda, ktorá spúšťa celú hru. Hrá sa dokým sa atribút
     * koniecHry nerovná true. Po tom ako hráč vyhrá, vypíše sa kto
     * vyhral a hra sa ukončí.
     */
    private void zacniHru() throws IOException {
        this.hraciaPlocha.setPolicka();
        this.hraciaPlocha.vypisPlochu();
        this.koniecHry = false;
        this.vyhercaKola = null;
        int[] nahodnePoradie = this.zamiesatPoradieHracov();

        // vymazanie výhercov
        for (int i = 0; i < this.vyherci.size(); i++) {
            this.vyherci.remove(0);
        }

        while (!this.koniecHry) {
            for (int i = 0; i < this.pocetHracov; i++) {
                this.vyhra();

                if (!this.koniecHry) {
                    this.setPolickoPreHraca(nahodnePoradie[i]);
                }
                this.vyhraKola();
                this.remiza();
            }
        }

        this.vypisVyhry();
        this.moznostiHry();
    }

    /**
     * Vypíše základné menu hry.
     * (Nová hra, Pokračovať v uloženej hre, Nastavenia, Ukončiť hru)
     */
    public void menuHry() throws IOException {
        UloznePriestory up = UloznePriestory.getUloznePriestory();
        
        System.out.println("Čo si prajete urobiť?");
        System.out.println("Nová hra (n)\nPokračovať v uloženej hre (c)\nNastavenia (o)\nUkončiť hru (e)\n");
        char znak = this.input.next().charAt(0);
        znak = Character.toLowerCase(znak);
        
        switch (znak) {
            case 'n':
                for (int i = 0; i < this.pocetHracov; i++) {
                    this.hraci.get(i).resetujVyhry();
                }
                this.zacniHru();
                break;
            case 'c':
                if (up.vyberSave(false)) {
                    System.out.format("Načítané%n");
                    this.zacniHru();
                } else {
                    System.out.println("Hru sa nepodarilo načítať. Súbor je zrejme poškodený.");
                }
                this.menuHry();
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
}
