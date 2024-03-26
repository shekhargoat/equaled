package com.equaled.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "year_group")
@Getter
@Setter
@NoArgsConstructor
public class YearGroup extends BaseEntity{
    @Basic
    @Column(name = "year")
    private Integer year;
}
