package com.baidubce.iot.duhome.demo.dueros.filter;

import com.baidubce.iot.duhome.demo.dueros.model.BotData;
import com.baidubce.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DuerosAccessTokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && ((HttpServletRequest) request).getRequestURI().equals("/api/bot")
                && StringUtils.isBlank(((HttpServletRequest) request).getHeader("Authorization"))) {
            try {
                CopyHttpServletRequest copiedRequest = new CopyHttpServletRequest((HttpServletRequest) request);

                String requestBody = new String(copiedRequest.getInputBuffer(), StandardCharsets.UTF_8);
                BotData botData = JsonUtils.fromJsonString(requestBody, BotData.class);
                String token = botData.getPayload().getAccessToken();
                log.info("get access_token={} from request body and put it into header", token);
                copiedRequest.setAccessToken( token);
                // System.out.println("BEFORE TeeFilter. filterChain.doFilter()");
                chain.doFilter(copiedRequest, response);
                // System.out.println("AFTER TeeFilter. filterChain.doFilter()");


            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } catch (ServletException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
