package com.archimatetool.recommender;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.archimatetool.editor.preferences.IPreferenceConstants;
import com.archimatetool.editor.ui.UIUtils;


public class RecommenderPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IPreferenceConstants {
	
	
	private static String HELP_ID = "com.archimatetool.help.prefsRecommender";
	
	private Text fHostField;
	
	private Text fUsernameField;
	
	private Text fUserPasswordField;
	
	private boolean isUpdatedFromListener = false;
	
	public RecommenderPreferencePage() {
		setPreferenceStore(ArchiRecommenderPlugin.INSTANCE.getPreferenceStore());
	}
	

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Control createContents(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
		Composite client = new Composite(parent,SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		client.setLayout(layout);
		
		Group settingsGroup = new Group(client, SWT.NULL);
		settingsGroup.setText(Messages.Recommender_Preference_Settings);
		settingsGroup.setLayout(new GridLayout(3, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 500;
		settingsGroup.setLayoutData(gd);
		
		Group connectors = new Group(client, SWT.BUTTON1);
		connectors.setText(Messages.Recommender_Preference_Connector);
		connectors.setLayout(new GridLayout(2, false));		
		settingsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
		Label label = new Label(connectors,SWT.NULL);
		label.setText(Messages.Recommender_Preference_Connector_Host);
		fHostField = UIUtils.createSingleTextControl(connectors, SWT.BORDER, false);
		fHostField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
		label = new Label(connectors,SWT.NULL);
		label.setText(Messages.Recommender_Preference_Connector_Username);
        fUsernameField = UIUtils.createSingleTextControl(connectors, SWT.BORDER, false);
        fUsernameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
		label = new Label(connectors,SWT.NULL);
		label.setText(Messages.Recommender_Preference_Connector_Password);
		fUserPasswordField = UIUtils.createSingleTextControl(connectors, SWT.BORDER, false);
		fUserPasswordField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		fHostField.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {				
						
			}
        }
        );
		
		
		fUsernameField.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				
							
			}
        }
        );
        
		fUserPasswordField.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
			}
		}
	
        
        );
        
        Button folderButton = new Button(connectors, SWT.PUSH);
        folderButton.setText("Test Connection");        
        folderButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String folderPath = "";
                if(folderPath != null) {
                    fUsernameField.setText(folderPath);
                }
            }
        });
		
		return client;
	}
	
	

}
