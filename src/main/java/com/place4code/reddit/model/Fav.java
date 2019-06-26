package com.place4code.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Fav  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Link link;

    @NotNull
    private Long userId;

    public Fav(@NotNull Link link, @NotNull Long userId) {
        this.link = link;
        this.userId = userId;
    }
}