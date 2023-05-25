package tn.soretras.contestmanager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Contestannounce.
 */
@Document(collection = "contestannounce")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contestannounce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("announce_text")
    private String announceText;

    @NotNull
    @Field("startdate")
    private LocalDate startdate;

    @NotNull
    @Field("limitdate")
    private LocalDate limitdate;

    @DBRef
    @Field("idsgeneralrules")
    private Generalrules idsgeneralrules;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Contestannounce id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnounceText() {
        return this.announceText;
    }

    public Contestannounce announceText(String announceText) {
        this.setAnnounceText(announceText);
        return this;
    }

    public void setAnnounceText(String announceText) {
        this.announceText = announceText;
    }

    public LocalDate getStartdate() {
        return this.startdate;
    }

    public Contestannounce startdate(LocalDate startdate) {
        this.setStartdate(startdate);
        return this;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getLimitdate() {
        return this.limitdate;
    }

    public Contestannounce limitdate(LocalDate limitdate) {
        this.setLimitdate(limitdate);
        return this;
    }

    public void setLimitdate(LocalDate limitdate) {
        this.limitdate = limitdate;
    }

    public Generalrules getIdsgeneralrules() {
        return this.idsgeneralrules;
    }

    public void setIdsgeneralrules(Generalrules generalrules) {
        this.idsgeneralrules = generalrules;
    }

    public Contestannounce idsgeneralrules(Generalrules generalrules) {
        this.setIdsgeneralrules(generalrules);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contestannounce)) {
            return false;
        }
        return id != null && id.equals(((Contestannounce) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contestannounce{" +
            "id=" + getId() +
            ", announceText='" + getAnnounceText() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", limitdate='" + getLimitdate() + "'" +
            "}";
    }
}
