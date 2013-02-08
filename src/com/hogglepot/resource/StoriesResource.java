package com.hogglepot.resource;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hogglepot.QueryStories;
import com.hogglepot.Story;

public class StoriesResource extends ServerResource {
	
	/**
	 * Create a new story
	 * @return Representation
	 * @throws Exception
	 */
	@Post
	public Representation acceptStory(Representation entity) throws Exception {
		Representation result = null;
		Form form = new Form(entity);
		String storyTitle = form.getFirstValue("title");
		String storyDate = form.getFirstValue("date");
		String storyContent = form.getFirstValue("content");
		String authorID = form.getFirstValue("author_id");
		
		int response = new QueryStories().addStory(storyTitle, storyDate, storyContent, authorID);
		System.out.println(response);
		if (response == 0) {
			setStatus(Status.SUCCESS_CREATED);
			Representation rep = new StringRepresentation("Story created.", MediaType.TEXT_PLAIN);
			result = rep;
		} else {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			result = generateErrorRepresentation("Story was not added.", "1");
		}
		return result;
	}
	
	private Representation generateErrorRepresentation(String errorMessage,
			String errorCode) {
		DomRepresentation result = null;

		try {
			result = new DomRepresentation(MediaType.TEXT_XML);
			Document d = result.getDocument();
			Element eltError = d.createElement("error");
			Element eltCode = d.createElement("code");
			eltCode.appendChild(d.createTextNode(errorCode));
			eltError.appendChild(eltCode);
			Element eltMessage = d.createElement("message");
			eltMessage.appendChild(d.createTextNode(errorMessage));
			eltError.appendChild(eltMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	/**
	 * GET a list of stories
	 * @return Representation
	 * @throws Exception
	 */
	@Get("xml")
	public Representation toXml() throws Exception {
		try {
			DomRepresentation representation = new DomRepresentation(MediaType.TEXT_XML);
			Document d = representation.getDocument();
			Element r = d.createElement("stories");
			d.appendChild(r);
			ConcurrentMap<String, Story> qs = new QueryStories().queryStoryList();
			for (Story story : qs.values()) {
				Element eltStory = d.createElement("story");
				Element eltStoryID = d.createElement("story_id");
				eltStoryID.appendChild(d.createTextNode(story.getStoryID()));
				eltStory.appendChild(eltStoryID);
				Element eltTitle = d.createElement("title");
				eltTitle.appendChild(d.createTextNode(story.getStoryTitle()));
				eltStory.appendChild(eltTitle);
				Element eltDate = d.createElement("date");
				eltDate.appendChild(d.createTextNode(story.getStoryDate()));
				eltStory.appendChild(eltDate);
				r.appendChild(eltStory);
			}
			d.normalizeDocument();
			return representation;
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
