package org.dealersautocenter.dealersvehiclinventory.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader("X-Tenant-ID");
        if (tenantId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "X-Tenant-ID header is required");
            return;
        }
        TenantContext.setTenantId(tenantId);
        try {
            filterChain.doFilter(request, response);
        }finally {
            TenantContext.clear();
        }

    }
}
