package gr.alexc.otaobservatory.configuration.filters;

import gr.alexc.otaobservatory.exception.RateLimitReachedException;
import gr.alexc.otaobservatory.service.RateLimiterService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    public RateLimitFilter(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String ip = httpReq.getRemoteAddr();


        if (rateLimiterService.allowRequest(ip)) {
            filterChain.doFilter(request, response); // Allow request
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Πάρα πολλά αιτήματα");
        }
    }
}
