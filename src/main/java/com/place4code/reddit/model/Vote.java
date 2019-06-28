package com.place4code.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @ManyToOne
    private Link link;

    @NotNull
    private short direction;

    public Vote(@NotNull Long userId, @NotNull Link link, @NotNull short direction) {
        this.userId = userId;
        this.link = link;
        this.direction = direction;
    }
}
