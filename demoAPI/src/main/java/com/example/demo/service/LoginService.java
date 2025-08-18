package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TblUserInfo;
import com.example.demo.exception.DemoException;
import com.example.demo.form.LoginForm;
import com.example.demo.repository.TblUserInfoRepository;
import com.example.demo.response.LoginResponse;
import com.example.demo.response.ResponseObject;

@Service
public class LoginService {

	/** DBアクセス */
	@Autowired
	private TblUserInfoRepository tblUserInfoRepository;

	/** 戻り値オブジェクト */
	private LoginResponse json = new LoginResponse();

	/**
	 * ログイン画面初期表示処理
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject init(LoginForm pForm) throws DemoException {
		return json;
	}

	/**
	 * ログイン処理
	 * @param mav
	 * @param pForm
	 * @return
	 */
	public ResponseObject login(LoginForm pForm) throws DemoException {
		String id = pForm.getUserId();
		String password = pForm.getPassword();
		List<TblUserInfo> userInfoList = tblUserInfoRepository.findByUserIdAndPassword(id, password);
		if (!userInfoList.isEmpty() && userInfoList.size() > 0) {
			json.setHttpStatus("200");
			json.setUserInfoList(userInfoList);
			json.setSuccessMessage("データ1件取得！！！");
			json.setErrMessage("");
		} else {
			json.setHttpStatus("200");
			json.setUserInfoList(null);
			json.setSuccessMessage("");
			json.setErrMessage("該当しないID／パスワードです...");
		}
		return json;
	}
}
