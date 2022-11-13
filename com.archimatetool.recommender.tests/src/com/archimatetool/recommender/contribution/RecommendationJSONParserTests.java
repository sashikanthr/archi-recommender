package com.archimatetool.recommender.contribution;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.archimatetool.model.IArchimateModel;
import com.archimatetool.recommender.contribution.RecommendationJSONParser;
import com.archimatetool.recommender.model.Recommendation;
import com.archimatetool.testingtools.ArchimateTestModel;
import com.archimatetool.tests.TestData;

import junit.framework.JUnit4TestAdapter;

public class RecommendationJSONParserTests {
	
	private static IArchimateModel model;	
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RecommendationJSONParserTests.class);
	}
	
	
    @BeforeClass
    public static void runOnceBeforeAllTests() throws IOException {
        ArchimateTestModel tm = new ArchimateTestModel(TestData.TEST_MODEL_FILE_ARCHISURANCE);
        model = tm.loadModel();
    }
    
    @Test
    public void testEmptyResponse() {
    	List<Recommendation> recommendations = RecommendationJSONParser.parseJson("");
    	assertTrue(recommendations.isEmpty());
    	recommendations = RecommendationJSONParser.parseJson("[]");
    	assertTrue(recommendations.isEmpty());
    }
    
    @Test
    public void testCorrectJSON() {
    	List<Recommendation> recommendations = RecommendationJSONParser.parseJson(TestJSONData.STANDARD_RESPONSE);
    	assertTrue(recommendations.size()==2);    	
    }
    
    @Test
    public void testInvalidJSON() {
    	List<Recommendation> recommendations = RecommendationJSONParser.parseJson(TestJSONData.INVALID_RESPONSE);
    	assertTrue(recommendations.size()==1);    	
    }   

}
