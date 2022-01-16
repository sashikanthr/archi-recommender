package com.archimatetool.recommender;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "com.archimatetool.recommender.messages"; //$NON-NLS-1$
    
    public static String IRecommender;
    
    public static String Recommender_Object_Type;
    
    public static String Recommender_Description;
    
    public static String Recommender_Similarity_Score;    
    
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
