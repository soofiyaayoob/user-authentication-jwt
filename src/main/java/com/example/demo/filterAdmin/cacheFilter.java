package com.example.demo.filterAdmin;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
@WebFilter(urlPatterns = {"/*"})
public class cacheFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletResponse httpServletResponse=(HttpServletResponse) response;
		 
		 httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
         httpServletResponse.setHeader("Pragma", "no-cache");
         httpServletResponse.setDateHeader("Expires", 0);
         System.out.println("iam working");
         chain.doFilter(request, response);
	}

}
