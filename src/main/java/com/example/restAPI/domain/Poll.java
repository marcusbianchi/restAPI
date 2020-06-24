package com.example.restAPI.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Poll {

    @Id
    @GeneratedValue
    @Column(name="POLL_ID")
    private Long id;

    @Column(name="QUESTION")
    private String question;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="POLL_ID")
    @OrderBy
    private Set<Option> options;
}