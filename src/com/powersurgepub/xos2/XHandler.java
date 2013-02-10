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

  import java.io.*;
  import java.net.*;
  
/**
   A standard interface for a user interfacing program designed to
   handle various standard events in a cross-platform manner. <p>
   
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

