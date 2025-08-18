package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoException extends Exception {

	/** エラーメッセージ */
	String errMessage;

	// コンストラクタ
	public DemoException(String errMessage_) {
		this.errMessage = errMessage_;
	}

}
