package com.example.demo.response;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.TblUserInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObject implements Serializable {

	/** シリアルID */
	private static final long serialVersionUID = 1L;

	/** 認証用トークン */
	private String token;

	/** Httpステータス */
	private String httpStatus;

	/** エラーメッセージ */
	private String errMessage;

	/** 成功メッセージ */
	private String successMessage;

	/** 画面表示モード */
	private String mode;

	/** ユーザー情報エンティティ */
	private TblUserInfo userInfo;

	/** ユーザー情報エンティティリスト */
	private List<TblUserInfo> userInfoList;

}
