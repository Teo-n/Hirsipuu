package hirsi;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hirsipuu {
    public static final String[] SANAT = {
            "HIENO", "HOMMA", "PELAAJA", "HAUSKA", "PELI", "MILLOIN", "UUDESTAAN"
    };

    public static final Random RANDOM = new Random();

    public static final int maksimiVirheet = 5; //Maksimimäärä virheitä, joita käyttäjä saa tehdä.

    private String loydettavaSana; //Sana, joka pitää arvata.

    private char[] sanaLoytyi; //Tallennetaan käyttäjän oikean sanan arvaamisen edistyminen taulukkoon.

    private int virheidenMaara; //Käyttäjän tekemät virheet.

    private ArrayList<String> kirjaimet = new ArrayList<>(); //Kirjaimet, jotka käyttäjä on jo syöttänyt.

    private String seuraavaSana() {
        return SANAT[RANDOM.nextInt(SANAT.length)];
    } //Valitaan satunnainen sana.
    public void uusiPeli() { //Aloitetaan uusi peli
        virheidenMaara = 0; //Käyttäjän tekemät virheet nollaan
        kirjaimet.clear(); //Nollataan myös käyttäjän syöttämät kirjaimet
        loydettavaSana = seuraavaSana(); //Uusi löydettävä sana

    sanaLoytyi = new char[loydettavaSana.length()]; //Alustetaan "löydetty" sana

    for (int i = 0; i < sanaLoytyi.length; i++) {
        sanaLoytyi[i] = '_'; //Loydettävän sanan pituudelta alaviivoja merkkaamaan käyttäjälle sanan pituuden
      }
    }

    public boolean sanaLoytyi() {
        //Muutetaan sanalöytyi-taulukko merkkijonoksi ja verrataan arvattavaan sanaan
        return loydettavaSana.contentEquals(new String(sanaLoytyi));
    }
    private void syota(String c) { //Päivittää sanan, kun käyttäjä on syöttänyt kirjaimen
        if (!kirjaimet.contains(c)) { //Päivitetään ainoastaan jos kirjain ei ole syötetty jo aiemmin
            if (loydettavaSana.contains(c)) { //tarkistetaan jos löydettävässä sanassa on syötetty kirjain
                int indeksi = loydettavaSana.indexOf(c);
                //jos on, laitetaan _ tilalle syötetty kirjain

                while(indeksi >=0) {
                    sanaLoytyi[indeksi] = c.charAt(0);
                    indeksi = loydettavaSana.indexOf(c, indeksi + 1);
                }
            }
            else {
                virheidenMaara++; //jos syötetty kirjain ei ole sanassa, lisätään virhe
            }
            // Käyttäjän syöttämä kirjain on käytetty
            kirjaimet.add(c);
        }
    }

    private String loydetynSananSisalto() { //Palauttaa sanan tämänhetkisen sisällön/tilan
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sanaLoytyi.length; i++) {
            builder.append(sanaLoytyi[i]);

            if (i < sanaLoytyi.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
    public void peli() {
        try (Scanner s = new Scanner(System.in)) {
            /*Pelataan niin kauan, kun virheiden määrä on pienempi kuin maksimimäärä virheitä, tai kunnes
            käyttäjä löytää sanan
             */
            while (virheidenMaara < maksimiVirheet) {
                System.out.println("\nSyötä kirjain: ");
                //Otetaan seuraava syöte käyttäjältä
                String str = s.next();

                //Otetaan ainoastaan ensimmäinen kirjain syötteestä
                if(str.length() >1) {
                    str = str.substring(0, 1);
                }
                //Päivitetään sana
                syota(str);

                System.out.println("\n" + loydetynSananSisalto());

                if (sanaLoytyi()) { //Jos löytyi oikea sana, pysäytetään toiminta
                    System.out.println("\nLöysit oikean sanan!");
                    break;
                }
                //Jos ei löytynyt, näytetään jäljellä olevat yritykset vähentämällä tekemät virheet maksimista
                else {
                    System.out.println("\n=> Yrityksiä jäljellä: " + (maksimiVirheet - virheidenMaara));
                }
            }
            //Jos virheiden määrä saavuttaa maksimin, käyttäjä häviää. Kerrotaan oikea sana
            if (virheidenMaara == maksimiVirheet) {
                System.out.println("\nHävisit!");
                System.out.println("=> Oikea sana oli: " + loydettavaSana);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Hirsipuu :D");
        Hirsipuu hirsipuu = new Hirsipuu();
        hirsipuu.uusiPeli();
        hirsipuu.peli();
    }
}
