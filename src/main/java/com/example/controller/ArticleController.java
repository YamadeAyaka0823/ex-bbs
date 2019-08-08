package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
/**
 * 記事関連の処理を行うコントローラ.
 * 
 * @author ayaka.yamade
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	
	/**
	 * 記事一覧.
	 * @param model リクエストスコープ
	 * @return 記事一覧情報
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);
		return "list";
	}
	
	/**
	 * 投稿追加.
	 * @param form リクエストパラメーターが入ったフォーム
	 * @return 初期画面
	 */
	@RequestMapping("/insert")
	public String insert(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form,article);
		articleRepository.insert(article);
		return "forward:/article/showList";
	}

}
