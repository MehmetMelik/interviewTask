package com.performgroup.interview.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(
    name="CAT_SEQ_GEN",
    sequenceName="CATSEQ",
    allocationSize=1
)
public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7242795471482102606L;

	private Long id;
	private String category;
	private Set<Video> videos = new HashSet<Video>();
	
	public Category() {

	}
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CAT_SEQ_GEN")
	@Column(name = "category_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "category", unique = true, nullable = false)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the videos
	 */
	@ManyToMany(mappedBy="categories", fetch = FetchType.EAGER)
	public Set<Video> getVideos() {
		return videos;
	}

	/**
	 * @param videos the videos to set
	 */
	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

}
