package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Contestfield} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContestfieldDTO implements Serializable {

    private String id;

    @NotNull
    private Integer reference;

    private Boolean mandatory;

    @NotNull
    private String fopconstraint;

    @NotNull
    private String fvalue;

    private String sopconstraint;

    private String svalue;

    private String logic;

    @NotNull
    private String ctype;

    @NotNull
    private String cname;

    private ContestDTO idcontest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getFopconstraint() {
        return fopconstraint;
    }

    public void setFopconstraint(String fopconstraint) {
        this.fopconstraint = fopconstraint;
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    public String getSopconstraint() {
        return sopconstraint;
    }

    public void setSopconstraint(String sopconstraint) {
        this.sopconstraint = sopconstraint;
    }

    public String getSvalue() {
        return svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public ContestDTO getIdcontest() {
        return idcontest;
    }

    public void setIdcontest(ContestDTO idcontest) {
        this.idcontest = idcontest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContestfieldDTO)) {
            return false;
        }

        ContestfieldDTO contestfieldDTO = (ContestfieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contestfieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContestfieldDTO{" +
            "id='" + getId() + "'" +
            ", reference=" + getReference() +
            ", mandatory='" + getMandatory() + "'" +
            ", fopconstraint='" + getFopconstraint() + "'" +
            ", fvalue='" + getFvalue() + "'" +
            ", sopconstraint='" + getSopconstraint() + "'" +
            ", svalue='" + getSvalue() + "'" +
            ", logic='" + getLogic() + "'" +
            ", ctype='" + getCtype() + "'" +
            ", cname='" + getCname() + "'" +
            ", idcontest=" + getIdcontest() +
            "}";
    }
}
