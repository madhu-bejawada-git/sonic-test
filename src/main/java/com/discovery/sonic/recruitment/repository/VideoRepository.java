package com.discovery.sonic.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.discovery.sonic.recruitment.model.Video;
import org.springframework.data.jpa.repository.*;

/**
 * @author Madhu
 *
 */

public interface VideoRepository extends JpaRepository<Video, String> {
	@Query("SELECT v FROM Video v WHERE v.id = ?1")
	Video findByVideoId(String videoId);
}
