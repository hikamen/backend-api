package com.base.backend.core.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.backend.common.Constants;
import com.base.backend.utils.ConvertUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 请求上下文
 * @author kamen
 */
public class WebRequestContext implements Serializable {
    /**
     * 分页请求相关参数
     */
    private int page;
    private int limit;
    private int size;
    private String sort;
    private String order;
    private String q;

    /**
     * 请求参数
     */
    private Map<String, String> params = Maps.newHashMap();

    public WebRequestContext() {
    }

    public WebRequestContext(NativeWebRequest webRequest) {
        this(webRequest.getNativeRequest(HttpServletRequest.class));
    }

    public WebRequestContext(HttpServletRequest request) {
        String param;
        String value;

        // 获取完整的请求参数，存进params里面
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            param = e.nextElement();
            value = request.getParameter(param);
            this.params.put(param, value);
        }

        // 分页请求当前页码
        this.page = ConvertUtils.convert(request.getParameter("current"), Integer.class, Constants.DEFAULT_PAGE);
        // 分页请求每页记录数
        this.limit = ConvertUtils.convert(request.getParameter("pageSize"), Integer.class, Constants.DEFAULT_PAGE_SIZE);
        // 分页请求的排序列
        this.sort = ConvertUtils.convert(request.getParameter("sort"), String.class);
        // 分页请求的排序方式
        this.order = ConvertUtils.convert(request.getParameter("order"), String.class);
        // 请求的搜索关键字
        this.q = ConvertUtils.convert(request.getParameter("queryValue"), String.class);
    }

    /**
     * 获取Spring Data的Pageable对象
     */
    public Pageable getPageable() {
        if (!Strings.isNullOrEmpty(sort)) {
            Sort.Direction direction = "desc".equalsIgnoreCase(this.getOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
            return PageRequest.of(this.page - 1, limit, Sort.by(direction, sort));
        } else {
            return PageRequest.of(this.page - 1, limit);
        }
    }

    /**
     * 获取MyBatisPlus的分页请求对象
     */
    public <E> Page<E> getMyBatisPage() {
        return new Page<>(this.page, this.limit);
    }

    // 转换排序成布尔值，默认顺序排序
    public boolean convertOrder(String order) {
        if (StringUtils.isNotEmpty(order) && Constants.DESC.equalsIgnoreCase(order)) {
            return false;
        } else {
            return true;
        }
    }

    public String getParam(String name) {
        return params.get(name);
    }

    /**
     * 获取指定类型获取请求参数
     *
     * @param name         参数名
     * @param clazz        类型
     * @param defaultValue 默认值
     * @param <E>          泛型的返回类型
     * @return 参数值
     */
    public <E> E getParam(String name, Class<E> clazz, E defaultValue) {
        String value = null;
        if (params.get(name) != null) {
            value = params.get(name);
        }
        return ConvertUtils.convert(value, clazz, defaultValue);
    }

    /**
     * 获取指定类型获取请求参数
     *
     * @param name  参数名
     * @param clazz 类型
     * @param <E>   泛型的返回类型
     * @return 参数值
     */
    public <E> E getParam(String name, Class<E> clazz) {
        String value = null;
        if (params.get(name) != null) {
            value = params.get(name);
        }
        return ConvertUtils.convert(value, clazz);
    }

    /**
     * 获取预期字段中的排序字段，不允许出现预期字段以外的数据，防止SQL注入
     *
     * @param expectColumns 预期的字段集合
     * @return 排序字段
     */
    public String getExpectSort(List<String> expectColumns) {
        String result = "";
        if (StringUtils.isNotEmpty(this.sort)) {
            String trimSort = StringUtils.trim(this.sort);

            for (String column : expectColumns) {
                if (trimSort.equals(column)) {
                    result = trimSort;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取预期内的排序方向（顺序）
     *
     * @return 排序方向
     */
    public String getExpectOrder() {
        return StringUtils.isNotEmpty(this.order) && Constants.DESC.equalsIgnoreCase(this.order) ?
                Constants.DESC : Constants.ASC;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
