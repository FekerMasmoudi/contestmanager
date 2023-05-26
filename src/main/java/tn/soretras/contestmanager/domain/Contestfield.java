package tn.soretras.contestmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Contestfield.
 */
@Document(collection = "contestfield")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contestfield implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("reference")
    private Integer reference;

    @Field("mandatory")
    private Boolean mandatory;

    @NotNull
    @Field("fopconstraint")
    private String fopconstraint;

    @NotNull
    @Field("fvalue")
    private String fvalue;

    @Field("sopconstraint")
    private String sopconstraint;

    @Field("svalue")
    private String svalue;

    @DBRef
    @Field("idcontest")
    @JsonIgnoreProperties(value = { "idcontestannounce", "idgrade", "idspeciality", "idsector" }, allowSetters = true)
    private Contest idcontest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Contestfield id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getReference() {
        return this.reference;
    }

    public Contestfield reference(Integer reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Boolean getMandatory() {
        return this.mandatory;
    }

    public Contestfield mandatory(Boolean mandatory) {
        this.setMandatory(mandatory);
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getFopconstraint() {
        return this.fopconstraint;
    }

    public Contestfield fopconstraint(String fopconstraint) {
        this.setFopconstraint(fopconstraint);
        return this;
    }

    public void setFopconstraint(String fopconstraint) {
        this.fopconstraint = fopconstraint;
    }

    public String getFvalue() {
        return this.fvalue;
    }

    public Contestfield fvalue(String fvalue) {
        this.setFvalue(fvalue);
        return this;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    public String getSopconstraint() {
        return this.sopconstraint;
    }

    public Contestfield sopconstraint(String sopconstraint) {
        this.setSopconstraint(sopconstraint);
        return this;
    }

    public void setSopconstraint(String sopconstraint) {
        this.sopconstraint = sopconstraint;
    }

    public String getSvalue() {
        return this.svalue;
    }

    public Contestfield svalue(String svalue) {
        this.setSvalue(svalue);
        return this;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    public Contest getIdcontest() {
        return this.idcontest;
    }

    public void setIdcontest(Contest contest) {
        this.idcontest = contest;
    }

    public Contestfield idcontest(Contest contest) {
        this.setIdcontest(contest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contestfield)) {
            return false;
        }
        return id != null && id.equals(((Contestfield) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contestfield{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            ", mandatory='" + getMandatory() + "'" +
            ", fopconstraint='" + getFopconstraint() + "'" +
            ", fvalue='" + getFvalue() + "'" +
            ", sopconstraint='" + getSopconstraint() + "'" +
            ", svalue='" + getSvalue() + "'" +
            "}";
    }
}
