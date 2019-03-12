package org.apz.cc.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Radio.
 */
@Entity
@Table(name = "radio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Radio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "folder", nullable = false)
    private String folder;

    @Column(name = "alias")
    private String alias;

    @Column(name = "ccg")
    private String ccg;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "radio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Folder> folders = new HashSet<>();
    @OneToMany(mappedBy = "radio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CCG> folders = new HashSet<>();
    @OneToMany(mappedBy = "radio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Alias> aliases = new HashSet<>();
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

    public Radio folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getAlias() {
        return alias;
    }

    public Radio alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCcg() {
        return ccg;
    }

    public Radio ccg(String ccg) {
        this.ccg = ccg;
        return this;
    }

    public void setCcg(String ccg) {
        this.ccg = ccg;
    }

    public String getNote() {
        return note;
    }

    public Radio note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public Radio folders(Set<Folder> folders) {
        this.folders = folders;
        return this;
    }

    public Radio addFolder(Folder folder) {
        this.folders.add(folder);
        folder.setRadio(this);
        return this;
    }

    public Radio removeFolder(Folder folder) {
        this.folders.remove(folder);
        folder.setRadio(null);
        return this;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public Set<CCG> getFolders() {
        return folders;
    }

    public Radio folders(Set<CCG> cCGS) {
        this.folders = cCGS;
        return this;
    }

    public Radio addFolder(CCG cCG) {
        this.folders.add(cCG);
        cCG.setRadio(this);
        return this;
    }

    public Radio removeFolder(CCG cCG) {
        this.folders.remove(cCG);
        cCG.setRadio(null);
        return this;
    }

    public void setFolders(Set<CCG> cCGS) {
        this.folders = cCGS;
    }

    public Set<Alias> getAliases() {
        return aliases;
    }

    public Radio aliases(Set<Alias> aliases) {
        this.aliases = aliases;
        return this;
    }

    public Radio addAlias(Alias alias) {
        this.aliases.add(alias);
        alias.setRadio(this);
        return this;
    }

    public Radio removeAlias(Alias alias) {
        this.aliases.remove(alias);
        alias.setRadio(null);
        return this;
    }

    public void setAliases(Set<Alias> aliases) {
        this.aliases = aliases;
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
        Radio radio = (Radio) o;
        if (radio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Radio{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            ", alias='" + getAlias() + "'" +
            ", ccg='" + getCcg() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
