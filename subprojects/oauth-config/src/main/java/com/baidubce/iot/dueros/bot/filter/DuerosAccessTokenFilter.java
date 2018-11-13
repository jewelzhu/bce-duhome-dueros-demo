package com.baidubce.iot.dueros.bot.filter;

import com.baidubce.iot.dueros.bot.util.DuerosAuthJsonHelper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * dueros的oauth请求是非标的，需要从requestbody中将access_token解析出来，然后按照标准格式放到request的Authorization header中
 */
@Slf4j
public class DuerosAccessTokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && (((HttpServletRequest) request).getHeader("Authorization") == null)) {
            try {
                CopyHttpServletRequest copiedRequest = new CopyHttpServletRequest((HttpServletRequest) request);

                String requestBody = new String(copiedRequest.getInputBuffer(), StandardCharsets.UTF_8);
                String token = DuerosAuthJsonHelper.getAccessTokenInBotData(requestBody);
                log.info("get access_token={} from request body and put it into header", token);
                copiedRequest.setAccessToken( token);
                chain.doFilter(copiedRequest, response);


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
