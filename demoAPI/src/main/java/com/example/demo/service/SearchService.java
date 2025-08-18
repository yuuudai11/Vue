package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DemoException;
import com.example.demo.form.SearchForm;
import com.example.demo.repository.TblUserInfoRepository;
import com.example.demo.response.ResponseObject;
import com.example.demo.response.SearchResponse;

@Service
public class SearchService {

	/** ユーザテーブルリポジトリ */
	@Autowired
	private TblUserInfoRepository tblUserInfoRepository;

	/** 戻り値オブジェクト */
	private SearchResponse json = new SearchResponse();

	/**
	 * データ検索処理（ユーザーTBL）
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject search() throws DemoException {
		json.setUserInfoList(tblUserInfoRepository.findByAllKeyword());
		return json;
	}

	/**
	 * データ検索画面初期表示処理
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject init(SearchForm pForm) throws DemoException {
		json.setUserInfoList(tblUserInfoRepository.findByAllKeyword());
		return json;
	}

	/**
	 * データ検索処理（ユーザーTBL）
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject search(SearchForm pForm) throws DemoException {
		json.setUserInfoList(tblUserInfoRepository.findByAllKeyword());
		json.setHttpStatus("200");
		json.setSuccessMessage("データ取得！！！");
		json.setErrMessage("");
		return json;
	}

}
