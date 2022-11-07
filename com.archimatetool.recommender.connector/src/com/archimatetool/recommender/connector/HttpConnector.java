package com.archimatetool.recommender.connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.apache.http.client.utils.URIBuilder;

public class HttpConnector implements Connector {

	private HttpClient client;

	private HttpRequest request;

	private HttpResponse<String> response;

	// TODO - URL endpoint defined in Preferences or maintain an init file
	
	private URI uri;
	
	public HttpConnector() {
		
	}
	
	public HttpConnector(URI uri) {
		this.uri = uri;
	}
	

	@Override
	public void send() {
		sendSync();
	}
	
	/*
	 * Method for synchronous connection
	 */
	
	private void sendSync() {
		ConnectorService connectorService = ConnectorService.getInstance();	
		try {						
			client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
			request = HttpRequest.newBuilder().uri(uri).timeout(Duration.ofSeconds(5)).header("Accept", "application/json")
					.build();
			response = client.send(request, BodyHandlers.ofString());
			
			
				if (response != null && HttpURLConnection.HTTP_OK == response.statusCode()) {
					String body = response.body();
												
					connectorService.sendResponse(uri, body);			
					
				}
			
			
			} catch (IOException | InterruptedException e) {
				connectorService.sendResponse(uri,"[]");
			}
	}
	
	/*
	 * Asynchronous connection is leading to an Invalid Thread access error
	 */
	
	
	private void sendAsync() {		
			
			client = HttpClient.newBuilder().connectTimeout(Duration.ofMinutes(1)).build();
			request = HttpRequest.newBuilder().uri(uri).timeout(Duration.ofMinutes(1)).header("Accept", "application/json")
					.build();
			CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, BodyHandlers.ofString());			
			futureResponse.whenComplete((response, error) -> {
				if (error == null && response != null && HttpURLConnection.HTTP_OK == response.statusCode()) {
					String body = response.body();
					ConnectorService connectorService = ConnectorService.getInstance();					
					connectorService.sendResponse(uri, body);				
					
				} else if(error!=null) {
					throw new RuntimeException("Unable to connect");
				}
			});			
			
	}

	@Override
	public URI getURI() {

		return uri;
	}

	public static void main(String[] args) {

		try {
		Connector connector = new HttpConnector(new URIBuilder("http://localhost:5000/recommendations").addParameter("id", "id-2b4d61b061724b9198fcbc626f44b5df" ).build());
		connector.send();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void connect() {
		
	}

	@Override
	public void setURI(URI uri) {
		this.uri = uri;
		
	}
}
