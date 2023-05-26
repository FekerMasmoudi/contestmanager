package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Generalrules} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GeneralrulesDTO implements Serializable {

    private String id;

    @NotNull
    private String designation;

    @NotNull
    private LocalDate effectdate;

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

    public LocalDate getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(LocalDate effectdate) {
        this.effectdate = effectdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeneralrulesDTO)) {
            return false;
        }

        GeneralrulesDTO generalrulesDTO = (GeneralrulesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, generalrulesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeneralrulesDTO{" +
            "id='" + getId() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", effectdate='" + getEffectdate() + "'" +
            "}";
    }
}
