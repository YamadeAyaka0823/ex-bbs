package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;
/**
 * コメントを操作するリポジトリ.
 * 
 * @author ayaka.yamade
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	/**
	 * Commentオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs,i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	/**
	 * 記事のID検索を行う.
	 * @param articleId 記事のID
	 * @return コメント一覧
	 */
	public List<Comment> findByArticleId(Integer articleId){
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id = :articleId ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId",articleId);
		List<Comment> commentList = template.query(sql,param,COMMENT_ROW_MAPPER);
		return commentList;
	}
	/**
	 * コメントの追加、更新を行う.
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "INSERT INTO comments(name, content, article_id) VALUES(:name, :content, :articleId)";
		template.update(sql, param);
	}

}
