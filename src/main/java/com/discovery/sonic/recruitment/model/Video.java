/**
 * 
 */
package com.discovery.sonic.recruitment.model;

/**
 * @author Madhu
 *
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotEmpty;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video {

	@Id
	private String id;
	@NotEmpty(message = "Please provide a title")
	private String title;
	private int count;

	public Video() {
	}

	public Video(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", count=" + count + "]";
	}

}
