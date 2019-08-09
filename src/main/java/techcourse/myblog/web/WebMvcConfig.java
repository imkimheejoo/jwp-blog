package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LogoutInterceptor logoutInterceptor;
    private final SessionHandlerMethodResolver sessionHandlerMethodResolver;


    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor, LogoutInterceptor logoutInterceptor
                            , SessionHandlerMethodResolver sessionHandlerMethodResolver) {
        this.loginInterceptor = loginInterceptor;
        this.logoutInterceptor = logoutInterceptor;
        this.sessionHandlerMethodResolver = sessionHandlerMethodResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/login")
                .addPathPatterns("/users/new");

        registry.addInterceptor(logoutInterceptor)
                .addPathPatterns("/users/**")
                .excludePathPatterns("/users/new")
                .addPathPatterns("/articles/**");
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sessionHandlerMethodResolver);
    }

}