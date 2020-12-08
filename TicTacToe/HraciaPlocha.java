
public class HraciaPlocha {
    private char[][] hraciaPlocha;
    private int stlpec;
    private int riadok;
    private boolean zadaneSpravne;
    
    /**
     * Vytvorí hraciu plochu podľa zadaných parametrov.
     * Ak je hracia plocha menšia ako 3x3 alebo väčšia ako 12x12,
     * tak sa vytvorí hracia plocha 3x3.
     * 
     * @param velkost je to veľkosť hracej plochy, plocha je vždy štvorcová
     */
    public HraciaPlocha(int velkost) {
        this.riadok = velkost;
        this.stlpec = velkost;
        this.zadaneSpravne = true;
        
        for (int i = 0; i < velkost; i++) {
            for (int j = 0; j < velkost; j++) {
                if (velkost > 2 && velkost < 13) {
                    this.hraciaPlocha = new char[velkost][velkost];
                } else {
                    this.hraciaPlocha = new char[3][3];
                    this.riadok = 3;
                    this.stlpec = 3;
                }
            }
        }
    }
    
    public boolean polickoZadaneSpravne() {
        return this.zadaneSpravne;
    }
    
    public int getPocetStlpcov() {
        return this.stlpec;
    }
    
    public int getPocetRiadkov() {
        return this.riadok;
    }
    
    /**
     * Vracia hodnotu celej diagonály v podobe stringu.
     * 
     * @param zaciatokRiadku riadok, od ktorého chceme zisťovať diagonálu
     * @param zaciatokStlpca stĺpec, od ktorého chceme zisťovať diagonálu
     * @param jeZLava určuje, z ktorej strany budeme zisťovať diagonálu
     * @return String vracia všetky znaky z diagonály v podobe reťazca
     * 
     */
    public String getDiagonala(int zaciatokRiadku, int zaciatokStlpca, boolean jeZLava) {
        if (zaciatokRiadku > this.riadok || zaciatokStlpca > this.stlpec) {
            return null;
        }
        if (zaciatokRiadku < 1 || zaciatokStlpca < 1) {
            return null;
        }
        
        int diagonala = this.stlpec;
        if (jeZLava) {
            
            if (zaciatokRiadku > 1 || zaciatokStlpca > 1) {
                // určuje, koľkokrát sa má cyklus pre získanie znaku opakovať
                diagonala -= (Math.abs(zaciatokRiadku - zaciatokStlpca));
            }
            
        }
        
        if (!jeZLava) {
            
            if (zaciatokRiadku < this.riadok || zaciatokStlpca < this.stlpec) {
                // určuje, koľkokrát sa má cyklus pre získanie znaku opakovať
                diagonala = (Math.abs(zaciatokRiadku - zaciatokStlpca) + 1);
            }
            
        }
        
        // odpočítava sa aby sa číslo rovnalo indexu v poli
        zaciatokRiadku--; 
        zaciatokStlpca--;
        String znaky = "";
        for (int i = 0; i < diagonala; i++) {
            znaky += this.hraciaPlocha[zaciatokRiadku][zaciatokStlpca];
            
            zaciatokRiadku++;
            if (jeZLava) {
                zaciatokStlpca++;
            }
            if (!jeZLava) {
                zaciatokStlpca--;
            }
            
        }
        return znaky;
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný stĺpec
     * @param stlpec konkrétny stĺpec, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa daný počet znakov 
     * následujúcich za sebou rovná
     */
    public boolean vyhraStlpec(Hrac hrac, int stlpec) {
        for (int i = 0; i < this.riadok - 2; i++) {
            char prvyZnak = this.hraciaPlocha[i][stlpec];
            char druhyZnak = this.hraciaPlocha[i + 1][stlpec];
            char tretiZnak = this.hraciaPlocha[i + 2][stlpec];
            System.out.format("%s %s %s %d%n", prvyZnak, druhyZnak, tretiZnak, i);
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
        for (int i = 0; i < this.stlpec - 2; i++) {
            char prvyZnak = this.hraciaPlocha[riadok][i];
            char druhyZnak = this.hraciaPlocha[riadok][i + 1];
            char tretiZnak = this.hraciaPlocha[riadok][i + 2];
            System.out.format("%s %s %s %d%n", prvyZnak, druhyZnak, tretiZnak, i);
            if (prvyZnak == hrac.getZnak() && prvyZnak == druhyZnak && prvyZnak == tretiZnak) {
                return true;
            }
        }
        return false;
    }
    
    public boolean vyhraDiagonala(Hrac hrac, int zaciatokRiadku, int zaciatokStlpca) {
        for (int i = 0; i < this.riadok - 2; i++) {
            char prvyZnak = this.hraciaPlocha[zaciatokRiadku][zaciatokStlpca];
            char druhyZnak = this.hraciaPlocha[zaciatokRiadku + 1][zaciatokStlpca + 1];
            char tretiZnak = this.hraciaPlocha[zaciatokRiadku + 2][zaciatokStlpca + 2];
            System.out.format("(%d %d) %s %s %s %d%n", zaciatokRiadku, zaciatokStlpca, prvyZnak, druhyZnak, tretiZnak, i);
            zaciatokRiadku++;
            zaciatokStlpca++;
            if (prvyZnak == hrac.getZnak() && prvyZnak == druhyZnak && prvyZnak == tretiZnak) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @TODO - spojiť výhry dokopy do jednej metódy
     * @TODO - vyhraDiagonala()
     * @TODO - výhry len s tromi znakmi nie celými riadkami a tak
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
        if (riadok > this.riadok || stlpec > this.stlpec) {
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
     */
    public void setPolicka() {
        for (int aktualnyRiadok = 0; aktualnyRiadok < this.riadok; aktualnyRiadok++) {
            for (int aktualnyStlpec = 0; aktualnyStlpec < this.stlpec; aktualnyStlpec++) {
                this.hraciaPlocha[aktualnyRiadok][aktualnyStlpec] = '.';
            }
        }
    }
    
    /**
     * Vypíšeme hraciu plochu
     * 
     * @TODO - pomenovať si premennú a nejako normálne
     */
    public void vypisPlochu() {
        int a = 1;
        for (char[] plocha: this.hraciaPlocha) {
            for (char aktualny: plocha) {
                if (a % this.stlpec == 0) {
                    System.out.println(aktualny);
                } else {
                    System.out.print(aktualny);
                }
                a++;
            }
        }
    }
}
