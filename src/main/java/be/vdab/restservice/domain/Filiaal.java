package be.vdab.restservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import javax.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;


@Entity
@Table(name = "filialen")
//@XmlRootElement    //om data in xml te zetten ipv json
//@XmlAccessorType(XmlAccessType.FIELD)  //om data in xml te zetten ipv json
public class Filiaal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @NotBlank
    private String gemeente;
    @NotNull @PositiveOrZero
    private BigDecimal omzet;

    public Filiaal(String naam, String gemeente, BigDecimal omzet) {
        this.naam = naam;
        this.gemeente = gemeente;
        this.omzet = omzet;
    }

    protected Filiaal() {};

    /*normaal stuur je naar browser request zonder id, als je iets wilt updaten, heeft hij het id ook nodig
    * dus we gaan een methode maken die als PUT request gebeurt, het ook mogelijk is om het filiaal mét
    * de parameter id te gebruiken*/
    public Filiaal withId(long id) {
        var filiaalMetId = new Filiaal(naam, gemeente, omzet);
        filiaalMetId.id = id;
        return filiaalMetId;
    }

    public long getId() {
        return id;
    }
    public String getNaam() {
        return naam;
    }
    public String getGemeente() {
        return gemeente;
    }
    public BigDecimal getOmzet() {
        return omzet;
    }


}
