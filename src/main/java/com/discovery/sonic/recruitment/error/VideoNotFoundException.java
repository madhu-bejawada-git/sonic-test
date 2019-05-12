/**
 * 
 */
package com.discovery.sonic.recruitment.error;

/**
 * @author Madhu
 *
 */
public class VideoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VideoNotFoundException(String id) {
		super("Video id is not found : " + id);
	}

}
