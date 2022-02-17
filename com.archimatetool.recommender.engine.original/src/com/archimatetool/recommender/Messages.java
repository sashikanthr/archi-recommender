package com.archimatetool.recommender;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "com.archimatetool.recommender.messages"; //$NON-NLS-1$
    
    public static String IRecommender;
    
    public static String Recommender_Object_Type;
    
    public static String Recommender_Description;
    
    public static String Recommender_Similarity_Score;
    
    public static String Recommender_Preference_Settings;
    
    public static String Recommender_Preference_Connector;
    
    public static String Recommender_Preference_Connector_Host;
    
    public static String Recommender_Preference_Connector_Username;
    
    public static String Recommender_Preference_Connector_Password;
    
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
