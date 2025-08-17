package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DemoException;
import com.example.demo.form.MenuForm;
import com.example.demo.repository.TblUserInfoRepository;
import com.example.demo.response.MenuResponse;
import com.example.demo.response.ResponseObject;

@Service
public class MenuService {

	/** ユーザテーブルリポジトリ */
	@Autowired
	private TblUserInfoRepository tblUserInfoRepository;

	/** 戻り値オブジェクト */
	private MenuResponse json = new MenuResponse();

	/**
	 * データ検索画面初期表示処理
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject init(MenuForm pForm) throws DemoException {
		json.setUserInfoList(tblUserInfoRepository.findByAllKeyword());
		return json;
	}

}
