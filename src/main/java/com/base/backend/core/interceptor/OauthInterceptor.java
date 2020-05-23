package com.base.backend.core.interceptor;

import com.base.backend.modules.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kamen
 */
public class OauthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(OauthInterceptor.class);

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
        /*if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        if (request.getHeader(Constants.AUTHORIZATION) == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String token = request.getHeader(Constants.AUTHORIZATION);

        try {
            Jws<Claims> jwt = JwtUtils.parseToken(token);
            String username = jwt.getBody().getSubject();
            if (StringUtils.isNoneBlank(username)) {
                Optional<User> user = userService.findByActiveUsername(username);
                if (user.isPresent()) {
                    request.getSession().setAttribute(Constants.LOGIN_USER_ID, user.get().getId());
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;*/
    }
}
