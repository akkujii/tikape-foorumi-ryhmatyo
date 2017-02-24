package tikape.runko.domain;

public class Aihealue {

    private Integer aihe_id;
    private String nimi;

    public Aihealue(Integer id, String nimi) {
        this.aihe_id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return aihe_id;
    }

    public void setId(Integer id) {
        this.aihe_id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
