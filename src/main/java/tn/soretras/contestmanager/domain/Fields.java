package tn.soretras.contestmanager.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tn.soretras.contestmanager.domain.enumeration.etype;

/**
 * A Fields.
 */
@Document(collection = "fields")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("ftype")
    private etype ftype;

    @Field("fvalue")
    private String fvalue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Fields id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Fields name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public etype getFtype() {
        return this.ftype;
    }

    public Fields ftype(etype ftype) {
        this.setFtype(ftype);
        return this;
    }

    public void setFtype(etype ftype) {
        this.ftype = ftype;
    }

    public String getFvalue() {
        return this.fvalue;
    }

    public Fields fvalue(String fvalue) {
        this.setFvalue(fvalue);
        return this;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fields)) {
            return false;
        }
        return id != null && id.equals(((Fields) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fields{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ftype='" + getFtype() + "'" +
            ", fvalue='" + getFvalue() + "'" +
            "}";
    }
}
