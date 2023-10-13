package com.ltp.gradesubmission.entity;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NonNull
    @Column(name = "subject", nullable = false)
    private String subject;
    
    @NonNull
    @Column(name = "code", nullable = false)
    private String code;
    
    @NonNull
    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;



}
