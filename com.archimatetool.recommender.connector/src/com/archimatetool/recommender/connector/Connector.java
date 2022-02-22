package com.archimatetool.recommender.connector;

import java.net.URI;

public interface Connector {
	
	void connect();
	
	void send();
	
	URI getURI();
	
	void setURI(URI uri);

}
