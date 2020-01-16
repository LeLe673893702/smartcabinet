package com.llc.smartcabinet.data.model;

/**
 * 返回结果包装类
 * @author newler
 * @date 2019/12/27
 */
public class BaseResult<T> {
    public int code;
    public String msg;
    public T data;
}
