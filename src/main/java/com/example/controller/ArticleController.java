package com.example.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;
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
	
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public CommentForm setUpForm() {
		return new CommentForm();
	}
	
	
	/**
	 * 記事一覧.
	 * @param model リクエストスコープ
	 * @return 記事一覧情報
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Article> articleList = articleRepository.findAll();
		for(Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
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
	/**
	 * コメント追加.
	 * @param form リクエストパラメーターが入ったフォーム
	 * @return 初期画面
	 */
	@RequestMapping("insert_comment")
	public String insertComment(CommentForm form) {
		System.out.println(form);
		Comment comment = new Comment();
		comment.setArticleId(form.getIntArticleId());
		BeanUtils.copyProperties(form, comment);
		commentRepository.insert(comment);
		return "forward:/article/showList";
	}
	/**
	 * コメント削除.
	 * @param form リクエストパラメーターが入ったフォーム
	 * @return 初期画面
	 */
	@RequestMapping("delete_article")
	public String deleteArticle(CommentForm form) {
		
	  commentRepository.deleteByArticleId(form.getIntArticleId());
	  articleRepository.deleteById(form.getIntArticleId());
	  return "forward:/article/showList";
	

}
}
