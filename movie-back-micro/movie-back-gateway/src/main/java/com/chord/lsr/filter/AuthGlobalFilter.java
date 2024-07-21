package com.chord.lsr.filter;

import com.chord.lsr.config.AuthProperties;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.properties.JwtProperties;
import com.chord.lsr.util.CollUtils;
import com.chord.lsr.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 判断是否不需要拦截
        if(isExclude(request.getPath().toString())){
            // 无需拦截，直接放行
            return chain.filter(exchange);
        }
        //1、从请求头中获取令牌
        String token = "";
        List<String> headers = request.getHeaders().get("token");
        if (!CollUtils.isEmpty(headers)) {
            token = headers.get(0);
        }
        System.out.println(token);
        //2、校验令牌
        Long userId = null;
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            userId = Long.valueOf(claims.get(constant.JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id：{}", userId);
            //3、通过，放行

        } catch (Exception ex) {
            //4、不通过，响应401状态码
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);
            return response.setComplete();
        }
        // 5.如果有效，传递用户信息
        String userInfo = userId.toString();
        exchange.mutate() // mutate:对下游请求做更改
                .request(builder -> builder.header("user-info", userInfo))
                .build();
        // 放行
        return chain.filter(exchange);
    }

    private boolean isExclude(String antPath) {
        for (String pathPattern : authProperties.getExcludePaths()) {
            if(antPathMatcher.match(pathPattern, antPath)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
