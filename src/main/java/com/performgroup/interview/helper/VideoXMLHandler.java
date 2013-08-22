/**
 * Perform Interview Task
 * @author Mehmet Melik Kose
 */
package com.performgroup.interview.helper;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.performgroup.interview.domain.Category;
import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.exception.CategoryAlreadyExistsException;
import com.performgroup.interview.service.CategoryService;
import com.performgroup.interview.service.VideoService;

public class VideoXMLHandler extends DefaultHandler {

	private Video video;
	private transient VideoService videoService;
	private transient CategoryService categoryService;
	private Set<Category> categories;

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	
	@Resource
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private boolean title;
	private boolean type;
	private boolean path;
	private boolean category;

	public void startDocument() throws SAXException {
		video = new Video();
		video.setCreationDate(new Date());
		categories = new HashSet<Category>();
		video.setCategories(categories);
	//	loadVideoService();
	}
	
	private void loadVideoService() {
		final GenericApplicationContext ctx = new GenericApplicationContext();
		final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		String appContext = System.getProperty("appContext");
		xmlReader.loadBeanDefinitions(new ClassPathResource(appContext));
		ctx.refresh();

		videoService = (VideoService) ctx.getBean("videoService");
	}

	public void startElement(String namespaceURI,
			String localName,
			String qName, 
			Attributes atts)
					throws SAXException {

		if(localName.equals("title")) {
			title = true;
		}
		if(localName.equals("type")) {
			type = true;
		}
		if(localName.equals("path")) {
			path = true;
		}
		if(localName.equals("category")) {
			category = true;
		}

	}

	public void endDocument() throws SAXException {
	//	loadVideoService();
		videoService.addVideo(video);
	}

	public void characters(char[] ch,
			int start,
			int length)
					throws SAXException {
		if(title) {
			video.setTitle(new String(ch, start, length));
			title = false;
		}
		
		if(type) {
			video.setVideoType(VideoType.valueOf(new String(ch, start, length)));
			type = false;
		}
		
		if (path) {
			video.setVideoPath(new String(ch, start, length));
			path = false;
		}
		
		if (category) {
			String catString = new String(ch, start, length);
			Long id;
			Category cat = new Category();
			cat.setCategory(catString);
			try {
				id = categoryService.addCategory(cat);
				cat = categoryService.getCategory(id);
			} catch (CategoryAlreadyExistsException _ex) {
				cat = categoryService.findCategory(catString);
			}
			categories.add(cat);
			category = false;
		}
	}
}
