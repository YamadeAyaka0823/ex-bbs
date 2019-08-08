package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;
/**
 * 記事一覧表示の処理を行うコントローラ.
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
		return "article/list";
	}

}
