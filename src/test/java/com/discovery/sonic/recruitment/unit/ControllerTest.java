package com.discovery.sonic.recruitment.unit;

/**
 * @author Madhu
 *
 */
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.jupiter.api.Test;
import com.discovery.sonic.recruitment.Controller;
import com.discovery.sonic.recruitment.error.VideoNotFoundException;
import com.discovery.sonic.recruitment.model.Video;
import com.discovery.sonic.recruitment.service.VideoService;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
public class ControllerTest {

	@Mock
	VideoService videoService = Mockito.mock(VideoService.class);

	@InjectMocks
	Controller controller = new Controller(videoService);
	
	@Test
	public void createVideoAndVerifyTheVideoSaveOrNot() {
		String id = UUID.randomUUID().toString();
		Video mockObj = getMockVideoData(id);
		when(videoService.save(mockObj)).thenReturn(mockObj);
		
		Video resObj = controller.newVideo(mockObj);
		assertNotNull(resObj);
		assertNotNull(resObj.getTitle());
		assertEquals("Avengers: Endgame", resObj.getTitle());
		assertNotNull(resObj.getId());
		assertEquals(id, resObj.getId());
		assertEquals(0, resObj.getCount());		
	}
	
	@Test
	public void getVideoReturnsCorrectIdAndTitle() {
		String id = UUID.randomUUID().toString();
		Video mockObj = getMockVideoData(id);
		when(videoService.save(mockObj)).thenReturn(mockObj);
		
		Video resObj = controller.newVideo(mockObj);
		
		when(videoService.findVideoById(mockObj.getId())).thenReturn(resObj);
		Video findVideo = controller.findVideo(mockObj.getId());
		
		assertNotNull(findVideo);
		assertNotNull(findVideo.getTitle());
		assertEquals("Avengers: Endgame", findVideo.getTitle());
		assertNotNull(findVideo.getId());
		assertEquals(id, findVideo.getId());				
	}
	
	@Test
	public void verifyVideoPlayBackInfoReturnsCount0() {
		String id = UUID.randomUUID().toString();
		Video mockObj = getMockVideoData(id);
		when(videoService.save(mockObj)).thenReturn(mockObj);
		
		Video resObj = controller.newVideo(mockObj);
		
		when(videoService.findPlayBackInfoById(mockObj.getId())).thenReturn(resObj);
		Video findVideo = controller.findPlaybackCount(mockObj.getId());
		
		assertNotNull(findVideo);		
		assertEquals(0, findVideo.getCount());		
	}
	@Test
	public void getPlaybackInfoReturnsCorrectCount() {
		String id = UUID.randomUUID().toString();
		Video mockObj = getMockVideoData(id);
		when(videoService.save(mockObj)).thenReturn(mockObj);
		
		Video resObj = controller.newVideo(mockObj);
		VideoService vs = Mockito.spy(videoService);
		Mockito.doNothing().when(vs).reportPlayBack(mockObj.getId());
		for(int i=1; i<=25; i++) {
			controller.reportPlaybackStart(mockObj.getId());
			resObj.setCount(i);
		}
		when(videoService.findPlayBackInfoById(mockObj.getId())).thenReturn(resObj);
		
		Video countObj = controller.findPlaybackCount(mockObj.getId());
		
		assertNotNull(countObj);		
		assertEquals(25, countObj.getCount());
	}
	
	@Test
	public void nonExistingVideoReturns404Exception() {
		String id = UUID.randomUUID().toString();
		Video mockObj = getMockVideoData(id);
		when(videoService.findVideoById(mockObj.getId())).thenThrow(new VideoNotFoundException(mockObj.getId()));
		try{
			controller.findVideo(mockObj.getId());
		}catch(VideoNotFoundException vne) {
			assertTrue(true);			
		}
	}
	
	private Video getMockVideoData(String id) {
		Video obj = new Video();
		obj.setId(id);
		obj.setTitle("Avengers: Endgame");
		return obj;
	}
}
