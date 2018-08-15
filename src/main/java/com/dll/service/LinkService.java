package com.dll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dll.dao.LinkMapper;
import com.dll.model.Link;

@Service
public class LinkService {
	@Autowired
	private LinkMapper linkDao;

	public List<Link> findAll() {
		// TODO Auto-generated method stub
		return linkDao.findAll();
	}

	public int getTotal() {
		return linkDao.findAll().size();
	}

	public void saveOrUpdate(Link link) {
		// TODO Auto-generated method stub
		if(link.getLid()==null || link.getLid().equals("")) {
			linkDao.save(link);
		}else {
			linkDao.update(link);
		}
	}

	public void delete(String lid) {
		// TODO Auto-generated method stub
		linkDao.delete(Integer.parseInt(lid));
	}

}
