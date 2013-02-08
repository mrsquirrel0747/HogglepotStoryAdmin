package com.hogglepot;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.*;
import org.restlet.Context;

import com.hogglepot.resource.*;

public class HogglepotStoryManagerApplication extends Application {
	
	public HogglepotStoryManagerApplication() {
		super();
	}
	
	public HogglepotStoryManagerApplication(Context context) {
		super(context);
	}
	
	public Restlet createInboundRoot() {
		Router router = new Router(this.getContext());
		// List all stories or add a new story
		router.attach("/story", StoriesResource.class);
		// Get details for a specific story
		router.attach("/story/{story_id}", StoryResource.class);
		return router;
		
	}
	
}
