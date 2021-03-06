
package tietorakenteet;

/**
 *  Yksinkertainen kasvava taulukko.
 */
public class Taulukko<T> {
    private T[] taulukko;
    private int indeksi;
    
    /**
     *  Java ei tykkää luoda geneerisiä taulukoita, joten joudutaan käyttämään mystistä (T[])new Object[koko] pätkää.
     */
    public Taulukko(){
        taulukko = (T[])new Object[16];
        indeksi = 0;
    }
    
    /**
     *  Lisää T-tyyppisen alkion ja kasvattaa tarvittaessa koon kaksinkertaiseksi.
     *
     *  @param  t   Lisättävä alkio.
     */
    public void lisaa(T t){
        taulukko[indeksi] = t;
        indeksi++;
        if(indeksi == taulukko.length){
            kasvata();
        }
    }
    
    /**
     *  Hakee alkion joka on indeksissä i
     *
     *  @param  i   Indeksi, josta halutaan alkio.
     *  @return T-tyyppinen olio.
     */
    public T hae(int i){
        return taulukko[i];
    }
    
    /**
     *  Vaihtaa nykyisen taulukon 2x kokoiseen.
     */
    private void kasvata(){
        T[] uusi = (T[])new Object[indeksi * 2];
        for(int i = 0; i < indeksi; i++){
            uusi[i] = taulukko[i];
        }
        taulukko = uusi;
    }
}