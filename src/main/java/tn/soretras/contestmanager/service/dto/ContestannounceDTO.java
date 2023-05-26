package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Contestannounce} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContestannounceDTO implements Serializable {

    private String id;

    @NotNull
    private String announceText;

    @NotNull
    private LocalDate startdate;

    @NotNull
    private LocalDate limitdate;

    @NotNull
    private Boolean status;

    private Set<GeneralrulesDTO> idsgeneralrules = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnounceText() {
        return announceText;
    }

    public void setAnnounceText(String announceText) {
        this.announceText = announceText;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getLimitdate() {
        return limitdate;
    }

    public void setLimitdate(LocalDate limitdate) {
        this.limitdate = limitdate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<GeneralrulesDTO> getIdsgeneralrules() {
        return idsgeneralrules;
    }

    public void setIdsgeneralrules(Set<GeneralrulesDTO> idsgeneralrules) {
        this.idsgeneralrules = idsgeneralrules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContestannounceDTO)) {
            return false;
        }

        ContestannounceDTO contestannounceDTO = (ContestannounceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contestannounceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContestannounceDTO{" +
            "id='" + getId() + "'" +
            ", announceText='" + getAnnounceText() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", limitdate='" + getLimitdate() + "'" +
            ", status='" + getStatus() + "'" +
            ", idsgeneralrules=" + getIdsgeneralrules() +
            "}";
    }
}
