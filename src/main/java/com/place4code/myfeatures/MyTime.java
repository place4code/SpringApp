package com.place4code.myfeatures;

import java.time.Duration;
import java.time.LocalDateTime;

public class MyTime {

    public static String prettyDifference(LocalDateTime creationDate) {

        Duration diff = Duration.between(creationDate, LocalDateTime.now());

        long period = diff.getSeconds();


        if (period < 120) {
            return "a moment ago";
        }
        if (period >= 120 && period < 60*60) {
            return period / 60 + " minutes ago";
        }
        if (period >= 60*60 && period <= 2*60*60) {
            return diff.toHours() + " hour ago";
        }
        if (period >= 2*60*60 && period <= 24*60*60) {
            return diff.toHours() + " hours ago";
        }
        if (period > 24*60*60 && period < 2*24*60*60) {
            return diff.toDays() + " day ago";
        } else {
            return diff.toDays() + " days ago";
        }

    }

}
