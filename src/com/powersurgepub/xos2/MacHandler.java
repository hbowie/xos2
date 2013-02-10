/*
 * Copyright 2004 - 2013 Herb Bowie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.powersurgepub.xos2;

  import com.apple.eawt.*;
  import java.io.*;
  import java.net.*;

/**
  A class that will provide Mac-specific support. To prevent run-time errors,
  when running on a non-Mac platform, this Mac-specific code is broken out
  into a separate class. This class is loaded at run-time by XOS, using
  reflection (rather than a direct reference), in order to handle Mac-specific
  situations. <p>
   
   Version History: <ul><li>
      2004/08/10 - Originally written. 
    </ul>
   @author Herb Bowie 
             (<a href="mailto:herb@powersurgepub.com">herb@powersurgepub.com</a>)<br>
           of PowerSurge Publishing 
             (<A href="http://www.powersurgepub.com/">www.powersurgepub.com/</a>)
  
   @version 
      2004/08/10 - Originally written.
 */



public class MacHandler 
    implements  
      AboutHandler,
      OpenFilesHandler,
      OpenURIHandler,
      PreferencesHandler,
      PrintFilesHandler,
      QuitHandler {
	
	/** UI Manager class. */
  private XHandler xHandler;
  private Application macApp;

	/**
	   Constructor.
	 */
	public MacHandler (XHandler xHandler, Application macApp) {
    
		this.xHandler = xHandler;
    this.macApp = macApp;

    macApp.setAboutHandler(this);
    if (xHandler.preferencesAvailable()) {
      macApp.setPreferencesHandler(this);
    }
    macApp.setOpenFileHandler(this);
    macApp.setOpenURIHandler(this);
    macApp.setPrintFileHandler(this);
    macApp.setQuitHandler(this);

	}

  /**
     Standard way to respond to an About Menu Item Selection on a Mac.
   */
  public void handleAbout(AppEvent.AboutEvent e) {
    xHandler.handleAbout();
    // System.out.println("MacHandler.handleAbout received AboutEvent");
  }
  
  /**
     Standard way to respond to a document being passed to this application on a Mac.
   */
  public void openFiles(AppEvent.OpenFilesEvent e) {
    java.util.List<File> files = e.getFiles();
    for (File file : files) {
      xHandler.handleOpenFile (file);
    }
  }

  public void openURI(AppEvent.OpenURIEvent e) {
    URI uri = e.getURI();
  }
  
  /**
     Standard way to respond to preferences being requested.
   */
  public void handlePreferences(AppEvent.PreferencesEvent e) {
    xHandler.handlePreferences ();
    // System.out.println("MacHandler.handlePreferences received PreferencesEvent");
  }
  
  /**
     Standard way to respond to a request to print a document.
   */
  public void printFiles(AppEvent.PrintFilesEvent e) {
    java.util.List<File> files = e.getFiles();
    for (File file : files) {
      xHandler.handlePrintFile(file);
    }
  }

  /**
     Standard way to respond to a Quit Menu Item on a Mac.
   */
  public void handleQuitRequestWith(AppEvent.QuitEvent e,
                           QuitResponse response) {
    // System.out.println("MacHandler.handleQuitRequestWith");
    xHandler.handleQuit();
  }
  
} // end MacHandler class


