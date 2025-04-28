package model.beans;
/*JavaBeans are classes that
encapsulate many objects
into a single object (the
bean). They are
serializable, have a zero-
argument constructor, and
allow access to properties
using getter and setter
methods.
• The name "Bean" was
given to encompass this
standard, which aims to
create reusable software
components for Java*/

public class Indirizzo {
    private String via,citta,provincia;
    private int cap;

    public Indirizzo() {}
    public String getVia(){ return via; }
    public void setVia(String via){ this.via = via; }
    public String getCitta(){ return citta; }
    public void setCitta(String citta){ this.citta = citta; }
    public String getProvincia(){ return provincia; }
    public void setProvincia(String provincia){ this.provincia = provincia; }
    public int getCap(){ return cap; }
    public void setCap(int cap){ this.cap = cap; }
}
