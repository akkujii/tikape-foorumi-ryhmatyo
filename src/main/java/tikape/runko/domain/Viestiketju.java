package tikape.runko.domain;

public class Viestiketju {

    private Integer ketju_id;
    private Integer aihe_id;
    private String otsikko;

    public Viestiketju(Integer ketju_id, Integer aihe_id, String otsikko) {
        this.ketju_id = ketju_id;
        this.aihe_id = aihe_id;
        this.otsikko = otsikko;
    }

    public Integer getKetjuId() {
        return ketju_id;
    }
    
    public Integer getAiheId() {
        return aihe_id;
    }
    
    public String getOtsikko() {
        return otsikko;
    }

    public void setKetjuId(Integer ketju_id) {
        this.ketju_id = ketju_id;
    }
    
    public void setAiheId(Integer aihe_id) {
        this.aihe_id = aihe_id;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

}
