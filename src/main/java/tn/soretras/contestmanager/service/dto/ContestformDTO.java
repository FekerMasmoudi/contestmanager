package tn.soretras.contestmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tn.soretras.contestmanager.domain.Contestform} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContestformDTO implements Serializable {

    private String id;

    private ContestDTO contest;

    private UserDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContestDTO getContest() {
        return contest;
    }

    public void setContest(ContestDTO contest) {
        this.contest = contest;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContestformDTO)) {
            return false;
        }

        ContestformDTO contestformDTO = (ContestformDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contestformDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContestformDTO{" +
            "id='" + getId() + "'" +
            ", contest=" + getContest() +
            ", user=" + getUser() +
            "}";
    }
}
