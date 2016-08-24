
package avaus;

import util.Muotoilu;

import java.util.HashMap;

/**
 *  Sanakirja, jota hyödynnetään pakatun tiedoston avaamisessa.
 */
public class AvausSanakirja {
    private HashMap<Integer,String> sanakirja;
    private HashMap<String,Integer> jonot;
    private int koodi;
    private int raja;
    
    public AvausSanakirja() {
        sanakirja = new HashMap<>();
        jonot = new HashMap<>();
        for(int i = 0; i <= 255; i++) {
            sanakirja.put(i,Muotoilu.etunollat(i));
            jonot.put(Muotoilu.etunollat(i),i);
        }
        
        koodi = 256;
        raja = 65536;
    }
    
    /**
     *  Lisää jonon sanakirjaan.
     */
    public void lisaa(String jono){
        if(koodi < raja){
            sanakirja.put(koodi, jono);
            jonot.put(jono,koodi);
            koodi++;
        }
    }
    
    /**
     *  Sisältääkö avaimen.
     */
    public boolean sisaltaa(int avain){
        return sanakirja.containsKey(avain);
    }
    
    /**
     *  Sisältääkö jonon.
     */
    public boolean sisaltaaJonon(String jono){
        return jonot.containsKey(jono);
    }
    
    /**
     *  @return avainta vastaava jono
     */
    public String hae(int avain){
        return sanakirja.get(avain);
    }
    
    /**
     *  @return jonoa vastaava avain
     */
    public int haeKoodi(String jono){
        return jonot.get(jono);
    }
    
    public HashMap<Integer,String> sanakirja(){
        return sanakirja;
    }
    
    public void dump(){
        for(int avain : sanakirja.keySet()){
            if(avain > 255){
                System.out.println(avain+":"+sanakirja.get(avain));
            }
        }
    }
}