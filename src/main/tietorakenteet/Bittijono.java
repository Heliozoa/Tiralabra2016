
package tietorakenteet;

import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;

/**
 *  Bittijono sisältää boolean-taulukossa jonon bittejä, jossa true == 1 ja false == 0.
 *  Jonoon lisätään eripituisia koodeja, jonka takia lisäys-metodi vastaanottaa longin ja tiedon siitä,
 *  montako bittiä longista halutaan lisätä. (Muuten ei voi tietää, montako etunollaa on tarkoitus laskea mukaan)
 */
public class Bittijono {
    
    private boolean[] bitit;
    private int alku;
    private int loppu;
    private final int alkukoko = 16;
    
    public Bittijono() {
        bitit = new boolean[alkukoko];
        alku = 0;
        loppu = 0;
    }
    
    /**
     *  Lisää k:n lkm vähäisintä bittiä. 
     *
     *  @see    kasvata
     *  @param  k   Luku jonka bittejä lisätään.
     *  @param  lkm Montako bittiä lisätään.
     */
    public void lisaa(long k, int lkm){
        int osoitin = 0x1;
        osoitin = osoitin << lkm-1;
        for(int i = 0; i < lkm; i++){
            if(loppu == bitit.length){
                kasvata();
            }
            bitit[loppu] = (k & osoitin) != 0;
            osoitin = osoitin >> 1;
            loppu++;
        }
    }
    
    /**
     *  Poistaa jonosta tavun, eli ensimmäiset 8 bittiä.
     *  Jos jono on lyhyempi kuin 8 bittiä, loput biteistä (loppupäästä) merkataan nolliksi.
     *
     *  @return Jonosta poistetut bitit.
     */
    public byte poistaTavu(){
        int tulos = 0x0;
        int osoitin = 0x1;
        osoitin = osoitin << 7;
        for(int i = 0; i < 8; i++){
            if(!tyhja()){
                if(bitit[alku]){
                    tulos = tulos | osoitin;
                }
                alku++;
            }
            osoitin = osoitin >> 0x1;
        }
        return (byte)tulos;
    }
    
    /**
     *  @return Onko jono tyhjä.
     */
    public boolean tyhja(){
        return alku == loppu;
    }
    
    private int poistaBitti() throws NoSuchElementException{
        if(tyhja()) throw new NoSuchElementException();
        if(bitit[loppu]){
            return 1;
        }else{
            return 0;
        }
    }
    
    private void kasvata(){
        boolean[] uusi = new boolean[bitit.length * 2];
        for(int i = 0; i < bitit.length; i++){
            uusi[i] = bitit[i];
        }
        bitit = uusi;
    }
    
    @Override
    public String toString(){
        String s = "";
        for(int i = alku; i < loppu; i++){
            if(bitit[i]){
                s += 1;
            } else {
                s += 0;
            }
        }
        return s;
    }
}