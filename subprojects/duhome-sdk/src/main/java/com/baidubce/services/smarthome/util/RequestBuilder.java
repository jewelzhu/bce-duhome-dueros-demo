package com.baidubce.services.smarthome.util;

import com.baidubce.http.HttpMethodName;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.util.HttpUtils;
import com.baidubce.util.JsonUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder for building a http request
 */
public class RequestBuilder {
    private URI endpoint;
    private HttpMethodName httpMethodName;
    private List<String> path;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Object body;

    private RequestBuilder(HttpMethodName httpMethodName, URI endpoint) {
        this.httpMethodName = httpMethodName;
        this.endpoint = endpoint;
        path = new ArrayList<String>();
        headers = new HashMap<String, String>();
        params = new HashMap<String, String>();
    }

    public static RequestBuilder getInstance(HttpMethodName httpMethodName, URI endpoint) {
        return new RequestBuilder(httpMethodName, endpoint);
    }

    public RequestBuilder path(String path) {
        this.path.add(path);
        return this;
    }

    public RequestBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public RequestBuilder addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestBuilder addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public RequestBuilder addParams(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public RequestBuilder addBody(Object body) {
        this.body = body;
        return this;
    }

    public InternalRequest build() {
        URI uri;
        if (path.isEmpty()) {
            uri = endpoint;
        } else {
            uri = HttpUtils.appendUri(endpoint, path.toArray(new String[0]));
        }
        InternalRequest internalRequest = new InternalRequest(httpMethodName, uri);
        if (!params.isEmpty()) {
            internalRequest.setParameters(params);
        }

        if (!headers.isEmpty()) {
            internalRequest.setHeaders(headers);
        }

        if (body != null) {
            internalRequest.setContent(
                    RestartableInputStream.wrap(JsonUtils.toJsonString(body).getBytes()));
        }
        return internalRequest;
    }
}
