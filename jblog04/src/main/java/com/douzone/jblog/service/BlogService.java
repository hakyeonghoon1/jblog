package com.douzone.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	private static String SAVE_PATH = "/upload-images/";
	private static String URL_BASE = "/images";
	
	private String generateSaveFilename(String extName) {
		String filename ="";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("."+extName);
		return filename;
	}

	public void update(String title, MultipartFile multipartFile, String userId) {
		System.out.println("updateBasic.service");
		String url =null;
		try {
			
			
			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1);
			String saveFilename = generateSaveFilename(extName);
			
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFilename);
			os.write(data);
			os.close();
			
			url = URL_BASE+"/"+saveFilename;
			
			if(multipartFile.isEmpty()) {
				url =null;
			}
			
			BlogVo vo = new BlogVo();
			vo.setTitle(title);
			vo.setId(userId);
			vo.setLogo(url);
			blogRepository.update(vo);
			System.out.println(vo);
		} catch(IOException e) {
			throw new RuntimeException("file upload error : "+e);
		}

		
	}

	public BlogVo getBlogBasic(String BlogId) {
		
		return blogRepository.getBlogBasic(BlogId);
		 
	}

	public List<CategoryVo> getBlogCategory(String blogId) {
		List<CategoryVo> list = new ArrayList<>();
		list = blogRepository.getBlogCategory(blogId);
		return list;
		
	}

	public void insertCategory(CategoryVo vo) {
		blogRepository.insertCategory(vo);
		
	}

	public void insertPost(PostVo vo) {
		blogRepository.insertPost(vo);
		
	}

	public List<PostVo> getPostList(Long categoryNo, String blogId) {
		return blogRepository.getPostList(categoryNo,blogId);
		
	}

	public PostVo getPost(String blogId, Long categoryNo, Long postNo) {
		
		return blogRepository.getPost(blogId, categoryNo, postNo);
	}

	public Long getRecentPostCategoryNo(String blogId) {
		
		return blogRepository.getRecentPostCategoryNo(blogId);
	}

	public void deleteCategory(Long categoryNo) {
		blogRepository.deleteCategory(categoryNo);
		
	}
}
