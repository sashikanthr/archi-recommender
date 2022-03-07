package com.archimatetool.recommender.contribution;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "com.archimatetool.recommender.contribution.messages"; //$NON-NLS-1$
    
    public static String Recommender;
    
    public static String Recommendation_Documentation;
    
    public static String Recommendation_Name;
    
    public static String Recommendation_Similarity_Score;
    
    public static String Recommendation_Action_Reveal_Object;
    
    public static String Recommendation_Action_Replace_Object;
    
    public static String Recommendation_Command_Rename;
    
    public static String Recommendation_Action_Replace_Object_Tooltip;
    
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
    
}
