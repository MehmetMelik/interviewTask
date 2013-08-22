package com.performgroup.interview.service;

import java.sql.SQLException;
import java.util.Collection;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

public interface ReportService {
	
	VideoReportingBean countForVideoType(VideoType videoType);
	
	Collection<VideoReportingBean> countByVideoType() throws SQLException;
	
	Collection<VideoReportingBean> countByDay();

}
