package com.base.backend.common.controller;

import com.base.backend.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author kamen
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    protected Long getLoginUserId(HttpSession session) {
        return  (Long)session.getAttribute(Constants.LOGIN_USER_ID);
    }

    protected Long getLoginUserId(HttpServletRequest request) {
        return getLoginUserId(request.getSession());
    }
}
