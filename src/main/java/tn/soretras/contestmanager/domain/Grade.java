package tn.soretras.contestmanager.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Grade.
 */
@Document(collection = "grade")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("designation")
    private String designation;

    @DBRef
    @Field("ideducationlevel")
    private Educationlevel ideducationlevel;

    @DBRef
    @Field("idcertificate")
    private Certificate idcertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Grade id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Grade designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Educationlevel getIdeducationlevel() {
        return this.ideducationlevel;
    }

    public void setIdeducationlevel(Educationlevel educationlevel) {
        this.ideducationlevel = educationlevel;
    }

    public Grade ideducationlevel(Educationlevel educationlevel) {
        this.setIdeducationlevel(educationlevel);
        return this;
    }

    public Certificate getIdcertificate() {
        return this.idcertificate;
    }

    public void setIdcertificate(Certificate certificate) {
        this.idcertificate = certificate;
    }

    public Grade idcertificate(Certificate certificate) {
        this.setIdcertificate(certificate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grade)) {
            return false;
        }
        return id != null && id.equals(((Grade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
