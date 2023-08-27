package com.gpingguo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * HTTP结果封装
 * @author gpingguo
 */
@Data
public class ResponseResult<T> implements Serializable {

	private Integer code = 0;
	private String msg = "操作成功";
	private T data;

	public static<T> ResponseResult<T> error() {
		return error(10000, "未知异常，请联系管理员");
	}

	public static<T> ResponseResult<T> error(String msg) {
		return error(10000, msg);
	}

	public static<T> ResponseResult<T> error(Integer code, String msg) {
		ResponseResult<T> r = new ResponseResult<>();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static<T> ResponseResult<T> ok() {
		return new ResponseResult<>();
	}

	public static<T> ResponseResult<T> ok(String msg) {
		ResponseResult<T> r = new ResponseResult<>();
		r.setMsg(msg);
		return r;
	}

	public static<T> ResponseResult<T> ok(String msg, T data) {
		ResponseResult<T> r = new ResponseResult<>();
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static<T> ResponseResult<T> okData(T data) {
		ResponseResult<T> r = new ResponseResult<>();
		r.setData(data);
		return r;
	}

	@Override
	public String toString() {
		return "ResponseResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
