package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TblUserInfo;

public interface TblUserInfoRepository extends CrudRepository<TblUserInfo, String> {

	/** 【ログイン画面】ID／パスワード確認のSQL */
	@Query(value = "SELECT * FROM tbl_user_info AS p WHERE ( p.id = :userId and password = :password ) or ( p.mail = :userId and password = :password )", nativeQuery = true)
	List<TblUserInfo> findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

	/** 【検索画面】検索ボタン押下時の検索SQL（後ほど検索条件やソート条件、ページングなどを付加したいね。） */
	@Query(value = "SELECT * FROM tbl_user_info AS p order by p.id", nativeQuery = true)
	List<TblUserInfo> findByAllKeyword();

	/** 【詳細画面】詳細画面の初期表示に利用するデータ抽出SQL */
	@Query(value = "SELECT * FROM tbl_user_info AS p WHERE p.id = :userId", nativeQuery = true)
	TblUserInfo findByUserId(@Param("userId") String userId);

	/** 【詳細画面】詳細画面の初期表示に利用するデータ抽出SQL（前0埋めで5桁の数字をカウントアップ。） */
	@Query(value = "select IFNULL(lpad((max(id) + 1),8,'0'),'00000001') from tbl_user_info", nativeQuery = true)
	String generateNewId();

}
