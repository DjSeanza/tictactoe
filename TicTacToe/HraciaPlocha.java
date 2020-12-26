
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
     * @param pocetVyhernych určuje na koľko výherných políčok sa bude hrať
     */
    public HraciaPlocha(int velkost, int pocetVyhernych) {
        // kvôli riadku a stĺpcu s číslami musíme pričítať ešte jeden riadok a stĺpec
        this.velkostPlochy = velkost + 1;
        this.pocetVyhernych = pocetVyhernych;
        this.zadaneSpravne = true;
        
        for (int i = 0; i < velkost; i++) {
            for (int j = 0; j < velkost; j++) {
                if (velkost > 2 && velkost < 13) {
                    this.hraciaPlocha = new char[this.velkostPlochy][this.velkostPlochy];
                } else {
                    this.hraciaPlocha = new char[4][4];
                    this.velkostPlochy = 4;
                }
            }
        }
    }
    
    public boolean jePolickoZadaneSpravne() {
        return this.zadaneSpravne;
    }
    
    /**
     * Vráti nám veľkosť plochy (t.j. šírku riadku/stĺpca)
     */
    public int getVelkostPlochy() {
        return this.velkostPlochy;
    }
    
    public boolean vyhraBunka(Hrac hrac, int riadok, int stlpec) {
        return this.hraciaPlocha[riadok][stlpec] == hrac.getZnak();
    }
    
    /**
     * Kontroluje, či je daný stĺpec výherný.
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný stĺpec
     * @param stlpec konkrétny stĺpec, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraStlpec(Hrac hrac, int stlpec) {
        for (int i = 0; i < this.velkostPlochy - (this.pocetVyhernych - 1); i++) {
            //char[] plocha = new char[this.pocetVyhernych];
            int vyhra = 0;
            
            for (int j = 0; j < this.pocetVyhernych; j++) {
                
                if (this.vyhraBunka(hrac, i + j, stlpec)) {
                    vyhra++;
                }
                
                /*plocha[j] = this.hraciaPlocha[i + j][stlpec];
                System.out.format("(%d %d) %d%n", i, j, vyhra);*/
                
                if (vyhra == this.pocetVyhernych) {
                    return true;
                }
                
            }
            
            /*for (int h = 0; h < plocha.length; h++) {
                System.out.print("[" + plocha[h] + "]");
            }
            System.out.println();*/
        }
        return false;
    }
    
    /**
     * Kontroluje, či je daný riadok výherný. 
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný riadok
     * @param riadok konkrétny riadok, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraRiadok(Hrac hrac, int riadok) {        
        for (int i = 0; i < this.velkostPlochy - (this.pocetVyhernych - 1); i++) {
            int vyhra = 0;
            
            for (int j = 0; j < this.pocetVyhernych; j++) {
                
                if (this.vyhraBunka(hrac, riadok, i + j)) {
                    vyhra++;
                }
                
                if (vyhra == this.pocetVyhernych) {
                    return true;
                }
                
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
        
        //char[] plocha = new char[this.pocetVyhernych];
        for (int i = 0; i < diagonala - (this.pocetVyhernych - 1); i++) {
            int vyhra = 0;
            
            for (int j = 0; j < this.pocetVyhernych; j++) {
                if (jeZLava) {
                    if (this.vyhraBunka(hrac, zaciatokRiadku + j, zaciatokStlpca + j)) {
                        vyhra++;
                    }
                }
                if (!jeZLava) {
                    if (this.vyhraBunka(hrac, zaciatokRiadku + j, zaciatokStlpca - j)) {
                        vyhra++;
                    }
                }
                /*plocha[j] = this.hraciaPlocha[zaciatokRiadku + j][zaciatokStlpca + j];
                System.out.format("(%d %d)(%d %d) %d%n", i, j, zaciatokRiadku + j, zaciatokStlpca + j, vyhra);*/
            }
            
            if (jeZLava) {
                zaciatokStlpca++;
            }
            if (!jeZLava) {
                zaciatokStlpca--;
            }
            zaciatokRiadku++;
            
            /*for (int h = 0; h < plocha.length; h++) {
                System.out.print("[" + plocha[h] + "]");
            }
            System.out.println();*/
            //System.out.format("(%d %d) %s %s %s %d%n", zaciatokRiadku, zaciatokStlpca, prvyZnak, druhyZnak, tretiZnak, i);
            
            

            if (vyhra == this.pocetVyhernych) {
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
        if (riadok > this.velkostPlochy - 1 || stlpec > this.velkostPlochy - 1) {
            System.out.println("Zle zadaný riadok alebo stĺpec");
            return;
        }
        
        if (this.hraciaPlocha[riadok][stlpec] == '.') {
            this.hraciaPlocha[riadok][stlpec] = hrac.getZnak();
            this.zadaneSpravne = true;
        } else {
            System.out.println("Tu už sa niečo nachádza");
            this.zadaneSpravne = false;
        }
    }
    
    /**
     * Vykreslíme hraciu plochu pomocou bodiek
     */
    public void setPolicka() {
        for (int riadok = 0; riadok < this.velkostPlochy; riadok++) {
            for (int stlpec = 0; stlpec < this.velkostPlochy; stlpec++) {
                this.hraciaPlocha[riadok][stlpec] = '.';
                // riešenie (char)stlpec nefungovalo, kvôli ASCII kódom
                // dané riešenie som našiel sem https://www.javatpoint.com/java-int-to-char
                this.hraciaPlocha[0][stlpec] = (char)(stlpec + '0');
                this.hraciaPlocha[riadok][0] = (char)(riadok + '0');
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
