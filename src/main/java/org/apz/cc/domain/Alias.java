package org.apz.cc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Alias.
 */
@Entity
@Table(name = "alias")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alias implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotNull
    @Column(name = "folder", nullable = false)
    private String folder;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "alias")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Folder> folders = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("aliases")
    private Radio radio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public Alias alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFolder() {
        return folder;
    }

    public Alias folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getNote() {
        return note;
    }

    public Alias note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public Alias folders(Set<Folder> folders) {
        this.folders = folders;
        return this;
    }

    public Alias addFolder(Folder folder) {
        this.folders.add(folder);
        folder.setAlias(this);
        return this;
    }

    public Alias removeFolder(Folder folder) {
        this.folders.remove(folder);
        folder.setAlias(null);
        return this;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public Radio getRadio() {
        return radio;
    }

    public Alias radio(Radio radio) {
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
        Alias alias = (Alias) o;
        if (alias.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alias.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Alias{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", folder='" + getFolder() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
