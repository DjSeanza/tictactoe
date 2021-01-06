/**
 * Táto trieda je trieda, ktorá obsahuje základné informácie o danom hráčovi.  
 * 
 * @author Patrik Ištvanko
 * @version 1.0.0
 */
public class Hrac {
    private char znak;
    private int pocetVyhier;
    private boolean jeBot;
    
    /**
     * @param znak konkrétny znak, ktorým chce daný hráč hrať
     */
    public Hrac(char znak, boolean jeBot) {
        this.znak = znak;
        this.jeBot = jeBot;
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
     * @return boolean vráti hodnotu true ak je hráč bot
     */
    public boolean getJeBot() {
        return this.jeBot;
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
    
    /**
     * Nastaví hodnotu jeBot.
     */
    public void setBot(boolean jeBot) {
        this.jeBot = jeBot;
    }
}
