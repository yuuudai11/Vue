package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.DetailForm;
import com.example.demo.form.LoginForm;
import com.example.demo.form.MenuForm;
import com.example.demo.form.SearchForm;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.DetailService;
import com.example.demo.service.LoginService;
import com.example.demo.service.MenuService;
import com.example.demo.service.SearchService;

/**
 *
 * @author yoshi
 *
 * APIの基本
 *
 * ＜RESTfulAPIのうんちく＞
 * 	① 世の中にあるAPIは、おおよそRESTfulAPIがほとんど。
 * 	※どの形式で作成するか迷ったらRESTfulAPIで作るといっても過言ではない。
 * 	② Springでコントローラーに「@RestController」アノテーションをつけると戻り値はJSON形式に変換されて返される。
 *	※Objectを戻り値に設定すると属性とその値をJSON形式に変換して返してくれる。
 *
 * ＜送信方法の基本 一般的な考え方＞
 * 	CRUD		メソッド	処理(SQL)
 * 	Create		POST		レコードを生成する(INSERT)	データ登録系もしくは、パスパラメータ未利用時の登録／検索／更新／削除）
 * 	Reference	GET			レコードを取得する(SELECT)	データ検索系 ※パスパラメータ利用時
 * 	Update		PUT			レコードを更新する(UPDATE)	データ更新系 ※パスパラメータ利用時
 * 	Delete		DELETE		レコードを削除する(DELETE)	データ削除系 ※パスパラメータ利用時
 *
 * ＜戻り値＞
 * 	・戻り値に設定したオブジェクトをSpringBootが、JSON形式に変換してレスポンスに載せてくれる。
 * 	・例.戻り値に設定したクラスに属性が「private String name」の1つだけなら、{ name : '' }がレスポンスされる。
 *
 *＜API認証＞
 *	・一般的にAPI認証方式の仕組みは、以下の3種類が利用されている。
 *	1)「標準化されたHTTP認証方式」標準化されたHTTP認証方式は、MD5 (Message Digest 5)と呼ばれる方法が有名で、Basic認証の平文でパスワードを送信する欠点を改善した認証方式で、IDとパスワードをハッシュ化して送信。
 *	2)「APIキー認証」セキュリティ認証でアカウントを所持しているユーザなど特定の人に限定して利用をするために、比較的に簡易的な方法で認証手段を用いるために利用されます
 *	3)「Form認証 / アクセストークン認証」認証鍵が動的にせいせされているため、APIキー認証よりも安全性が高いことが特長です。
 *
 *＜トークン利用の一般的な作法。＞
 *	REST のステートレスの考え方から認証トークンを使用する方がメジャーです。
 *	認証トークンは、認証成功時に生成してクライアントに送信します。（ログインなどの認証。）
 *	クライアントは、認可の必要な API に対して認証トークンを含めたリクエストを送信します。
 *	この認証トークンが正しいものであることを検証し、API の処理を実行します。
 *	認証トークンはリクエストヘッダーのAuthorizationに設定して送信します。
 *	生成したトークンは、ログイン認証時にレスポンスにて返却し、次回以降のリクエストのヘッダーに加えるのが一般的。
 *	生成したトークンは所定時間だけ有効にするよう工夫すること。
 *	┗ トークン用のDBに登録し一定期間後に消滅。消滅しない間はAPIアクセス有効。消滅した場合はAPIアクセス不可。
 *	※トークンはWEBシステムでないのでセッション等に記述することは不可能。
 */
@RestController
public class DemoRestController {

	/** Log 「参考サイト	https://qiita.com/Masahiro_Uemura1234/items/61a25ce4aa815a9922d6」 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** ログイン処理 */
	@Autowired
	private LoginService loginService;

	/** メニュー処理 */
	@Autowired
	private MenuService menuService;

	/** 検索画面処理 */
	@Autowired
	private SearchService searchService;

	/** 詳細画面処理 */
	@Autowired
	private DetailService detailService;
	
	/** 許可するURL */
	public static final String ORIGINAL_URL = "http://localhost:8081";

	/**
	 * アクセステスト
	 * @return
	 */
	@GetMapping("/demo/dbtest")
	public ResponseObject hello() throws Exception {
		return searchService.search();
	}

	/**
	 *	ログイン画面を表示する。
	 *	※ログイン認証がOKの場合、Tokenを返す。このTokenは以下、APIを利用する際は必ずHeaderのAuthorizationに設定する。
	 * @param mav
	 * @return
	 */
	@GetMapping("/demo/login/init")
	public ResponseObject login_init() throws Exception {
		return loginService.init(new LoginForm());
	}

	/**
	 * ログイン画面のログイン処理を行う。
	 *	※本来は、ログイン成功時にログイン成功トークンを返却し、以降のAPIの処理でヘッダー情報に返却されたトークンを含めた形式でアクセスする。
	 *	  トークンはDBに登録し、常に更新がか	かるようにする。一方でトークンテーブルは、定期バッチで指定時間更新がない場合は除去するようにする。
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/login/login")
	public ResponseObject login_login(@RequestBody LoginForm pBodyForm)
			throws Exception {
		ResponseObject obj = loginService.login(pBodyForm);
		System.out.println(obj.toString());
		return obj;
	}

	/**
	 * メニュー画面を表示する。
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/menu/init")
	//public ResponseObject menu_init() throws Exception {
	public ResponseObject menu_init(HttpServletRequest request, @RequestBody MenuForm pBodyForm)
			throws Exception {
		return menuService.init(pBodyForm);
	}

	/**
	 * データ検索画面を表示する
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/search/init")
	public ResponseObject search_init(HttpServletRequest request, @RequestBody SearchForm pBodyForm)
			throws Exception {
		return searchService.init(pBodyForm);
	}

	/**
	 * データ検索画面の検索を行う。
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/search/search")
	public ResponseObject search_search(HttpServletRequest request, @RequestBody SearchForm pBodyForm)
			throws Exception {
		return searchService.search(pBodyForm);
	}

	/**
	 * データ詳細画面を表示する。
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/detail/init")
	public ResponseObject detail(HttpServletRequest request, @RequestBody DetailForm pBodyForm)
			throws Exception {
		return detailService.init(pBodyForm);
	}

	/**
	 * データ詳細画面を表示する。(新規追加ボタン)
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/detail/regist")
	public ResponseObject detail_regist(HttpServletRequest request, @RequestBody DetailForm pBodyForm)
			throws Exception {
		return detailService.regist(pBodyForm);
	}

	/**
	 * データ詳細画面を表示する。（更新ボタン）
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/detail/update")
	public ResponseObject detail_update(HttpServletRequest request, @RequestBody DetailForm pBodyForm)
			throws Exception {
		return detailService.update(pBodyForm);
	}

	/**
	 * データ詳細画面を表示する。（削除ボタン）
	 * @param pHeaderForm
	 * @param pBodyForm
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = ORIGINAL_URL ,methods = { RequestMethod.POST})
	@PostMapping(value = "/demo/detail/delete")
	public ResponseObject detail_delete(HttpServletRequest request, @RequestBody DetailForm pBodyForm)
			throws Exception {
		return detailService.delete(pBodyForm);
	}

}
