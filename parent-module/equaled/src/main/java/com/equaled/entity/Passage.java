package com.equaled.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "passages")
public class Passage extends BaseEntity {

    private static final long serialVersionUID = 2822368507766509213L;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Users author;

    @Column(name = "publication_date")
    private Instant publicationDate;


}
