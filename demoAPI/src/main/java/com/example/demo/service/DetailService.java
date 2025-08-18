package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TblUserInfo;
import com.example.demo.exception.DemoException;
import com.example.demo.form.DetailForm;
import com.example.demo.repository.TblUserInfoRepository;
import com.example.demo.response.DetailResponse;
import com.example.demo.response.ResponseObject;

@Service
public class DetailService {

	/** ユーザテーブルリポジトリ */
	@Autowired
	private TblUserInfoRepository tblUserInfoRepository;

	/** 戻り値オブジェクト */
	private DetailResponse json = new DetailResponse();

	/**
	 * データ詳細画面 初期表示処理
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject init(DetailForm pForm) throws DemoException {
		String sUserId = pForm.getId();
		String sMode = pForm.getMode();
		TblUserInfo info = new TblUserInfo();
		if ("regist".equals(pForm.getMode())) {
			// 新規作成
			json.setUserInfo(info);
		} else if ("update".equals(pForm.getMode())) {
			// 更新
			json.setUserInfo(tblUserInfoRepository.findByUserId(sUserId));
		} else if ("delete".equals(pForm.getMode())) {
			// 削除
			json.setUserInfo(tblUserInfoRepository.findByUserId(sUserId));
		}
		json.setMode(sMode);
		return json;
	}

	/**
	 * データ詳細画面 登録ボタン
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject regist(DetailForm pForm) throws DemoException {
		String sNewId = null;
		try {

			// 引数取得
			sNewId = tblUserInfoRepository.generateNewId(); // 新規発番。
			TblUserInfo tblUserInfo = paramToEntity(pForm, sNewId);

			// DB登録処理
			tblUserInfoRepository.save(tblUserInfo);

			// 成功メッセージ
			json.setSuccessMessage("ユーザー情報の登録に成功しました。");
			json.setMode("confirm");

			// 表示用の再取得
			initSearch(json, sNewId);

		} catch (Exception e) {
			json.setErrMessage("ユーザー情報の登録に失敗しました。\n" + e.getLocalizedMessage());
		}
		return json;
	}

	/**
	 * データ詳細画面 更新ボタン
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject update(DetailForm pForm) throws DemoException {
		try {

			// 引数取得
			TblUserInfo tblUserInfo = paramToEntity(pForm, null);

			// DB登録処理
			tblUserInfoRepository.save(tblUserInfo);

			// 成功メッセージ
			json.setSuccessMessage("ユーザー情報の更新に成功しました。");
			json.setMode("confirm");

			// 表示用の再取得
			initSearch(json, pForm.getId());

		} catch (Exception e) {
			json.setErrMessage("ユーザー情報の更新に失敗しました。\n" + e.getLocalizedMessage());
		}
		return json;
	}

	/**
	 * データ詳細画面 削除ボタン
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject delete(DetailForm pForm) throws DemoException {
		try {

			// 引数取得
			String sId = pForm.getId();

			// DB更新処理
			tblUserInfoRepository.deleteById(sId);

			// 成功メッセージ
			json.setSuccessMessage("ユーザー情報の削除に成功しました。");
			json.setMode("confirm");

			// 表示用の値セット
			json.setUserInfo(paramToEntity(pForm, null));

		} catch (Exception e) {
			json.setErrMessage("ユーザー情報の削除に失敗しました。\n" + e.getLocalizedMessage());
		}
		return json;
	}

	/**
	 * 共通初期検索処理
	 * @return
	 * @throws DemoException
	 */
	private void initSearch(DetailResponse json, String pId) throws DemoException {
		TblUserInfo tblUserInfo_ = tblUserInfoRepository.findByUserId(pId);
		json.setUserInfo(tblUserInfo_);
	}

	/**
	 * 入力値をテーブルテンティにセット。
	 * @param pForm
	 * @throws DemoException
	 */
	private TblUserInfo paramToEntity(DetailForm pForm, String pNewId) throws DemoException {
		// 表示用セット
		TblUserInfo tblUserInfo = new TblUserInfo();
		if (pNewId != null) {
			tblUserInfo.setId(pNewId);
		} else {
			tblUserInfo.setId(pForm.getId());
		}
		tblUserInfo.setPassword(pForm.getPassword());
		tblUserInfo.setName(pForm.getName());
		tblUserInfo.setMail(pForm.getMail());
		tblUserInfo.setAddress(pForm.getAddress());
		return tblUserInfo;
	}

}
