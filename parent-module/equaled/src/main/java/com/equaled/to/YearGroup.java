package com.equaled.to;

import com.equaled.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "year_group")
@Getter @Setter @NoArgsConstructor
public class YearGroup extends BaseEntity {
    @Column(name = "year")
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearGroup yearGroup = (YearGroup) o;
        return id == yearGroup.id && year == yearGroup.year && Arrays.equals(sid, yearGroup.sid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, year);
        result = 31 * result + Arrays.hashCode(sid);
        return result;
    }
}
