package tn.soretras.contestmanager.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Generalrules.
 */
@Document(collection = "generalrules")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Generalrules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("designation")
    private String designation;

    @NotNull
    @Field("effectdate")
    private LocalDate effectdate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Generalrules id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Generalrules designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getEffectdate() {
        return this.effectdate;
    }

    public Generalrules effectdate(LocalDate effectdate) {
        this.setEffectdate(effectdate);
        return this;
    }

    public void setEffectdate(LocalDate effectdate) {
        this.effectdate = effectdate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Generalrules)) {
            return false;
        }
        return id != null && id.equals(((Generalrules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Generalrules{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", effectdate='" + getEffectdate() + "'" +
            "}";
    }
}
