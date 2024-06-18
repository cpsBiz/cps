package com.mobcomms.raising.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class UserAttendanceMissionPK implements Serializable {
    private static final long serialVersionUID = -5235947066519814817L;
    @Column(name = "user_seq", nullable = false)
    private Long userSeq;
    @Column(name = "check_day", nullable = false)
    private Integer checkDay;

    @Override
    public int hashCode() {
        return Objects.hash(userSeq, checkDay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAttendanceMissionPK entity = (UserAttendanceMissionPK) o;
        return Objects.equals(this.userSeq, entity.userSeq) &&
                Objects.equals(this.checkDay, entity.checkDay);
    }
}