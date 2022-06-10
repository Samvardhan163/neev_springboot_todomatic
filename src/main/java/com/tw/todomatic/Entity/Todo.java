package com.tw.todomatic.Entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "Todo")
@Table

public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


}
