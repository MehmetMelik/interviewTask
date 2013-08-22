package com.performgroup.interview.service.impl;

import java.sql.SQLException;
import java.util.Collection;

import javax.annotation.Resource;

import com.performgroup.interview.dao.VideoDAO;
import com.performgroup.interview.dao.VideoReportingDAO;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.service.ReportService;

public class ReportServiceImpl implements ReportService {
	
	private VideoReportingDAO videoReportingDAO;
	
	public VideoReportingDAO getVideoReportingDAO() {
		return videoReportingDAO;
	}
	
	@Resource
	public void setVideoReportingDAO(VideoReportingDAO videoReportingDAO) {
		this.videoReportingDAO = videoReportingDAO;
	}

	public VideoReportingBean countForVideoType(VideoType videoType) {
		return getVideoReportingDAO().countForVideoType(videoType);
	}

	public Collection<VideoReportingBean> countByVideoType()
			throws SQLException {
		return getVideoReportingDAO().countByVideoType();
	}

	public Collection<VideoReportingBean> countByDay() {
		return getVideoReportingDAO().countByDay();
	}

}
