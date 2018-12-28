package org.soen387.app;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.dsrg.soenea.application.filter.PermalinkFilter;
import org.dsrg.soenea.application.servlet.DispatcherServlet;

@WebFilter(value="/poke")
public class PokeFilter extends PermalinkFilter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    	super.init(config);
    	System.out.println(config.getServletContext().getContextPath().toString());
    	config.getServletContext().setAttribute(DispatcherServlet.BASE_URI_ATTR, config.getServletContext().getContextPath() + "/");
    }
}
