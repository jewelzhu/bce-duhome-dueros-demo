package com.baidubce.iot.duhome.demo.dueros.filter;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.servlet.Util;
import com.google.common.collect.Iterators;

import javax.servlet.ReadListener;
import javax.servlet.http.HttpServletRequestWrapper;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static ch.qos.logback.access.AccessConstants.LB_INPUT_BUFFER;

/**
 * As the "tee" program on Unix, duplicate the request's input stream.
 *
 * @author Ceki G&uuml;lc&uuml;
 *
 * 这个类参考的Teefilter中的实现，由于Teefilter里相关类非public无法直接饮用所以只能拷贝出来一份
 */
class CopyHttpServletRequest extends HttpServletRequestWrapper {

    private TeeServletInputStream inStream;
    private BufferedReader reader;
    boolean postedParametersMode = false;
    private String injectedAccessToken = null;

    CopyHttpServletRequest(HttpServletRequest request) {
        super(request);
        // we can't access the input stream and access the request parameters
        // at the same time
        if (Util.isFormUrlEncoded(request)) {
            postedParametersMode = true;
        } else {
            inStream = new TeeServletInputStream(request);
            // add the contents of the input buffer as an attribute of the request
            request.setAttribute(LB_INPUT_BUFFER, inStream.getInputBuffer());
            reader = new BufferedReader(new InputStreamReader(inStream));
        }

    }

    public void setAccessToken(String accessToken) {
        this.injectedAccessToken = accessToken;
    }

    @Override
    public Enumeration getHeaderNames() {
        //create an enumeration of the request headers
        //additionally, add the "username" request header

        //create a list
        List list = new ArrayList();

        //loop over request headers from wrapped request object
        HttpServletRequest request = (HttpServletRequest)getRequest();
        Enumeration e = request.getHeaderNames();
        while(e.hasMoreElements()) {
            //add the names of the request headers into the list
            String n = (String)e.nextElement();
            list.add(n);
        }

        //additionally, add the "username" to the list of request header names
        if (this.injectedAccessToken != null) {
            list.add("Authorization");
        }

        //create an enumeration from the list and return
        Enumeration en = Collections.enumeration(list);
        return en;
    }

    @Override
    public String getHeader(String name) {
        //get the request object and cast it
        HttpServletRequest request = (HttpServletRequest)getRequest();

        //if we are looking for the "username" request header
        if("Authorization".equals(name) && injectedAccessToken != null) {
            return "bearer" + injectedAccessToken;
        }

        //otherwise fall through to wrapped request object
        return request.getHeader(name);
    }

    public byte[] getInputBuffer() {
        if (postedParametersMode) {
            throw new IllegalStateException("Call disallowed in postedParametersMode");
        }
        return inStream.getInputBuffer();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        //get the request object and cast it
        HttpServletRequest request = (HttpServletRequest)getRequest();
        if("Authorization".equals(name) && injectedAccessToken != null) {
            Vector<String> v = new Vector<>();
            v.add("bearer" + injectedAccessToken);
            return v.elements();
        }
        return request.getHeaders(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (!postedParametersMode) {
            return inStream;
        } else {
            return super.getInputStream();
        }
    }

    //

    @Override
    public BufferedReader getReader() throws IOException {
        if (!postedParametersMode) {
            return reader;
        } else {
            return super.getReader();
        }
    }

    public boolean isPostedParametersMode() {
        return postedParametersMode;
    }

    static class TeeServletInputStream extends ServletInputStream {

        InputStream in;
        byte[] inputBuffer;

        TeeServletInputStream(HttpServletRequest request) {
            duplicateInputStream(request);
        }

        private void duplicateInputStream(HttpServletRequest request) {
            ServletInputStream originalSIS = null;
            try {
                originalSIS = request.getInputStream();
                inputBuffer = consumeBufferAndReturnAsByteArray(originalSIS);
                this.in = new ByteArrayInputStream(inputBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeStream(originalSIS);
            }
        }

        @Override
        public int read() throws IOException {
            return in.read();
        }

        byte[] consumeBufferAndReturnAsByteArray(InputStream is) throws IOException {
            int len = 1024;
            byte[] temp = new byte[len];
            int c = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((c = is.read(temp, 0, len)) != -1) {
                baos.write(temp, 0, c);
            }
            return baos.toByteArray();
        }

        void closeStream(ServletInputStream is) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        byte[] getInputBuffer() {
            return inputBuffer;
        }

        @Override
        public boolean isFinished() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public boolean isReady() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not yet implemented");
        }
    }

}
