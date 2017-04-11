package com.trello.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class jkkjhk extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.trello.feature.launch.LaunchRoutingActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public jkkjhk() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.trello.feature.launch.LaunchRoutingActivity'
		solo.waitForActivity("LaunchRoutingActivity", 2000);
        //Click on Seng437
		solo.clickOnText(java.util.regex.Pattern.quote("Seng437"));
        //Wait for activity: 'com.trello.feature.board.BoardActivity'
		assertTrue("BoardActivity is not found!", solo.waitForActivity("BoardActivity"));
        //Enter the text: 'To Do'
		solo.clearEditText((android.widget.EditText) solo.getView("listName"));
		solo.enterText((android.widget.EditText) solo.getView("listName"), "To Do");
        //Enter the text: 'Doing'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 1));
		solo.enterText((android.widget.EditText) solo.getView("listName", 1), "Doing");
        //Enter the text: 'Done'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 2));
		solo.enterText((android.widget.EditText) solo.getView("listName", 2), "Done");
        //Enter the text: 'New list'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 3));
		solo.enterText((android.widget.EditText) solo.getView("listName", 3), "New list");
        //Enter the text: 'New slide 1'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 4));
		solo.enterText((android.widget.EditText) solo.getView("listName", 4), "New slide 1");
        //Enter the text: 'To Do'
		solo.clearEditText((android.widget.EditText) solo.getView("listName"));
		solo.enterText((android.widget.EditText) solo.getView("listName"), "To Do");
        //Enter the text: 'Doing'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 1));
		solo.enterText((android.widget.EditText) solo.getView("listName", 1), "Doing");
        //Enter the text: 'Done'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 2));
		solo.enterText((android.widget.EditText) solo.getView("listName", 2), "Done");
        //Enter the text: 'New list'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 3));
		solo.enterText((android.widget.EditText) solo.getView("listName", 3), "New list");
        //Enter the text: 'New slide 1'
		solo.clearEditText((android.widget.EditText) solo.getView("listName", 4));
		solo.enterText((android.widget.EditText) solo.getView("listName", 4), "New slide 1");
        //Click on Empty Text View
		solo.clickOnView(solo.getView("board_sections"));
        //Click on Activity
		solo.clickOnView(solo.getView("activity_button"));
	}
}
