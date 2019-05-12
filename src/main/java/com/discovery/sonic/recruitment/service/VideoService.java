/**
 * 
 */
package com.discovery.sonic.recruitment.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.discovery.sonic.recruitment.error.VideoNotFoundException;
import com.discovery.sonic.recruitment.model.Video;
import com.discovery.sonic.recruitment.repository.VideoRepository;

/**
 * @author Madhu
 *
 */
@Service
public class VideoService {
	
	private static final Logger log = LoggerFactory.getLogger(VideoService.class);
	
	@Autowired
	public VideoRepository videoRepository;
	
	public VideoService() {}
	
	@Autowired
	public VideoService(VideoRepository videoRepository) {
		this.videoRepository = videoRepository;
	}

	public Video save(Video video) {
		video.setId(UUID.randomUUID().toString());
		video = videoRepository.save(video);
		log.info("new video is created successfully : " + video.getTitle());
		return video;
	}

	public Video findVideoById(String id) {
		Video video = videoRepository.findByVideoId(id);
		if (video == null) {
			log.info("video is not found : " + id);
			throw new VideoNotFoundException(id);
		}
		return video;
	}

	public Video findPlayBackInfoById(String id) {
		Video video = new Video();
		Video videoTemp = videoRepository.findByVideoId(id);
		if (null == videoTemp) {
			log.info("video is not found : " + id);
			throw new VideoNotFoundException(id);
		} else {
			video.setCount(videoTemp.getCount());//returning count only
			log.info("video is Found : " + id);
		}
		return video;
	}

	public void reportPlayBack(String id) {
		Video videoTemp = videoRepository.findByVideoId(id);
		if (null == videoTemp) {
			log.info("video is not found : " + id);
			throw new VideoNotFoundException(id);
		} else {
			videoTemp.setCount(videoTemp.getCount() + 1);
			videoRepository.save(videoTemp);
			log.info("video count is updated : " + id);
		}
	}
}
