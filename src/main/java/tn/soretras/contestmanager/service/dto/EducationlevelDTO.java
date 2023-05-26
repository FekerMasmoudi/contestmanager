package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Educationlevel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationlevelDTO implements Serializable {

    private String id;

    @NotNull
    private String designation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationlevelDTO)) {
            return false;
        }

        EducationlevelDTO educationlevelDTO = (EducationlevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, educationlevelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationlevelDTO{" +
            "id='" + getId() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
