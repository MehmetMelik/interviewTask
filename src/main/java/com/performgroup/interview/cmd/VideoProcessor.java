package com.performgroup.interview.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.helper.VideoXMLHandler;
import com.performgroup.interview.service.ReportService;
import com.performgroup.interview.service.VideoService;

public class VideoProcessor {

	private transient VideoService videoService;
	private transient ReportService reportService;
	
	private transient String videoIngestFolder;
	
	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	
	@Resource
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	public void setVideoIngestFolder(String videoIngestFolder) {
		this.videoIngestFolder = videoIngestFolder;
	}

	/**
	 * Outputs video details to the specified logger
	 * @param logger
	 */
	public void listVideos(Logger logger) {
		
		Collection<Video> videos = videoService.listVideos();
		
		for (Video video : videos) {			
			String videoData = String.format("[%d] - %s / %s - %s / %s", video.getId(), video.getTitle(), video.getVideoType(), video.getVideoPath(), video.loadCategories());
			logger.info(videoData);
		}
	}
	
	/**
	 * Processes a video by ingesting data from XML
	 * @param logger
	 * @param videoFile
	 */
	public void ingestVideo(Logger logger, String videoFile) {
		
		String path = videoIngestFolder+videoFile;
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
		if (in == null) {
			logger.info("Cannot find file");
		}
		else {
			// TODO
			final GenericApplicationContext ctx = new GenericApplicationContext();
			final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
			String appContext = System.getProperty("appContext");
			xmlReader.loadBeanDefinitions(new ClassPathResource(appContext));
			ctx.refresh();

			VideoXMLHandler videoXML = (VideoXMLHandler) ctx.getBean("videoXML");

			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			try {
				SAXParser saxParser = factory.newSAXParser();
				XMLReader xmlReader2 = saxParser.getXMLReader();
				xmlReader2.setContentHandler(videoXML);
				xmlReader2.parse(new InputSource(in));
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void reportByType() {
		try {
			Collection<VideoReportingBean> coll = reportService.countByVideoType();
			for(VideoReportingBean vrb : coll) {
				System.out.println(vrb.getDescription() + "\t" + vrb.getCount());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reportForAType(VideoType videoType) {
		VideoReportingBean vrb = reportService.countForVideoType(videoType);
		System.out.println(vrb.getDescription() + "\t" + vrb.getCount());
	}
	
	public void reportByDay() {
		Collection<VideoReportingBean> coll = reportService.countByDay();
		for(VideoReportingBean vrb : coll) {
			System.out.println(vrb.getDescription() + "\t" + vrb.getCount());
		}
	}
}
