
package lzw;

import tiedosto.Tiedosto;
import tietorakenteet.Sanakirja;
import tietorakenteet.Tavujono;

import java.io.IOException;

/**
 *  Hoitaa tiedon pakkaamisen. Vastaanottaa Tiedosto- ja Sanakirja-luokkien oliot joiden avulla pakkaus hoidetaan.
 *  
 *  Toimintaperiaate lyhyesti:
 *      Algoritmilla on aluksi sanakirja joka sisältää kaikki mahdolliset 256 tavua.
 *      Algoritmi tarkastelee aina tavujonoa kerrallaan, joka on vähintään 2 tavua pitkä.
 *      Jos tavujono löytyy jo sanakirjasta, algoritmi ottaa jonoon mukaan seuraavan tavun.
 *      Jos tavujonoa ei löydy sanakirjasta, se lisätään sanakirjaan ja algoritmi kirjoittaa muistiin sanakirjan koodin,
 *      joka vastaa tavujonoa ilman uusinta tavua.
 *      Koodit tallennetaan kahden tavun pituisina. Tällöin pakattu tiedosto koostuu tavupareista, joista jokainen on koodi jota vastaa jokin tavujono.
 *      Näin lopputuloksena on tiedosto, jossa esim 2 tavua pitkä koodi 897 voi merkitä 30 tavua pitkää tavujonoa.
 *      Avausalgoritmi osaa muodostaa samalla periaatteella sanakirjan lennosta avatessa, siis sanakirjaa ei tarvitse mitenkään
 *      tallentaa muistiin ja tuloksena on pakattu tiedosto jossa on vähemmän tavuja.
 */
public class Pakkaaja {
    private Tiedosto t;
    private Sanakirja sk;
    
    /**
     *  @param tiedosto    Tiedostosta saadaan tieto joka halutaan pakata.
     *  @param sanakirja   Sanakirja jota käytetään tiedon koodaamiseen.
     */
    public Pakkaaja(Tiedosto tiedosto, Sanakirja sanakirja) {
        t = tiedosto;
        sk = sanakirja;
    }
    
    /**
     *  Koodaa Tiedoston t sisällön byte-tyypin taulukkoon, jonka se antaa tiedostolle kirjoitettavaksi muistiin.
     *
     *  @param  kohde   Polku tiedostoon, jonne pakattu tieto kirjoitetaan.
     */
    public void pakkaa(String kohde) throws IOException{
        try{
            if(t.loppu()) return;
            Tavujono tavut = new Tavujono();
            koodaaJonoon(tavut);
            Tiedosto.kirjoita(tavut, kohde);
        } finally {
            t.sulje();
        }
    }
    
    /**
     *  Koodaa Tiedoston t sisällön jonoon ja muodostaa samalla sanakirjan.
     *  
     *  @param  tavut   Jono johon tavut lisätään.
     */
    private void koodaaJonoon(Tavujono tavut) throws IOException{
        Tavujono jono = new Tavujono();
        jono.lisaa(lue());
        byte seuraava;
        
        boolean edellinenLoytyySanakirjasta = false;
        while(!t.loppu()){
            seuraava = lue();
            if(sk.sisaltaa(jono, seuraava)){
                jono.lisaa(seuraava);
                edellinenLoytyySanakirjasta = false;
            } else {
                tavut.lisaaInt(sk.hae(jono));
                sk.lisaa(jono, seuraava);
                jono.tyhjenna();
                jono.lisaa(seuraava);
                edellinenLoytyySanakirjasta = true;
            }
        }
        
        if(edellinenLoytyySanakirjasta){
            tavut.lisaaInt(sk.hae(jono));
        } else {
            byte vika = jono.poistaLopusta();
            Tavujono temp = new Tavujono();
            temp.lisaa(vika);
            tavut.lisaaInt(sk.hae(jono));
            tavut.lisaaInt(sk.hae(temp));
        }
    }
    
    /*
     *  Lukee seuraavan tavun Tiedostosta t.
     *
     *  @return Tiedostosta luettu seuraava tavu.
     */
    private byte lue() throws IOException{
        return (byte) t.lue();
    }
}