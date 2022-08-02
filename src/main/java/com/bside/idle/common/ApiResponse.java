package com.bside.idle.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	private T response;
	private T error;

	public static <T> ApiResponse<T> createSuccess(T data){
		return new ApiResponse<>(true, data, null);
	}

	public static <T> ApiResponse<T> createError(T data){
		return new ApiResponse<>(false, null, data);
	}
}
