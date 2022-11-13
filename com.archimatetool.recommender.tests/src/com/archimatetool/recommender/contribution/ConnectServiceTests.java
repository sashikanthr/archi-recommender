package com.archimatetool.recommender.contribution;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.archimatetool.recommender.connector.ConnectorService;
import com.archimatetool.recommender.connector.RecommendationSubscriber;

import junit.framework.JUnit4TestAdapter;

public class ConnectServiceTests {

	private static ConnectorService connectorService;

	private static RecommendationSubscriber recommendationSubscriber;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ConnectServiceTests.class);
	}

	@BeforeClass
	public static void runOnceBeforeAllTests() throws IOException {
		connectorService = ConnectorService.getInstance();
	}

	@Test
	public void testAddSubscriber() {
		recommendationSubscriber = new RecommendationSubscriber() {

			@Override
			public void subscribe(RecommendationSubscriber subscriber, String id) {

			}

			@Override
			public void receive(String response) {				
				assertTrue(connectorService.getSubscriberMap().size() == 1);

			}
		};
		connectorService.addSubscriber(recommendationSubscriber, null);
	}
	
	@Test
	public void testSendResponse() {
		
		recommendationSubscriber = new RecommendationSubscriber() {

			@Override
			public void subscribe(RecommendationSubscriber subscriber, String id) {

			}

			@Override
			public void receive(String response) {				
				assertTrue(true);

			}
		};
		connectorService.addSubscriber(recommendationSubscriber, null);
		
	}
	
}
