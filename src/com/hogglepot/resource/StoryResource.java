package com.hogglepot.resource;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hogglepot.QueryStories;
import com.hogglepot.Story;

public class StoryResource extends ServerResource {
	String story_id;
	
	protected void doInit() throws ResourceException {
		// Get the story ID from the URI
        this.story_id = (String) getRequest().getAttributes().get("story_id");
   	}
	
	@Get("xml")
	public Representation toXml() throws Exception {
		Story story = new QueryStories().getStoryContents(story_id);
		
		try {
			DomRepresentation representation = new DomRepresentation(MediaType.TEXT_XML);
			Document d = representation.getDocument();
			Element r = d.createElement("story");
			d.appendChild(r);
			
			Element eltStoryID = d.createElement("story_id");
			eltStoryID.appendChild(d.createTextNode(story.getStoryID()));
			r.appendChild(eltStoryID);
			
			Element eltTitle = d.createElement("title");
			eltTitle.appendChild(d.createTextNode(story.getStoryTitle()));
			r.appendChild(eltTitle);
			
			Element eltDate = d.createElement("date");
			eltDate.appendChild(d.createTextNode(story.getStoryDate()));
			r.appendChild(eltDate);
			
			Element eltContent = d.createElement("content");
			eltContent.appendChild(d.createTextNode(story.getStoryContent()));
			r.appendChild(eltContent);
			
			Element eltAuthorID = d.createElement("author_id");
			eltAuthorID.appendChild(d.createTextNode(story.getAuthorID()));
			r.appendChild(eltAuthorID);
						
			d.normalizeDocument();
			return representation;
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
