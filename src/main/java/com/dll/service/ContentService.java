package com.dll.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dll.dao.CommentMapper;
import com.dll.dao.ContentMapper;
import com.dll.exception.TipException;
import com.dll.model.Archive;
import com.dll.model.Category;
import com.dll.model.Content;
import com.dll.model.StatisticsBo;
import com.dll.util.DateKit;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;

@Service
public class ContentService {
	@Autowired
	private ContentMapper contentDao;
	
	@Autowired
	private CommentMapper commentDao;
	
	public int getTotal() {
		return contentDao.findAll()==null?0:contentDao.findAll().size();
	}
	
	public List<Content> findAll(){
		return contentDao.findAll();
	}
	
	public PageInfo<Content> findByPage(int index, int pageSize) {
		PageHelper.startPage(index,pageSize);
		List<Content> contents = contentDao.findAll();
		PageInfo<Content> pageInfo=new PageInfo<Content>(contents);
		return pageInfo;
	}
	
	public PageInfo<Content> findByPageAndStatus(int index, int pageSize) {
		PageHelper.startPage(index,pageSize);
		List<Content> contents = contentDao.findAllAndStatus();
		PageInfo<Content> pageInfo=new PageInfo<Content>(contents);
		return pageInfo;
	}
	/**
	 * 根据id查询
	 * @param cid
	 */
	public Content findById(Integer cid) {
		// TODO Auto-generated method stub
		Content content = contentDao.findById(cid);
		System.out.println("xxx"+commentDao.countByContentId(cid));
		content.setCommentsNum(commentDao.countByContentId(cid));
		return content;
	}
	
	public void publish(Content content,String category) {
        if (StringUtils.isBlank(content.getTitle())) {
        	 throw new TipException("文章标题不能为空");
        }
        if (StringUtils.isBlank(content.getContent())) {
        	throw new TipException("文章内容不能为空");
        }
        
        Category c=new Category();
        c.setCaid(Integer.parseInt(category));
        content.setContent(EmojiParser.parseToAliases(content.getContent()));
        content.setCategory(c);
        Long time=new Date().getTime();
        //更新
        if(content.getCid()!=null && !content.getCid().equals("")) {
        	content.setModified(time/1000);
        	contentDao.update(content);
        }else {
        	 //保存
            content.setCreated(time/1000);
            content.setModified(time/1000);
            content.setHits(0);
            content.setCommentsNum(0);
            contentDao.save(content);
        }
       
	}
	public PageInfo<Content> findByCategory(Integer page,Integer pageSize,Integer caid) {
		PageHelper.startPage(page,pageSize);
		PageInfo<Content> info=new PageInfo<>(contentDao.findByCategory(caid));
		return info;
	}
	@Test
	public List<Archive> archives() throws ParseException {
		// TODO Auto-generated method stub
		List<Archive> archives = contentDao.archiveByCreateTime();
		
		for (Archive archive : archives) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(DateKit.dateFormat(archive.getDate(),"yyyy-MM"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			Date parse = sdf.parse(archive.getDate());
			long start = calendar.getTimeInMillis();
			calendar.add(Calendar.MONTH, 1);
			long end = calendar.getTimeInMillis();
			List<Content> lists = contentDao.findByTime(start/1000,end/1000);
			archive.setContents(lists);
		}
		return archives;
	}
	public PageInfo<Content> findByTitleLikeOrContentLike(String keywords) {
		// TODO Auto-generated method stub
		PageHelper.startPage(1, 6);
		List<Content> articles = contentDao.findByTitleLikeOrContentLike("%"+keywords+"%");
		return new PageInfo<Content>(articles);
	}
	
	public List<Content> findTop5NewsArticles(){
		List<Content> contents=contentDao.findTop5NewsArticles();
		for (Content content : contents) {
			int count=commentDao.countByContentId(content.getCid());
			content.setCommentsNum(count);
		}
		return contents;
	}

	public void deleteById(Integer cid) {
		// TODO Auto-generated method stub
		contentDao.deleteById(cid);
	}
}
