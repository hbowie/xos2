package com.powersurgepub.xos2;

  import java.io.*;
  import java.net.*;
  
/**
   A standard interface for a user interfacing program designed to
   handle various standard events in a cross-platform manner. <p>
  
   This code was developed by Herb Bowie. Copyright was voluntarily relinquished
   in 2004, placing the source and executables into the public domain. <p>
   
   Version History: <ul><li>
      0.9 - 2004/08/07 - Originally written. 
    </ul>
   @author Herb Bowie of PowerSurge Publishing
  
   @version 0.9, 2004/08/07, Originally written.
 */

public interface XHandler {

  /**
     Standard way to respond to an About Menu Item Selection on a Mac.
   */
  public void handleAbout();
  
  /**      
    Standard way to respond to a document being passed to this application on a Mac.
   
    @param inFile File to be processed by this application, generally
                  as a result of a file or directory being dragged
                  onto the application icon.
   */
  public void handleOpenFile (File inFile);

  /**
   Standard way to respond to a URI being passed to this application on a Mac.

   @param inURI URI to be processed by this application.
   */
  public void handleOpenURI (URI inURI);

  /**
   Are there any application preferences to be made available to the users?

   @return True if preferences are available, false otherwise. 
   */
  public boolean preferencesAvailable();
  
  /**
    Standard way to respond to a request to display preferences.
   */
  public void handlePreferences();
  
  /**      
    Standard way to respond to a print request.
   
    @param inFile File to be printed by this application.
   */
  public void handlePrintFile (File inFile);

  /**
     Standard way to respond to a Quit Menu Item on a Mac.
   */
  public void handleQuit();
    
}

