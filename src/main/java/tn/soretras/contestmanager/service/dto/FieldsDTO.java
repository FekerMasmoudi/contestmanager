package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import tn.soretras.contestmanager.domain.enumeration.etype;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Fields} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldsDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private etype ftype;

    private String fvalue;

    private ContestformDTO contestform;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public etype getFtype() {
        return ftype;
    }

    public void setFtype(etype ftype) {
        this.ftype = ftype;
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    public ContestformDTO getContestform() {
        return contestform;
    }

    public void setContestform(ContestformDTO contestform) {
        this.contestform = contestform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldsDTO)) {
            return false;
        }

        FieldsDTO fieldsDTO = (FieldsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldsDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", ftype='" + getFtype() + "'" +
            ", fvalue='" + getFvalue() + "'" +
            ", contestform=" + getContestform() +
            "}";
    }
}
