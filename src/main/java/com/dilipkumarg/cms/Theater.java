package com.dilipkumarg.cms;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/4/17
 */
public class Theater {

    private String id;
    private String name;

    private List<Show> shows;

    public Theater(
            final @JsonProperty("id") String id, final @JsonProperty("name") String name,
            final @JsonProperty("shows") List<Show> shows) {
        this.id = id;
        this.name = name;
        this.shows = shows;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Show> getShows() {
        return shows;
    }

    public boolean isAvailable() {
        return !shows.isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Theater)) return false;
        final Theater theater = (Theater) o;
        return Objects.equals(getId(), theater.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
