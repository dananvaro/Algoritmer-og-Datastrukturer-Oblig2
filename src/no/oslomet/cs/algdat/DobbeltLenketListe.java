package no.oslomet.cs.algdat;

////////////////// class DobbeltLenketListe /////////////////////////////
/**
 * Jawid Mohammadi, S315591, s315591@oslomet.no
 * Rahmat Faribarz, s235799, s235799@oslomet.no
 * Danan Subramaniam, S321422, s321422@oslomet.no
 * Hajhain Nirmalan, S331425, s331425@oslomet.no
 * Mohamed Yusuf Nur, S333725, s333725@oslomet.no
 */

import java.util.*;


////////////////// Oppgave1 //////////////////////////////
public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */

    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den forste i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //throw new NotImplementedException();
    }

    public DobbeltLenketListe(T[] a) {
        //dersom arrayen er tom kastes et unntak
        if (a == null) {
            throw new NullPointerException("tabellen er tom");
        }
        //Dette er en peker, som ble brukt til a sette neste og forige node.
        Node<T> tmp = null;

        for (Integer i = 0; i < a.length; i++) {
            if (a.length == 1) {
                if (a[i] != null) {
                    Node<T> node = new Node<T>(a[i]);
                    hode = node;
                    hale = node;
                    antall++;
                }
            } else if (a[i] != null) {
                if (hode == null) {
                    Node<T> node = new Node<T>(a[i]);
                    hode = node;
                    hale = node;
                    hode.forrige = null;
                    hode.neste = null;
                    tmp = node;
                    antall++;
                }

                else {
                    Node<T> node = new Node<T>(a[i]);
                    node.forrige = tmp;
                    tmp.neste = node;
                    tmp = node;
                    hale = node;
                    antall++;
                }
            }
        }
    }

    ////////////////// Oppgave2 //////////////////////////////
    @Override
    public String toString() {
        //sjekker om listen er tom
        if (tom()){
            return "[]";
        }
        //dersom hode eller halens verdi er null sa er listen tom
        else if(hode.verdi == null){
            return "[]";
        }
        //starter pa hode
        Node forste = hode;
        StringBuilder ut = new StringBuilder("["+forste.verdi);
        forste = forste.neste;
        //fortsetter lokken til forste ikke er null
        while(forste != null){
            ut.append(", "+forste.verdi);
            forste = forste.neste;
        }
        ut.append("]");
        return ut.toString();
    }

    public String omvendtString() {
        //sjekker om listen er tom
        if (tom()){
            return "[]";
        }else if(hode.verdi == null){
            return "[]";
        }
        //starter pa halen
        Node sist = hale;
        StringBuilder ut = new StringBuilder("["+sist.verdi);
        sist = sist.forrige;
        while(sist != null){
            ut.append(", "+sist.verdi);
            sist = sist.forrige;
        }
        ut.append("]");
        return ut.toString();
    }

    ////////////////// Oppgave3 //////////////////////////////
    private Node<T> finnNode(int indeks){

        //Hvis indeksen er mindre enn antall/2
        // starter vi fra hode
        if(indeks <= (antall/2)){
            //Starter fra hode ogsa gar vi til neste
            Node p = hode;

            //For loop som gar til neste
            for(int i =0; i < indeks; i++){
                p = p.neste;
            }

            return p;
        }

        // Hvis indeks er storre enn antall/2 starter vi ved halen
        else{
            //Starter fra halen
            Node q = hale;

            //For loop som gar til forrige
            for(int i = antall; i <= (antall - indeks); i--){

                //Gar til forrige node
                q = q.forrige;
            }

            return q;
        }
    }

    ////////////////// Oppgave3a //////////////////////////////
    @Override
    public T hent(int indeks) {

        //Kontrollerer ideksen
        indeksKontroll(indeks,false);

        //Returnerer verdien til finnNode
        Node<T> hentNode = finnNode(indeks);

        return hentNode.verdi;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        //Kaster en Exception hvis verdien er lik null
        if(nyverdi == null){
            throw new NullPointerException("Ny verdi kan ikke vaere lik null");
        }

        //Skjekker indeksen
        indeksKontroll(indeks, false);

        //Finner node og lagrer den gamle verdien
        Node p = finnNode(indeks);
        T gammel = (T) p.verdi;

        //Setter p.verdi til nye verdien og plusser pa endringer
        p.verdi = nyverdi;
        endringer++;
        return gammel;

    }

    ////////////////// Oppgave3b //////////////////////////////
    private void fratilKontroll(int fra, int til, int antall){

        //Skjekker om fra er storre enn 0 (negativ)
        if(fra < 0){
            throw new IndexOutOfBoundsException("fra ma vaere storre enn 0");
        }
        //Skjekker om siste er storre enn antall
        if(til > antall){
            throw new IndexOutOfBoundsException("til kan ikke vaere storre enn antall");
        }
        //Skjekker om fra er storre enn til
        if(fra>til){
            throw new IllegalArgumentException("fra kan ikke vaere storre enn til");
        }
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(fra,til,antall);

        //Lager en DobbeltLenketLoste
        DobbeltLenketListe<T> subliste = new DobbeltLenketListe<>();

        //Skjekker om den er tom, hvis den er tom returnerer den subliste
        if(tom()){
            return subliste;
        }

        //Starter fra hode
        Node p = hode;

        //Legger inn vedier hvis i er storre eller lik fra
        for(int i = 0; i<til; i++){
            if(i >= fra){
                subliste.leggInn((T) p.verdi);
            }
            p = p.neste;
        }
        return subliste;
    }

    @Override
    public int antall() {
        return antall;

    }

    @Override
    public boolean tom() {

        return (antall==0);
    }

    @Override
    public boolean leggInn(T verdi) {
      if(verdi == null){
          throw new NullPointerException("Ma ha en verdi");
      }
       //listen er tom
       if(tom()){
           Node<T> nynode = new Node<T>(verdi);
           hode = hale = nynode;
           antall++;
           endringer++;
           return true;
       }else {
           //noden leeges i slutten av listen
           Node<T> nynode = new Node<T>(verdi);
           Node gammel_siste = hale;
           //neste for gammel var null
           gammel_siste.neste = nynode;
           nynode.forrige = gammel_siste;
           hale = nynode;
           antall++;
           endringer++;
           return true;
       }
    }

    ////////////////// Oppgave4 //////////////////////////////
    @Override
    public int indeksTil(T verdi)
    {
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall; indeks++, p = p.neste)
        {
            if (p.verdi.equals(verdi)) return indeks;
        }

        return -1;
    }


    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }


    ////////////////// Oppgave5 //////////////////////////////
    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);

        if (tom())                              // tom liste
        {
            hode = hale = new Node<>(verdi, null, null);
        }
        else if (indeks == 0)                   // ny verdi forrest
        {
            hode = hode.forrige = new Node<>(verdi, null, hode);
        }
        else if (indeks == antall)              // ny verdi bakerst
        {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        else                                    // ny verdi pa plass indeks
        {
            Node<T> p = finnNode(indeks);     // ny verdi skal til venstre for p
            p.forrige = p.forrige.neste = new Node<>(verdi, p.forrige, p);
        }

        antall++;            // ny verdi i listen
        endringer++;   // en endring i listen
    }

    ////////////////// Oppgave6 //////////////////////////////
    @Override
    public boolean fjern(T verdi) {
        //Med Iterator kan vi kalle pa hasNext() metoden
        Iterator<T> iterator = iterator();


        //Bruker hasNext for a bla igjennom
        while (iterator.hasNext()){

            //Hvis verdien er lik .next() sin fjerner vi verdien og returnerer true (Velykket)
            if(verdi.equals(iterator.next())){
                    iterator.remove();
                    return true;
            }
        }

        //Plusser pa antall endringer
        endringer++;
        //Returnerer false (Mislykket)
        return false;
    }

    @Override
    public T fjern(int indeks) {

        //Ideks kontrollerer
        indeksKontroll(indeks,false);

        //Lager en variabel sa vi kan lagre verdien
        T midlertidig;

        //Hvis vi skal fjerne hode kjorer den gjennom denne if setningen
        if(indeks == 0){

            //Lagrer gamle verdi
            midlertidig = hode.verdi;

            //Hvis antallet er lik 1 setter vi hode og halen lik null
            if(antall==1){
                hode = hale = null;
            }

            //Setter nye hode lik hode sin neste og setter gamle lik null
            else {
                hode = hode.neste;
                hode.forrige = null;
            }

        }

        //Kjorer hvis vi fjerner noe imellom hode og halen eller halen
        else {

            //Finner noden til foran den vi skal fjerne og den vi skal fjerne
            Node<T> nodeForan = finnNode((indeks - 1));
            Node<T> fjernNode = finnNode(indeks);
            //Lagrer den gamle verdien
            midlertidig = fjernNode.verdi;

            //Kjorer hvis vi skal fjerne halen
            if(fjernNode == hale){

                //Setter nye halen lik halen sin forrige og setter gamle halen lik null
                hale = hale.forrige;
                hale.neste = null;
            }

            //Endrer pekerne
            else {
                nodeForan.neste = fjernNode.neste;
                nodeForan.neste.forrige = nodeForan;

            }
        }

        //Fjerner antall og plusser pa endriger
        antall--;
        endringer++;

        //Returnerer verdien vi fjernet
        return midlertidig;
    }
    ///////////////Oppgave 7/////////////////
    @Override
    public void nullstill() {

        Node start = hode;
        start.verdi = null;
        start = start.neste;
        //Dersom start.neste ikke er null sa gar vi videre i listen
        while(start.neste!=null){
            //setter alle pekerne og dens verdi til null
            start.forrige.neste = null;
            start.forrige = null;
            start.verdi = null;
            //gar til neste node i listem
            start = start.neste;
            //oker endringer
            endringer++;
        }
        //setter alle pekerne og deres verdi til null for den
        //siste noden
        start.verdi = null;
        start.forrige.neste = null;
        start.forrige = null;
        hode.verdi = null;
        //antall er naa lik 0
        antall = 0;
/*
        {
        //Virket som om den ovre algoritmen var raskere enn denne
            Node starten = hode;
            starten.verdi = null;
            //gaar igjennom alle verdiene og nuller dem
            for(int i =0; i<=antall; i++){
                fjern(i);
            }
            //setter antall lik 0
            antall = 0;
        }
*/
    }

    ////////////////// Oppgave8 //////////////////////////////
    @Override
    public Iterator<T> iterator() {

        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // p starter pa den forste i listen
            fjernOK = false;  // blir sann nar next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne = finnNode(indeks);  // noden med oppgitt indeks;
            fjernOK = false;  // blir sann nar next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;
        }

        @Override
        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");

            T tempverdi = denne.verdi;
            denne = denne.neste;

            fjernOK = true;

            return tempverdi;
        }

        ////////////////// Oppgave9 //////////////////////////////
        @Override
        public void remove(){
            if(!fjernOK){
                throw new IllegalStateException();
            }
            if(endringer != iteratorendringer){
                new ConcurrentModificationException();
            }
            fjernOK = false;
            Node <T> p = null;

            if (antall == 1)    // Nar det bare er en node i listen
            {
                hode = null;
                hale = null;
            }
            else if(denne == null){ // Nar du skal fjerne den siste
                hale = hale.forrige;
                hale.neste = null;
            }
            else if(denne.forrige==hode){ // Nar du skal fjerne den forste
                hode=denne;
                hode.forrige = null;
            }
            else{
                //Nar du skal fjerne noden som ligger mellom hode og hale
                p = denne.forrige;
                p.forrige.neste = p.neste;
                p.neste.forrige = p.forrige;
            }
            iteratorendringer++;
            endringer++;
            antall--;
        }
    } // class DobbeltLenketListeIterator

    ////////////////// Oppgave10 //////////////////////////////
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        for (int n = liste.antall(); n > 0; n--)
        //gar igjennom elementene i lista
        {
            int m = 0;
            Iterator<T> iterator = liste.iterator();

            T minverdi = iterator.next();
            for (int i = 1; i < n; i++)
            {
                T verdi = iterator.next();
                //sammenlikner menverdi og verdi
                //minverdi vil inneholde minste verdi og dens indeks
                if (c.compare(minverdi,verdi) > 0)
                {
                    minverdi = verdi;
                    m = i;
                }
            }
            //fjerner minverdi fra listen
            liste.fjern(minverdi);
            //legger til minverdi i slutten av listen
            liste.leggInn(minverdi);
        }
    }
} // class DobbeltLenketListe



