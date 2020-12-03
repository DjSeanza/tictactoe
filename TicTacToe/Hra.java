import java.util.ArrayList;
import java.util.Scanner;

public class Hra {
    private ArrayList<Hrac> hraci;
    private HraciaPlocha hraciaPlocha;
    private int pocetHracov;
    private boolean vyhra;
    private Hrac vyherca;
    
    private Scanner input = new Scanner(System.in);
    
    /**
     * @param riadky počet riadkov hracej plochy
     * @param stlpce počet stĺpcov hracej plochy
     * @param pocetHracov počet hráčov
     * 
     * @TODO - ošetriť počet hráčov tak, aby ich na hracej ploche nebolo veľa
     * 
     * Inicializujeme si základné hodnoty atribútov,
     * vytvoríme hraciu plochu a následne sa hráčov opýtame
     * aké znaky chcú používať pri hre (berie sa vždy len 
     * prvý znak, ktorý zadajú)
     */
    public Hra(int riadky, int stlpce, int pocetHracov) { 
        this.hraci = new ArrayList<Hrac>();
        
        this.hraciaPlocha = new HraciaPlocha(riadky, stlpce);
        this.hraciaPlocha.setPolicka();
        this.hraciaPlocha.vypisPlochu();
        
        for (int i = 0; i < pocetHracov; i++) {
            System.out.println("Znak hráča: " + (i + 1));
            char znak = this.input.next().charAt(0);
            this.hraci.add(new Hrac(znak));
        }
        
        this.pocetHracov = pocetHracov;
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme nastaviť 
     * políčko na hodnotu znaku, ktorý si zadal
     * 
     * @TODO - kontrola, či zadali správne hodnoty pre riadok a stĺpec
     * 
     * Opýtame sa hráča na Riadok a stĺpec, do ktorého chce znak
     * napísať a následne ho tam napíšeme pomocou metódy setPolicko()
     */
    public void setPolickoPreHraca(int hrac) {
        System.out.println("Kolo hráča: " + (hrac + 1));
        System.out.println("Riadok: ");
        int riadok = this.input.nextInt();
        System.out.println("Stlpec: ");
        int stlpec = this.input.nextInt();
        
        this.hraciaPlocha.setPolicko(riadok, stlpec, this.hraci.get(hrac));
        this.hraciaPlocha.vypisPlochu();
        if (!this.hraciaPlocha.polickoZadaneSpravne()) {
            this.setPolickoPreHraca(hrac);
        }
    }
    
    /**
     * @TODO - kontrola diagonály
     * @TODO - urobiť remízu
     * 
     * Po každom kole kontroluje riadky a stĺpce, či nevyhral niektorí z
     * hráčov
     */
    public void vyhra() {
        for (Hrac aktualny: this.hraci) {
            for (int i = 0; i < this.hraciaPlocha.getPocetRiadkov(); i++) {
                if (!this.vyhra) {
                    this.vyhra = this.hraciaPlocha.vyhraRiadok(aktualny, i);
                    this.vyherca = aktualny;
                }
            }
            for (int i = 0; i < this.hraciaPlocha.getPocetStlpcov(); i++) {
                if (!this.vyhra) {
                    this.vyhra = this.hraciaPlocha.vyhraStlpec(aktualny, i);
                    this.vyherca = aktualny;
                }
            }
        }
    }
    
    /**
     * @TODO - dať na výber na koľko v rade chce hrať (default 3)
     * @TODO - dať hráčovi na výber, na koľko výherných chce hrať
     * @TODO - pripočítanie výhry hráčovi a spustenie ďalšej hry
     * @TODO - po každej hre sa opýtať, či chceme pokračovať, ak nie, tak
     * vypísať výhercu a ukončiť hru
     * 
     * Základná metóda, ktorá spúšťa celú hru. Hrá sa dokým sa atribút
     * vyhra nerovná true. Po tom ako hráč vyhrá, vypíše sa kto
     * vyhral a hra sa ukončí.
     */
    public void hra() {
        this.vyhra = false;
        
        while (!this.vyhra) {
            for (int i = 0; i < this.pocetHracov; i++) {
                this.setPolickoPreHraca(i);
                this.vyhra();
            }
        }
        
        System.out.format("Vyhral hráč so znakom %s", this.vyherca.getZnak());
    }
}
