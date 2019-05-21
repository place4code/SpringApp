package com.place4code.reddit.model;

import com.place4code.myfeatures.MyTime;
import com.place4code.myfeatures.MyURL;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Link extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titel;

    @NotNull
    private String url;

    //comments
    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();

    public Link(@NotNull String titel, @NotNull String url) {
        this.titel = titel;
        this.url = url;
    }

    public void addComment(Comment tempComment) {
        comments.add(tempComment);
    }

    public String getPrettyDifference() {
        return MyTime.prettyDifference(getCreationDate());
    }

    public String getShortUrl() {
        return MyURL.makeShortUrl(url);
    }
}
