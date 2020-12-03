
public class Hrac {
    private char znak;
    
    /**
     * @param znak konkrétny znak, ktorým chce daný hráč hrať
     */
    public Hrac(char znak) {
        this.znak = znak;
    }
    
    public char getZnak() {
        return this.znak;
    }
}
