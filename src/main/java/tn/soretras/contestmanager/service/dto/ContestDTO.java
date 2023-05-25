package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Contest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContestDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String parent;

    @NotNull
    private String description;

    private ContestannounceDTO idcontestannounce;

    private GradeDTO idgrade;

    private SpecialityDTO idspeciality;

    private SectorDTO idsector;

    private EducationlevelDTO ideducationlevel;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContestannounceDTO getIdcontestannounce() {
        return idcontestannounce;
    }

    public void setIdcontestannounce(ContestannounceDTO idcontestannounce) {
        this.idcontestannounce = idcontestannounce;
    }

    public GradeDTO getIdgrade() {
        return idgrade;
    }

    public void setIdgrade(GradeDTO idgrade) {
        this.idgrade = idgrade;
    }

    public SpecialityDTO getIdspeciality() {
        return idspeciality;
    }

    public void setIdspeciality(SpecialityDTO idspeciality) {
        this.idspeciality = idspeciality;
    }

    public SectorDTO getIdsector() {
        return idsector;
    }

    public void setIdsector(SectorDTO idsector) {
        this.idsector = idsector;
    }

    public EducationlevelDTO getIdeducationlevel() {
        return ideducationlevel;
    }

    public void setIdeducationlevel(EducationlevelDTO ideducationlevel) {
        this.ideducationlevel = ideducationlevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContestDTO)) {
            return false;
        }

        ContestDTO contestDTO = (ContestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContestDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", parent='" + getParent() + "'" +
            ", description='" + getDescription() + "'" +
            ", idcontestannounce=" + getIdcontestannounce() +
            ", idgrade=" + getIdgrade() +
            ", idspeciality=" + getIdspeciality() +
            ", idsector=" + getIdsector() +
            ", ideducationlevel=" + getIdeducationlevel() +
            "}";
    }
}
