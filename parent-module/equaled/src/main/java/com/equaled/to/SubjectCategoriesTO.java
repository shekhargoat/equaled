package com.equaled.to;

import com.equaled.entity.BaseEntity;
import com.equaled.entity.Subject;
import com.equaled.entity.YearGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
public class SubjectCategoriesTO extends BaseEntity {

    private SubjectTO subject;
    private String category;
    private String subCategory;
    private String subCategory1;
    private YearGroupTO yearGroup;


}
