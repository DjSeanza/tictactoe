
public class HraciaPlocha {
    private char[][] hraciaPlocha;
    private int velkostPlochy;
    private int pocetVyhernych;
    private boolean zadaneSpravne;
    
    /**
     * Vytvorí hraciu plochu podľa zadaných parametrov.
     * Ak je hracia plocha menšia ako 3x3 alebo väčšia ako 12x12,
     * tak sa vytvorí hracia plocha 3x3.
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     */
    public HraciaPlocha(int velkost, int pocetVyhernych) {
        this.velkostPlochy = velkost;
        this.pocetVyhernych = pocetVyhernych;
        this.zadaneSpravne = true;
        
        for (int i = 0; i < velkost; i++) {
            for (int j = 0; j < velkost; j++) {
                if (velkost > 2 && velkost < 13) {
                    this.hraciaPlocha = new char[velkost][velkost];
                } else {
                    this.hraciaPlocha = new char[3][3];
                    this.velkostPlochy = 3;
                }
            }
        }
    }
    
    public boolean polickoZadaneSpravne() {
        return this.zadaneSpravne;
    }
    
    public int getVelkostPlochy() {
        return this.velkostPlochy;
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný stĺpec
     * @param stlpec konkrétny stĺpec, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraStlpec(Hrac hrac, int stlpec) {
        for (int i = 0; i < this.velkostPlochy - (this.pocetVyhernych - 1); i++) {
            char prvyZnak = this.hraciaPlocha[i][stlpec];
            char druhyZnak = this.hraciaPlocha[i + 1][stlpec];
            char tretiZnak = this.hraciaPlocha[i + 2][stlpec];
            //System.out.format("%s %s %s %d%n", prvyZnak, druhyZnak, tretiZnak, i);
            if (prvyZnak == hrac.getZnak() && prvyZnak == druhyZnak && prvyZnak == tretiZnak) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný riadok
     * @param riadok konkrétny riadok, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraRiadok(Hrac hrac, int riadok) {        
        for (int i = 0; i < this.velkostPlochy - (this.pocetVyhernych - 1); i++) {
            char prvyZnak = this.hraciaPlocha[riadok][i];
            char druhyZnak = this.hraciaPlocha[riadok][i + 1];
            char tretiZnak = this.hraciaPlocha[riadok][i + 2];
            //System.out.format("%s %s %s %d%n", prvyZnak, druhyZnak, tretiZnak, i);
            if (prvyZnak == hrac.getZnak() && prvyZnak == druhyZnak && prvyZnak == tretiZnak) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výhernú diagonálu
     * @param zaciatokRiadku od ktorého riadku chceme kontrolovať
     * @param zaciatokStlpca od ktorého stĺpca chceme kontrolovať
     * @param jeZLava určuje z ktorej strany chceme diagonálu kontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraDiagonala(Hrac hrac, int zaciatokRiadku, int zaciatokStlpca, boolean jeZLava) {
        if (zaciatokRiadku > this.velkostPlochy || zaciatokStlpca > this.velkostPlochy) {
            return false;
        }
        if (zaciatokRiadku < 0 || zaciatokStlpca < 0) {
            return false;
        }
        
        
        int diagonala = this.velkostPlochy;
        if (jeZLava) {
            diagonala -= (Math.abs(zaciatokRiadku - zaciatokStlpca));
        }
        if (!jeZLava) {
            diagonala = (Math.abs(zaciatokRiadku - zaciatokStlpca) + 1);
        }
        
        for (int i = 0; i < diagonala - (this.pocetVyhernych - 1); i++) {
            char prvyZnak = this.hraciaPlocha[zaciatokRiadku][zaciatokStlpca];
            char druhyZnak = '.';
            char tretiZnak = '.';
            if (jeZLava) {
                druhyZnak = this.hraciaPlocha[zaciatokRiadku + 1][zaciatokStlpca + 1];
                tretiZnak = this.hraciaPlocha[zaciatokRiadku + 2][zaciatokStlpca + 2];
                zaciatokStlpca++;
            }
            if (!jeZLava) {
                druhyZnak = this.hraciaPlocha[zaciatokRiadku + 1][zaciatokStlpca - 1];
                tretiZnak = this.hraciaPlocha[zaciatokRiadku + 2][zaciatokStlpca - 2];
                zaciatokStlpca--;
            }
            //System.out.format("(%d %d) %s %s %s %d%n", zaciatokRiadku, zaciatokStlpca, prvyZnak, druhyZnak, tretiZnak, i);
            
            zaciatokRiadku++;

            if (prvyZnak == hrac.getZnak() && prvyZnak == druhyZnak && prvyZnak == tretiZnak) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return boolean vracia hodnotu true, ak je celá plocha zaplnená
     * znakmi rôznymi od '.' (t.j. celá plocha je zaplnená znakmi od hráčov).
     */
    public boolean jeZaplnena() {
        for (int i = 0; i < this.velkostPlochy; i++) {
            for (int j = 0; j < this.velkostPlochy; j++) {
                if (this.hraciaPlocha[i][j] == '.') {
                    return false;
                }
            }   
        }
        return true;
    }
    
    /**
     * @TODO - pre vyhry robiť kontroly na riadky a stlpce, aby sa nedalo
     * zadať zlé číslo (napr. viac ako pocet stlpcov a tak)
     * @TODO - urobiť výhry na viac výherných, podľa toho, koľko si zadá používateľ
     * @TODO - spojiť výhry dokopy do jednej metódy
     */
    
    /**
     * Nastavíme políčko pre daného hráča na konkrétnu pozíciu
     * 
     * @param riadok číslo riadku, do ktorého chceme uložiť hodnotu
     * @param stlpec číslo stĺpca, do ktorého chceme uložiť hodnotu
     * @param hrac konkrétny hráč, pre ktorého nastavujeme políčko
     * 
     * @TODO - ak sa už niečo v políčku nachádza, pridať možnosť pre
     * zabranie si políčka (napríklad hodenie mincou alebo kameň, papier, nožnice)
     */
    public void setPolicko(int riadok, int stlpec, Hrac hrac) {
        if (riadok > this.velkostPlochy || stlpec > this.velkostPlochy) {
            System.out.println("Zle zadaný riadok alebo stĺpec");
            return;
        }
        
        if (this.hraciaPlocha[riadok - 1][stlpec - 1] == '.') {
            this.hraciaPlocha[riadok - 1][stlpec - 1] = hrac.getZnak();
            this.zadaneSpravne = true;
        } else {
            System.out.println("Tu už sa niečo nachádza");
            this.zadaneSpravne = false;
        }
    }
    
    /**
     * Vykreslíme hraciu plochu pomocou bodiek
     * 
     * @TODO - v hracej ploche nech sú indexy riadkov a stĺpcov na lepšiu orientáciu
     */
    public void setPolicka() {
        for (int riadok = 0; riadok < this.velkostPlochy; riadok++) {
            for (int stlpec = 0; stlpec < this.velkostPlochy; stlpec++) {
                this.hraciaPlocha[riadok][stlpec] = '.';
                /*this.hraciaPlocha[0][stlpec] = '1';
                this.hraciaPlocha[riadok][0] = '1';*/
            }
        }
    }
    
    /**
     * Vypíšeme hraciu plochu
     */
    public void vypisPlochu() {
        int sirkaPlochy = 1;
        for (char[] plocha: this.hraciaPlocha) {
            for (char aktualny: plocha) {
                if (sirkaPlochy % this.velkostPlochy == 0) {
                    System.out.format("%3s%n", aktualny);
                } else {
                    System.out.format("%3s", aktualny);
                }
                sirkaPlochy++;
            }
        }
    }
}
