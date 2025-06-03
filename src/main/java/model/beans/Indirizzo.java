package model.beans;
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
