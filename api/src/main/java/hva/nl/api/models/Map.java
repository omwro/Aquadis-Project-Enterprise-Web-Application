package hva.nl.api.models;

import javax.persistence.*;

@Entity
@NamedQuery(name ="find_all_maps", query="select m from Map m")
public class Map {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    private String keyname;
    private int value;

    public Map() {
    }

    public Map(String keyname, int value) {
        this.keyname = keyname;
        this.value = value;
    }

    public String getKeyname() {
        return keyname;
    }

    public int getValue() {
        return value;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
