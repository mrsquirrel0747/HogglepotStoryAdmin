package com.hogglepot;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class StoryAdmin {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ResourceException 
	 */
	public static void main(String[] args) throws ResourceException, IOException {
		// TODO Auto-generated method stub
		ClientResource storiesResource = new ClientResource(
				"http://localhost:55123/hpsm/story");
		ClientResource storyResource1 = new ClientResource(
				"http://localhost:55123/hpsm/story/1");
		
		get(storiesResource);
		get(storyResource1);
		Story item = new Story("Foy", "20130203", 
				"Tm8gb25lIGV2ZXIgdGFsa3MgYWJvdXQgdGhlIGVsZGVycyBsZWF2aW5nLiBJdCBpcyBhIHRhYm9vI", "4");
        storiesResource.post(getRepresentation(item));
        get(storiesResource);
	}

	 public static void get(ClientResource clientResource) throws IOException,
     ResourceException {
		 clientResource.get();
		 if (clientResource.getStatus().isSuccess()
				 && clientResource.getResponseEntity().isAvailable()) {
			 clientResource.getResponseEntity().write(System.out);
		 }
	 }
	 
	 public static Representation getRepresentation(Story story) {
	        // Gathering information into a Web form and POST it to server.
	        Form form = new Form();
	        form.add("title", story.getStoryTitle());
	        form.add("date", story.getStoryDate());
	        form.add("content", story.getStoryContent());
	        form.add("author_id", story.getAuthorID());
	        return form.getWebRepresentation();
	    }
}
