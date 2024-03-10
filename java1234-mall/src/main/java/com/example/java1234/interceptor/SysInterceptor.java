package com.example.java1234.interceptor;

import com.example.java1234.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor implements HandlerInterceptor {

    
    public boolean preHandlle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        String path=request.getRequestURI();
        System.out.println("path="+path);
        if(handler instanceof HandlerMethod){
            //判断token是否为空
            String token=request.getHeader("token");
            System.out.println("token="+token);
            if(StringUtils.isEmpty(token)){
                System.out.println("token为空");
                throw new RuntimeException("签名验证不存在");

            }else{
                //如果token不为空
                Claims claims = JwtUtils.validateJWT(token).getClaims();
                if(claims==null){
                    throw new RuntimeException("鉴权失败");
                }else{
                    System.out.println("鉴权成功");
                    return true;
                }
            }
        }else{
            return true;
        }
    }
}
