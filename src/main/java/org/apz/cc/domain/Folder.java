package org.apz.cc.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Folder.
 */
@Entity
@Table(name = "folder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "folder", nullable = false)
    private String folder;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("folders")
    private Alias alias;

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

    public String getFolder() {
        return folder;
    }

    public Folder folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getNote() {
        return note;
    }

    public Folder note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Alias getAlias() {
        return alias;
    }

    public Folder alias(Alias alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public Radio getRadio() {
        return radio;
    }

    public Folder radio(Radio radio) {
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
        Folder folder = (Folder) o;
        if (folder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), folder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Folder{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
