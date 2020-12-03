
public class HraciaPlocha {
    private char[][] hraciaPlocha;
    private int stlpec;
    private int riadok;
    private boolean zadaneSpravne;
    
    /**
     * @param riadky počet riadkov hracej plochy
     * @param stlpce počet stĺpcov hracej plochy
     * 
     * Vytvorí hraciu plochu podľa zadaných parametrov.
     * Ak je hracia plocha menšia ako 3x3 alebo väčšia ako 12x12,
     * tak sa vytvorí hracia plocha 3x3.
     */
    public HraciaPlocha(int riadky, int stlpce) {
        this.riadok = riadky;
        this.stlpec = stlpce;
        this.zadaneSpravne = true;
        
        for (int i = 0; i < stlpce; i++) {
            for (int j = 0; j < riadky; j++) {
                if (riadky > 2 && stlpce > 2 && riadky < 13 && stlpce < 13) {
                    this.hraciaPlocha = new char[riadky][stlpce];
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
     * @param riadok konkrétny riadok, ktorý chceme skontrolovať
     * @return String vracia všetky znaky z daného riadku v podobe reťazca
     * 
     * Vracia hodnotu celého riadka v podobe stringu.
     */
    public String getRiadok(int riadok) {
        String znaky = "";
        for (int i = 0; i < this.riadok; i++) {
            znaky += this.hraciaPlocha[riadok][i];
        }
        return znaky;
    }
    
    /**
     * @param stlpec konkrétny stĺpec, ktorý chceme skontrolovať
     * @return String vracia všetky znaky z daného stĺpca v podobe reťazca
     * 
     * Vracia hodnotu celého stĺpca v podobe stringu.
     */
    public String getStlpec(int stlpec) {
        String znaky = "";
        for (int i = 0; i < this.stlpec; i++) {
            znaky += this.hraciaPlocha[i][stlpec];
        }
        return znaky;
    }
    
    /**
     * @param zaciatokRiadku riadok, od ktorého chceme zisťovať diagonálu
     * @param zaciatokStlpca stĺpec, od ktorého chceme zisťovať diagonálu
     * @param jeZLava určuje, z ktorej strany budeme zisťovať diagonálu
     * @return String vracia všetky znaky z diagonály v podobe reťazca
     * 
     * @TODO - ak je viac riadkov ako stĺpcov alebo viac stĺpcov ako riadkov
     * tak treba ešte vyriešiť diagonálu (zistiť o koľko je viac a potom to
     * pripočítať ku tej lokálnej premennej diagonala
     * 
     * Vracia hodnotu celej diagonály v podobe stringu.
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
     * @return boolean vracia hodnotu true, ak sa znakyHraca rovnajú hodnote,
     * z metódy getStlpec()
     * 
     * znakyHraca - reťazec obsahujúci hodnoty stĺpca od konkrétneho hráča
     */
    public boolean vyhraStlpec(Hrac hrac, int stlpec) {
        String znakyHraca = "";
        
        for (int i = 0; i < this.stlpec; i++) {
            znakyHraca += hrac.getZnak();
        }
        
        return znakyHraca.equals(this.getStlpec(stlpec));
    }
    
    /**
     * @param hrac konkrétny hráč, pre ktorého chceme kontrolovať výherný riadok
     * @param riadok konkrétny riadok, ktorý chceme skontrolovať
     * @return boolean vracia hodnotu true, ak sa znakyHraca rovnajú hodnote,
     * z metódy getRiadok()
     * 
     * znakyHraca - reťazec obsahujúci hodnoty riadku od konkrétneho hráča
     */
    public boolean vyhraRiadok(Hrac hrac, int riadok) {
        String znakyHraca = "";
        
        for (int i = 0; i < this.riadok; i++) {
            znakyHraca += hrac.getZnak();
        }
        
        return znakyHraca.equals(this.getRiadok(riadok));
    }
    
    public boolean vyhraDiagonala(Hrac hrac) {
        return false;
    }
    
    /**
     * @TODO - spojiť výhry dokopy do jednej metódy
     * @TODO - vyhraDiagonala()
     * @TODO - výhry len s tromi znakmi nie celými riadkami a tak
     */
    
    /**
     * @param riadok číslo riadku, do ktorého chceme uložiť hodnotu
     * @param stlpec číslo stĺpca, do ktorého chceme uložiť hodnotu
     * @param hrac konkrétny hráč, pre ktorého nastavujeme políčko
     * 
     * @TODO - ak sa už niečo v políčku nachádza, pridať možnosť pre
     * zabranie si políčka (napríklad hodenie mincou alebo kameň, papier, nožnice)
     * 
     * Nastavíme políčko pre daného hráča na konkrétnu pozíciu
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
