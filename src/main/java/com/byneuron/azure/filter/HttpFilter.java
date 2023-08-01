package com.byneuron.azure.filter;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;

import com.byneuron.azure.header.Headers;

import reactor.core.publisher.Flux;

import java.util.UUID;

@Filter("/**")
@Singleton
@Slf4j
public class HttpFilter implements HttpServerFilter {

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute("traceId", uuid);
        MDC.put("trace", uuid);

        log.info("filter is working");
        return Flux.from(chain.proceed(request)).map(mutableHttpResponse -> {
            MDC.put("trace", uuid);
            mutableHttpResponse.getHeaders().add(Headers.traceId,uuid);
            log.info(mutableHttpResponse.getBody(String.class).orElse("empty"));
            return mutableHttpResponse;
        });
    }
}
