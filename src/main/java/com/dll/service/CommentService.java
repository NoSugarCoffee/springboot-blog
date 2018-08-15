package com.dll.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dll.dao.CommentMapper;
import com.dll.model.Comment;
import com.dll.util.DateKit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.Setter;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentDao;

	public void save(Comment comment) {
		comment.setCreated(DateKit.getUnixTimeLong());
		System.out.println(comment);
		commentDao.save(comment);
	}

	public PageInfo<Comment> findById(Integer cid) {
		PageHelper.startPage(1,6);
		List<Comment> comments=commentDao.findById(cid);
		return new PageInfo<>(comments);
	}

	
	public List<Comment> findAll() {
		return commentDao.findAll();
	}
	
	public PageInfo<Comment> findAll(Integer index,Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(index,pageSize);
		return new PageInfo<Comment>(commentDao.findAll());
	}

	public void updateStatusById(Integer coid) {
		commentDao.updateStatusById(coid);
	}

	public void deleteById(Integer coid) {
		// TODO Auto-generated method stub
		commentDao.deleteById(coid);
	}

	public int getTotal() {
		return commentDao.findAll()==null?0:commentDao.findAll().size();
	}

	public PageInfo<Comment> findByIdAndStatus(Integer cid,String status) {
		return  new PageInfo<Comment>(commentDao.findByIdAndStatus(cid,"approved"));
	}

}
