package com.dilipkumarg.cms;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 25/4/17
 */
public class MovieFetcherServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();
    private MovieListFetcher fetcher = new MovieListFetcher();

    @Override
    protected void doGet(
            final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String bmsUrl = req.getParameter("bmsUrl");

        final Map<String, Theater> theaters = fetcher.fetchAvailableTheaters(bmsUrl);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), theaters);
    }
}
