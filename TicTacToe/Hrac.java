/**
 * Táto trieda je trieda, ktorá obsahuje základné informácie o danom hráčovi.  
 * 
 * @author Patrik Ištvanko
 * @version 1.0.0
 */
public class Hrac {
    private char znak;
    private int pocetVyhier;
    
    /**
     * @param znak konkrétny znak, ktorým chce daný hráč hrať
     */
    public Hrac(char znak) {
        this.znak = znak;
        this.pocetVyhier = 0;
    }
    
    /**
     * @return char vráti znak hráča
     */
    public char getZnak() {
        return this.znak;
    }
    
    /**
     * @return int vráti počet výhier hráča
     */
    public int getPocetVyhier() {
        return this.pocetVyhier;
    }
    
    /**
     * Pripočíta jednu výhru hráčovi.
     */
    public void pridajVyhru() {
        this.pocetVyhier++;
    }
    
    /**
     * Vyresetuje výhry hráčovi.
     */
    public void resetujVyhry() {
        this.pocetVyhier = 0;
    }
}
