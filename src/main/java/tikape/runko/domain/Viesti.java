package tikape.runko.domain;

import java.sql.Date;

public class Viesti {
    private Integer viesti_id;
    private Integer viestiketju_id;
    private String lahettaja;
    private Date lahetetty;
    private String sisalto;

    public Viesti(Integer viesti_id, Integer viestiketju_id, String lahettaja, Date lahetetty, String sisalto) {
        this.viesti_id = viesti_id;
        this.viestiketju_id = viestiketju_id;
        this.lahettaja = lahettaja;
        this.lahetetty = lahetetty;
        this.sisalto = sisalto;
    }

    public Integer getViesti_id() {
        return viesti_id;
    }

    public void setViesti_id(Integer viesti_id) {
        this.viesti_id = viesti_id;
    }

    public Integer getViestiketju_id() {
        return viestiketju_id;
    }

    public void setViestiketju_id(Integer viestiketju_id) {
        this.viestiketju_id = viestiketju_id;
    }

    public String getLahettaja() {
        return lahettaja;
    }

    public void setLahettaja(String lahettaja) {
        this.lahettaja = lahettaja;
    }

    public Date getLahetetty() {
        return lahetetty;
    }

    public void setLahetetty(Date lahetetty) {
        this.lahetetty = lahetetty;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
}
