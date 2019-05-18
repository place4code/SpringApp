package com.place4code.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String body;

    //link
    @ManyToOne
    @NotNull
    private Link link;


    public Comment(@NotNull String body, @NotNull Link link) {
        this.body = body;
        this.link = link;
    }
}
