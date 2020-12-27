import java.util.ArrayList;
import java.util.Scanner;

public class Hra {
    private ArrayList<Hrac> hraci;
    private HraciaPlocha hraciaPlocha;
    private int pocetHracov;
    private boolean koniecHry;
    private Hrac vyherca;
    
    private Scanner input = new Scanner(System.in);
    
    /**
     * Inicializujeme si základné hodnoty atribútov,
     * vytvoríme hraciu plochu a následne sa hráčov opýtame
     * aké znaky chcú používať pri hre (berie sa vždy len 
     * prvý znak, ktorý zadajú).
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     * @param pocetHracov počet hráčov
     * 
     * @TODO - počet výherných zmeniť na pocetZnakovZaSebou a počet výherných
     * dať na ľubovoľný počet opakovaní tej hry.
     */
    public Hra(int velkost, int pocetPolicokZaSebou, int pocetHracov) { 
        this.hraci = new ArrayList<Hrac>();
        
        this.hraciaPlocha = new HraciaPlocha(velkost, pocetPolicokZaSebou);
        this.hraciaPlocha.setPolicka();
        this.hraciaPlocha.vypisPlochu();        
        
        for (int i = 0; i < pocetHracov; i++) {
            boolean jePridany = this.pridajHraca();
            while (!jePridany) {
                jePridany = this.pridajHraca();
            }
        }
        
        this.pocetHracov = pocetHracov;
    }
    
    /**
     * Metóda pridáva hráčov do hry.
     * 
     * @return boolean vráti hodnotu true ak sa hráč úspešne pridal
     */
    public boolean pridajHraca() {
        /* this.hraciaPlocha.getVelkostPlochy() - 2
         * pretože do veľkosti plochy sa rátajú aj stĺpce a riadky s číslami
         * a hráčov má byť aspoň o jedného menej ako je veľkosť plochy.
        */
        if (this.hraci.size() >= this.hraciaPlocha.getVelkostPlochy() - 2) {
            System.out.println("Príliš veľa hráčov, nedá sa pridať viac.");
            return false;
        }
        
        System.out.println("Znak hráča: " + (this.hraci.size() + 1));
        char znak = this.input.next().charAt(0);
        boolean jeZnakRovnaky = false;
        
        for (int i = 0; i < this.hraci.size(); i++) {
            if (znak == this.hraci.get(i).getZnak()) {
                System.out.println("Takýto znak má už iný hráč. Zadajte nový.");
                jeZnakRovnaky = true;
            }
        }
        
        if (!jeZnakRovnaky) {
            this.pocetHracov++;
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
            System.out.println("Hráča sa nepodarilo odstrániť. Skúste to znovu,");
        }
        return jeOdstraneny;
    }
    
    /**
     * Opýtame sa hráča na riadok a stĺpec, do ktorého chce znak
     * napísať a následne ho tam napíšeme pomocou metódy setPolicko().
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme nastaviť 
     * políčko na hodnotu znaku, ktorý si zadal
     * 
     * @TODO - kontrola, či zadali správne hodnoty pre riadok a stĺpec
     */
    public void setPolickoPreHraca(int hrac) {
        System.out.println("Kolo hráča: " + (hrac + 1));
        System.out.println("Riadok: ");
        int riadok = this.input.nextInt();
        System.out.println("Stlpec: ");
        int stlpec = this.input.nextInt();
        
        this.hraciaPlocha.setPolicko(riadok, stlpec, this.hraci.get(hrac));
        this.hraciaPlocha.vypisPlochu();
        if (!this.hraciaPlocha.jePolickoZadaneSpravne()) {
            this.setPolickoPreHraca(hrac);
        }
    }
    
    /**
     * Po každom kole kontroluje riadky a stĺpce, či nevyhral niektorí z hráčov.
     * 
     * @TODO - urobiť remízu
     * @TODO - fujky metóda, treba ju upraviť
     */
    public void vyhra() {
        for (Hrac aktualny: this.hraci) {
            for (int i = 0; i < this.hraciaPlocha.getVelkostPlochy(); i++) {
                if (!this.koniecHry) {
                    // true značí, že ide o kontrolu riadku
                    this.koniecHry = this.hraciaPlocha.vyhraRiadokStlpec(aktualny, i, i, true);
                    this.vyherca = aktualny;
                }
                if (!this.koniecHry) {
                    // false značí, že ide o kontrolu stĺpca
                    this.koniecHry = this.hraciaPlocha.vyhraRiadokStlpec(aktualny, i, i, false);
                    this.vyherca = aktualny;
                }
                if (!this.koniecHry) {
                    //System.out.format("%d%n", i);
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, 0, i, true);
                    this.vyherca = aktualny;
                }
                if (!this.koniecHry) {
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, i, 0, true);
                    this.vyherca = aktualny;
                }
                if (!this.koniecHry) {
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, 0, i, false);
                    this.vyherca = aktualny;
                }
                if (!this.koniecHry) {
                    //System.out.format("%d%n", i);
                    
                    /* 
                     * this.hraciaPlocha.getVelkostPlochy() - 1 
                     * je to kvôli tomu, aby sa kontrolovalo stále od posledného stĺpca
                     */
                    this.koniecHry = this.hraciaPlocha.vyhraDiagonala(aktualny, i, this.hraciaPlocha.getVelkostPlochy() - 1, false);
                    this.vyherca = aktualny;
                }
            }
        }
        //System.out.println(this.vyhra);
    }
    
    /**
     * Metóda nastaví koniec hry na true a výhercu na null.
     */
    public void remiza() {
        if (this.hraciaPlocha.jeZaplnena()) {
            this.koniecHry = true;
            this.vyherca = null;
        }
    }
    
    /**
     * Vypíše kto vyhrál a taktiež body všetkých hráčov.
     */
    public void vypisVyhry() {
        if (this.vyherca == null) {
            System.out.format("Je to remíza.%n");
            System.out.println("------------------------------------");
            for (int i = 0; i < this.pocetHracov; i++) {
                System.out.format("Hráč so znakom %s: %d%n", this.hraci.get(i).getZnak(), this.hraci.get(i).getPocetVyhier());
            }
            System.out.println("------------------------------------");
        } else {
            this.vyherca.pridajVyhru();
            System.out.println("------------------------------------");
            System.out.format("Vyhral hráč so znakom %s.%n", this.vyherca.getZnak());
            for (int i = 0; i < this.pocetHracov; i++) {
                System.out.format("Hráč so znakom %s: %d%n", this.hraci.get(i).getZnak(), this.hraci.get(i).getPocetVyhier());
            }
            System.out.println("------------------------------------");
        }
    }
    
    /**
     * Základná metóda, ktorá spúšťa celú hru. Hrá sa dokým sa atribút
     * koniecHry nerovná true. Po tom ako hráč vyhrá, vypíše sa kto
     * vyhral a hra sa ukončí.
     * 
     * @TODO - pripočítanie výhry hráčovi a spustenie ďalšej hry
     * @TODO - po každej hre sa opýtať, či chceme pokračovať, ak nie, tak
     * vypísať výhercu a ukončiť hru
     */
    public void hra() {
        this.koniecHry = false;
        this.hraciaPlocha.setPolicka();
        this.vyherca = null;
        
        while (!this.koniecHry) {
            for (int i = 0; i < this.pocetHracov; i++) {
                
                if (!this.koniecHry) {
                    this.setPolickoPreHraca(i);
                }
                this.vyhra();
                this.remiza();
            }
        }
        
        this.vypisVyhry();
    }
}
