package com.place4code.reddit.model;

import com.place4code.myfeatures.MyTime;
import com.place4code.myfeatures.MyURL;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Please enter a title")
    private String titel;

    @NotNull
    @NotEmpty(message = "Please enter a URL")
    @URL(message = "Please enter a valid URL")
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
