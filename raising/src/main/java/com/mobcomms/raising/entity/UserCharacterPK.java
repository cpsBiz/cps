package com.mobcomms.raising.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class UserCharacterPK implements Serializable {
    private static final long serialVersionUID = 2776434041103366947L;
    @Column(name = "user_seq", nullable = false)
    private Long userSeq;
    @Column(name = "character_seq", nullable = false)
    private Long characterSeq;

    @Override
    public int hashCode() {
        return Objects.hash(characterSeq, userSeq);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserCharacterPK entity = (UserCharacterPK) o;
        return Objects.equals(this.characterSeq, entity.characterSeq) &&
                Objects.equals(this.userSeq, entity.userSeq);
    }
}