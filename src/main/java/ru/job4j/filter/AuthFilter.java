package ru.job4j.filter;


import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    List<String> acceptLink = List.of(
            "registration",
            "formAddUser",
            "loginPage",
            "login",
            "index"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        if (req.getSession().getAttribute("user") != null) {
            chain.doFilter(req, res);
        } else {
            boolean accept = !acceptLink.stream()
                    .filter(link -> uri.endsWith(link))
                    .findFirst()
                    .isEmpty();
            if (accept) {
                chain.doFilter(req, res);
            } else {
                res.sendRedirect(req.getContextPath() + "/loginPage");
            }
        }
    }
}
