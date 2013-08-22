package com.performgroup.interview.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

/**
 * The JDBC implementation of the reporting DAO. Needs to remain JDBC driven for
 * the purpose of this task (as opposed to Hibernate).
 */
public class VideoReportingJDBCDAO extends JdbcDaoSupport implements
		VideoReportingDAO {

	public Collection<VideoReportingBean> countByDay() {
		JdbcTemplate jdbcTemplate = createJdbcTemplate(getDataSource());
		List<VideoReportingBean> list = new ArrayList<VideoReportingBean>();
		@SuppressWarnings({ "unchecked" })
		List<Map<String,Object>> rows = jdbcTemplate
				.queryForList("select cast(creation_date as date) as day, count(*) as c from video group by cast(creation_date as date)");
		for (Map<String,Object> row : rows) {
			VideoReportingBean vrb = new VideoReportingBean();
			vrb.setDescription(((Date) row.get("day")).toString());
			vrb.setCount((Integer) row.get("c"));
			list.add(vrb);
		}

		return list;
	}

	public Collection<VideoReportingBean> countByVideoType() {
		Connection con = getConnection();
		Statement stmt = null;
		String query = "select video_type, count(*) as c from video group by video_type";
		List<VideoReportingBean> list = new ArrayList<VideoReportingBean>();
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				VideoReportingBean vrb = new VideoReportingBean();
				vrb.setDescription(rs.getString("video_type"));
				vrb.setCount(rs.getInt("c"));
				list.add(vrb);
			}
		} catch (SQLException _ex) {
			_ex.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			releaseConnection(con);
		}
		return list;
	}

	public VideoReportingBean countForVideoType(VideoType videoType) {
		JdbcTemplate jdbcTemplate = createJdbcTemplate(getDataSource());
		VideoReportingBean vrb = (VideoReportingBean) jdbcTemplate
				.queryForObject(
						"select video_type, count(*) as c from video where video_type = ? group by video_type",
						new Object[] { new String(videoType.toString()) },
						new RowMapper() {
							public Object mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								VideoReportingBean vrb = new VideoReportingBean();
								vrb.setDescription(rs.getString("video_type"));
								vrb.setCount(rs.getInt("c"));
								return vrb;
							}

						});
		return vrb;
	}

}
