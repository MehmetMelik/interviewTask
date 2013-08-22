package com.performgroup.interview.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.Hibernate;

/**
 * A POJO representing a video in the Perform system. 
*/

@Entity
@SequenceGenerator(
    name="VID_SEQ_GEN",
    sequenceName="VIDSEQ",
    allocationSize=1
)
public class Video implements Serializable {

	private static final long serialVersionUID = 2284488937952510797L;

	private Long id;
	private String title;
	private String videoPath;
	private VideoType videoType;
	private Date creationDate;

	private Set<Category> categories = new HashSet<Category>();

	// Default Constructor
	public Video(){

	}

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="VID_SEQ_GEN")
	@Column(name = "video_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", unique = false, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "video_path", unique = false, nullable = false)
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	@Column(name = "video_type", unique = false, nullable = false)
	@Enumerated(value=EnumType.STRING)
	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}

	@Column(name = "creation_date", unique = false, nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * @return the categories
	 */
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="VIDEO_CATEGORY", 
                joinColumns={@JoinColumn(name="video_id")}, 
                inverseJoinColumns={@JoinColumn(name="category_id")})
	public Set<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public String loadCategories() {
		if (this.getCategories() != null) {
			Iterator<Category> it = this.getCategories().iterator();
			StringBuffer strBuf = new StringBuffer();
			while (it.hasNext()) {
				strBuf.append(it.next().getCategory());
				if (it.hasNext())
					strBuf.append(", ");
			}
			strBuf.trimToSize();
			return strBuf.toString();
		} else
			return "";
	}

}
