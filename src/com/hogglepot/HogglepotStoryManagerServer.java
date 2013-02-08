package com.hogglepot;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class HogglepotStoryManagerServer {

	// Start a server 
	public static void main(String[] args) throws Exception {
		Component component = new Component();
		component.getServers().add(Protocol.HTTP,55123);
		component.getDefaultHost().attach("/hpsm", new HogglepotStoryManagerApplication());
		component.start();
	}

}
