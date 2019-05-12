package com.discovery.sonic.recruitment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.discovery.sonic.recruitment.model.Video;
import com.discovery.sonic.recruitment.service.VideoService;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;

@RestController
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	public Controller(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping("ping")
	public String ping() {
		return "pong";
	}

	// Create Video
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/video")
	public Video newVideo(@Valid @RequestBody Video video) {
		log.info("new request title is: " + video.getTitle());
		return videoService.save(video);
	}

	// Find Video
	@GetMapping("/video/{id}")
	public Video findVideo(@PathVariable String id) {
		log.info("request id is: " + id);
		return videoService.findVideoById(id);
	}

	// report playBack start of video
	@PostMapping(value = "/video/{id}/playbackReport")
	public void reportPlaybackStart(@PathVariable String id) {
		log.info("report playback request id is: " + id);
		videoService.reportPlayBack(id);
	}

	// Find playBackInfo
	@GetMapping("/video/{id}/playbackInfo")
	public Video findPlaybackCount(@PathVariable String id) {
		log.info("playback info request id is: " + id);
		return videoService.findPlayBackInfoById(id);
	}

}
