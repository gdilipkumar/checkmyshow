package com.dilipkumarg.cms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/4/17
 */
public class Show {
    private String time;
    private boolean available;

    public Show(final @JsonProperty("time") String time, final @JsonProperty("available") boolean available) {
        this.time = time;
        this.available = available;
    }

    public String getTime() {
        return time;
    }

    public Show setTime(final String time) {
        this.time = time;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public Show setAvailable(final boolean available) {
        this.available = available;
        return this;
    }
}
