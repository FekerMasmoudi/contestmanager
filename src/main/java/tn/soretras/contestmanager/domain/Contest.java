package tn.soretras.contestmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Contest.
 */
@Document(collection = "contest")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("parent")
    private String parent;

    @NotNull
    @Field("description")
    private String description;

    @DBRef
    @Field("idcontestannounce")
    @JsonIgnoreProperties(value = { "idsgeneralrules" }, allowSetters = true)
    private Contestannounce idcontestannounce;

    @DBRef
    @Field("idgrade")
    private Grade idgrade;

    @DBRef
    @Field("idspeciality")
    private Speciality idspeciality;

    @DBRef
    @Field("idsector")
    private Sector idsector;

    @DBRef
    @Field("ideducationlevel")
    private Educationlevel ideducationlevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Contest id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Contest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return this.parent;
    }

    public Contest parent(String parent) {
        this.setParent(parent);
        return this;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return this.description;
    }

    public Contest description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contestannounce getIdcontestannounce() {
        return this.idcontestannounce;
    }

    public void setIdcontestannounce(Contestannounce contestannounce) {
        this.idcontestannounce = contestannounce;
    }

    public Contest idcontestannounce(Contestannounce contestannounce) {
        this.setIdcontestannounce(contestannounce);
        return this;
    }

    public Grade getIdgrade() {
        return this.idgrade;
    }

    public void setIdgrade(Grade grade) {
        this.idgrade = grade;
    }

    public Contest idgrade(Grade grade) {
        this.setIdgrade(grade);
        return this;
    }

    public Speciality getIdspeciality() {
        return this.idspeciality;
    }

    public void setIdspeciality(Speciality speciality) {
        this.idspeciality = speciality;
    }

    public Contest idspeciality(Speciality speciality) {
        this.setIdspeciality(speciality);
        return this;
    }

    public Sector getIdsector() {
        return this.idsector;
    }

    public void setIdsector(Sector sector) {
        this.idsector = sector;
    }

    public Contest idsector(Sector sector) {
        this.setIdsector(sector);
        return this;
    }

    public Educationlevel getIdeducationlevel() {
        return this.ideducationlevel;
    }

    public void setIdeducationlevel(Educationlevel educationlevel) {
        this.ideducationlevel = educationlevel;
    }

    public Contest ideducationlevel(Educationlevel educationlevel) {
        this.setIdeducationlevel(educationlevel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contest)) {
            return false;
        }
        return id != null && id.equals(((Contest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parent='" + getParent() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
