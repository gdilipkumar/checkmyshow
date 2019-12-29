package com.dilipkumarg.cms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/4/17
 */
public class MovieListFetcher {

    public Map<String, Theater> fetchAvailableTheaters(String bmsUrl) throws IOException {
        final Document document = Jsoup.parse(new URL(bmsUrl), 1000 * 10);

        final Elements venueList = document.getElementById("venuelist").getElementsByTag("li");
        List<Theater> theaters = new ArrayList<>();
        for (final Element venue : venueList) {
            String id = venue.attr("data-id");
            String name = venue.attr("data-name");

            theaters.add(new Theater(id, name, readShows(venue)));
        }

        return theaters.stream().filter(Theater::isAvailable).collect(Collectors.toMap(p -> p.getId(), p -> p));
    }

    private List<Show> readShows(Element venue) {
        final Elements body = venue.getElementsByClass("body");
        List<Show> shows = new ArrayList<>();
        if (!body.isEmpty()) {
            final Element timingsElement = body.get(0);
            for (final Element timing : timingsElement.getElementsByTag("div").get(0).getElementsByTag("a")) {
                final String time = timing.attr("data-date-time");
                final String availability = timing.attr("data-availability");

                if ("A".equalsIgnoreCase(availability)) {
                    shows.add(new Show(time, true));
                }
            }
        }
        return shows;
    }

    public static void main(String[] args) throws IOException {
        MovieListFetcher fetcher = new MovieListFetcher();

        final Collection<Theater> theaters = fetcher.fetchAvailableTheaters("https://in.bookmyshow" +
                ".com/buytickets/baahubali-2-the-conclusion-hyderabad/movie-hyd-ET00038693-MT/20170428").values();

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(theaters));
    }

}
