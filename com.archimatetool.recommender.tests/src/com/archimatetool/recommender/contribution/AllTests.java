package com.archimatetool.recommender.contribution;

import junit.framework.TestSuite;

public class AllTests {
	
	   public static junit.framework.Test suite() {
			TestSuite suite = new TestSuite("com.archimatetool.recommender.contribution");
			suite.addTest(RecommendationJSONParserTests.suite());
			suite.addTest(ConnectServiceTests.suite());
	       return suite;
		}

}

