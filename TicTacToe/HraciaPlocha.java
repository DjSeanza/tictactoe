/**
 * Táto trieda slúži na základnú prácu s hracou plochou, t.j. výpis plochy, nastavovanie hodnoty daným políčkam a pod. 
 * 
 * @author Patrik Ištvanko
 * @version 1.0.5
 */
public class HraciaPlocha {
    private char[][] hraciaPlocha;
    private int velkostPlochy;
    private int pocetPolicokZaSebou;
    private boolean zadaneSpravne;
    
    /**
     * Vytvorí hraciu plochu podľa zadaných parametrov.
     * Ak je hracia plocha menšia ako 3x3 alebo väčšia ako 9x9,
     * tak sa vytvorí hracia plocha 3x3.
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     * @param pocetPolicokZaSebou určuje na koľko výherných políčok sa bude hrať
     */
    public HraciaPlocha(int velkost, int pocetPolicokZaSebou) {
        this.velkostPlochy = velkost + 1;
        this.pocetPolicokZaSebou = pocetPolicokZaSebou;
        this.zadaneSpravne = true;
        
        for (int i = 0; i < velkost; i++) {
            for (int j = 0; j < velkost; j++) {
                this.hraciaPlocha = new char[this.velkostPlochy][this.velkostPlochy];
            }
        }
    }
    
    /**
     * Kontroluje, či sa v danej bunke nachádza znak daného hráča.
     * 
     * @param hrac hráč, pre ktorého chceme kontrolovať bunku
     * @param riadok riadok v ktorom budeme kontrolovať bunku
     * @param stlpec stĺpec v ktorom budeme kontrolovať bunku
     * @return boolean vráti hodnotu true ak daná bunka obsahuje znak daného hráča
     */
    private boolean vyhraBunka(Hrac hrac, int riadok, int stlpec) {
        return this.hraciaPlocha[riadok][stlpec] == hrac.getZnak();
    }
    
    /**
     * @return boolean vráti hodnotu true ak je políčko zadané správne
     */
    public boolean jePolickoZadaneSpravne() {
        return this.zadaneSpravne;
    }
    
    /**
     * Vráti nám veľkosť plochy (t.j. šírku riadku/stĺpca)
     * 
     * @return int vráti veľkosť plochy
     */
    public int getVelkostPlochy() {
        return this.velkostPlochy;
    }
    
    /**
     * Vráti nám hodnotu, koľko políčok následujúcich za sebou je potrebných na výhru kola.
     * 
     * @return int vráti počet políčok následujúcich za sebou potrebných na výhru kola.
     */
    public int getPocetPolicokZaSebou() {
        return this.pocetPolicokZaSebou;
    }
    
    /**
     * Kontroluje či je daný riadok alebo stĺpec výherný. Ak je riadok alebo stĺpec výherný 
     * (t.j. ak následuje za sebou určitý počet znakov, ktoré zadal používateľ), tak vráti hodnotu true.
     * 
     * @param hrac hráč, pre ktorého chceme kontrolovať riadok a stĺpec
     * @param riadok konkrétny riadok, ktorý chceme skontrolovať
     * @param stlpec stĺpec, ktorý chceme skontrolovať
     * @param jeRiadok určuje, či sa jedná o riadok alebo stĺpec
     * @return boolean vracia hodnotu true, ak sa určitý počet znakov následujúcich za sebou rovná
     */
    public boolean vyhraRiadokStlpec(Hrac hrac, int riadok, int stlpec, boolean jeRiadok) {
        if (riadok < 0 || riadok > this.velkostPlochy || stlpec < 0 || stlpec > this.velkostPlochy) {
            System.out.println("Zle zadaný riadok alebo stĺpec.");
            return false;
        }
        
        //char[] plocha = new char[this.pocetPolicokZaSebou];
        
        for (int i = 0; i < this.velkostPlochy - (this.pocetPolicokZaSebou - 1); i++) {
            int vyhra = 0;
            
            for (int j = 0; j < this.pocetPolicokZaSebou; j++) {
                
                if (jeRiadok) {
                    if (this.vyhraBunka(hrac, riadok, i + j)) {
                        vyhra++;
                    }
                } else {
                    if (this.vyhraBunka(hrac, i + j, stlpec)) {
                        vyhra++;
                    }
                }
                
                if (vyhra == this.pocetPolicokZaSebou) {
                    return true;
                }
                
                /*plocha[j] = this.hraciaPlocha[riadok + j][stlpec + j];
                System.out.format("(%d %d)(%d %d) %d%n", i, j, riadok + j, stlpec + j, vyhra);*/
            }
            /*for (int h = 0; h < plocha.length; h++) {
                System.out.print("[" + plocha[h] + "]");
            }
            System.out.println();*/
        }
        return false;
    }
    
    /**
     * Kontroluje diagonálu, či je výherná. Ak výherná je (t.j. ak následuje za sebou určitý 
     * počet znakov, ktoré zadal používateľ), tak vráti hodnotu true.
     * 
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výhernú diagonálu
     * @param zaciatokRiadku od ktorého riadku chceme kontrolovať
     * @param zaciatokStlpca od ktorého stĺpca chceme kontrolovať
     * @param jeZLava určuje z ktorej strany chceme diagonálu kontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraDiagonala(Hrac hrac, int zaciatokRiadku, int zaciatokStlpca, boolean jeZLava) {
        if (zaciatokRiadku < 0 || zaciatokStlpca < 0) {
            return false;
        }
        if (zaciatokRiadku > this.velkostPlochy || zaciatokStlpca > this.velkostPlochy) {
            return false;
        }
        
        int diagonala = this.velkostPlochy;
        if (jeZLava) {
            diagonala -= (Math.abs(zaciatokRiadku - zaciatokStlpca));
        } else {
            diagonala = (Math.abs(zaciatokRiadku - zaciatokStlpca) + 1);
        }
        
        for (int i = 0; i < diagonala - (this.pocetPolicokZaSebou - 1); i++) {
            int vyhra = 0;
            
            for (int j = 0; j < this.pocetPolicokZaSebou; j++) {
                
                if (jeZLava) {
                    if (this.vyhraBunka(hrac, zaciatokRiadku + j, zaciatokStlpca + j)) {
                        vyhra++;
                    }
                } else {
                    if (this.vyhraBunka(hrac, zaciatokRiadku + j, zaciatokStlpca - j)) {
                        vyhra++;
                    }
                }
                
            }
            
            if (jeZLava) {
                zaciatokStlpca++;
            } else {
                zaciatokStlpca--;
            }
            zaciatokRiadku++;

            if (vyhra == this.pocetPolicokZaSebou) {
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
            this.zadaneSpravne = false;
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
     * Dáme do každej bunky bodku a následne do prvého riadku a stĺpca vpíšeme ešte pomocné čísla riadkov a stĺpcov.
     */
    public void setPolicka() {
        for (int riadok = 0; riadok < this.velkostPlochy; riadok++) {
            for (int stlpec = 0; stlpec < this.velkostPlochy; stlpec++) {
                this.hraciaPlocha[riadok][stlpec] = '.';
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
