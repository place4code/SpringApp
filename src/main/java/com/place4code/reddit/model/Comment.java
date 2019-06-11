package com.place4code.reddit.model;

import com.place4code.myfeatures.MyTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String body;

    @ManyToOne
    @NotNull
    private Link link;

    private String login;


    public Comment(@NotNull String body, @NotNull Link link) {
        this.body = body;
        this.link = link;
    }

    public String getPrettyDifference() {
        return MyTime.prettyDifference(getCreationDate());
    }
}
