package org.apz.cc.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CCG.
 */
@Entity
@Table(name = "ccg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CCG implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ccg", nullable = false)
    private String ccg;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("folders")
    private Radio radio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCcg() {
        return ccg;
    }

    public CCG ccg(String ccg) {
        this.ccg = ccg;
        return this;
    }

    public void setCcg(String ccg) {
        this.ccg = ccg;
    }

    public String getNote() {
        return note;
    }

    public CCG note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Radio getRadio() {
        return radio;
    }

    public CCG radio(Radio radio) {
        this.radio = radio;
        return this;
    }

    public void setRadio(Radio radio) {
        this.radio = radio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCG cCG = (CCG) o;
        if (cCG.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cCG.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CCG{" +
            "id=" + getId() +
            ", ccg='" + getCcg() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
