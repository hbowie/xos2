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

  import java.awt.*;
  import java.io.*;
  import javax.swing.*;

/**
  A generic file chooser capable of invoking either the Swing JFileChooser
  component or the older AWT component FileDialog, based on user preferences. 
  If no preference has been specified by the user for the currently running
  program, then we will default to the native AWT FileDialog on a Mac, and to 
  JFileChooser on other platforms. The older AWT FileDialog is useful
  especially on a Mac, where it uses the native file chooser, rather
  than the generic Swing representation. <p>
 
  Note that the native FileChooser API is not as robust as the JFileChooser API,
  so in certain situations (as when the program wants to allow the user to 
  choose a directory or a file) this class will not
  be able to respect the user's preference for a native file chooser. <p>
 
  Following is typical code that would be used to access XFileChooser. <p>
 
  <pre><code>    
    XFileChooser chooser = new XFileChooser (); 
    chooser.setFileSelectionMode(XFileChooser.FILES_AND_DIRECTORIES); 
    chooser.setCurrentDirectory (store.getToDoFile()); 
    File result = chooser.showOpenDialog (frame); 
    if (result != null) { 
      File openFile = chooser.getSelectedFile(); 
    } 
  </code></pre>
   
   Version History: <ul><li>
      0.9 - 2004/08/10 - Originally written. 
    </ul>
   @author Herb Bowie of PowerSurge Publishing
  
   @version 0.9 - 2004/08/10 - Originally written
 */
public class XFileChooser {
  
  /** String that will be used as a key for user preferences. */
  public  static final  String        FILE_CHOOSER_KEY    = "filechooser";
  
  /** User preference for JFileChooser. */
  public  static final  String        FILE_CHOOSER_SWING  = "Swing";
  
  /** User preference for the native file chooser. */
  public  static final  String        FILE_CHOOSER_AWT    = "AWT";
  
  /** Value for specifying that only files may be selected (the default). */
  public  static final  int           FILES_ONLY          
      = JFileChooser.FILES_ONLY;
  
  /** 
   Value for specifying that only directories may be selected (this will
   force the use of JFileChooser).
   */
  public  static final  int           DIRECTORIES_ONLY          
      = JFileChooser.DIRECTORIES_ONLY;
  
  /** 
   Value for specifying that either files or directories may be selected (this 
   will force the use of JFileChooser).
   */
  public  static final  int           FILES_AND_DIRECTORIES          
      = JFileChooser.FILES_AND_DIRECTORIES;
  
  private               XOS           xos                 = XOS.getShared();
  //* Default to use of Swing Chooser
  private               boolean       useSwingChooser     = true;
  private               JFileChooser  swingChooser;
  private               FileDialog    awtChooser;
  private               int           fileSelectionMode 
                                          = JFileChooser.FILES_ONLY;
  private               File          currentDirectory    = null;
  private               String        dialogTitle;
  private               File          selectedFile        = null;
  
  /** 
    Creates a new instance of XFileChooser. 
   */
  public XFileChooser() {
    if (xos.isRunningOnMacOS()) {
      String chooser 
          = xos.getPref (FILE_CHOOSER_KEY, FILE_CHOOSER_AWT);
      useSwingChooser = (! chooser.equalsIgnoreCase (FILE_CHOOSER_AWT));
    } // end if running on a Mac
    swingChooser = new JFileChooser();
    swingChooser.putClientProperty
        ("JFileChooser.packageIsTraversable", "never");
  } // end constructor
  
  /**
    Sets the file selection mode. If Directories or Files,
    forces use of JFileChooser.
   
    @param mode Uses JFileChooser values for FILES_ONLY, DIRECTORIES_ONLY, 
                or FILES_AND_DIRECTORIES.
   */
  public void setFileSelectionMode (int mode) {
    fileSelectionMode = mode;
    swingChooser.setFileSelectionMode (mode);
    if (fileSelectionMode == DIRECTORIES_ONLY
        || fileSelectionMode == FILES_AND_DIRECTORIES
        ) {
      System.setProperty("apple.awt.fileDialogForDirectories", "true");
    } else {
      System.setProperty("apple.awt.fileDialogForDirectories", "false");
    }
  }
  
  /**
    Sets the starting directory to display to the user.
   
    @param dir The starting directory for the user.
   */
  public void setCurrentDirectory (File dir) {
    currentDirectory = dir;
    swingChooser.setCurrentDirectory (dir);
    selectedFile = null;
  }

  public File getCurrentDirectory () {
    return currentDirectory;
  }

  /**
   Sets the selected file.

   @param target The file to be set.
   */
  public void setSelectedFile (File file) {
    setFile (file);
  }
  
  /**
   Sets the selected file.
   
   @param target The file to be set.
   */
  public void setFile (File file) {
    swingChooser.setSelectedFile (file);
    currentDirectory = swingChooser.getCurrentDirectory();
    selectedFile = file;
  }
  
  /**
    Sets the title of the dialog to be shown to the user.
   
    @param title The dialog title to be used.
   */
  public void setDialogTitle (String title) {
    dialogTitle = title;
    swingChooser.setDialogTitle (title);
  }
  
  /**
    Ask the user to pick a file to open.
   
    @return The File chosen by the user, or null if no successful choice
            was made. 
    @param  parent The parent Frame for the dialog.
   */
  public File showOpenDialog (Frame parent) {
    String fileName;
    selectedFile = null;
    if (useSwingChooser
        || fileSelectionMode == FILES_AND_DIRECTORIES) {
        // System.out.println("Swing chooser");
        //* || fileSelectionMode != FILES_ONLY) {
      
      // Using Swing Component JFileChooser
      int result = swingChooser.showOpenDialog (parent);
      if (result == JFileChooser.APPROVE_OPTION) {
        selectedFile = swingChooser.getSelectedFile();
        currentDirectory = swingChooser.getCurrentDirectory();
      }
    } else {
      // System.out.println("AWT FileDialog");
      // Using AWT Component FileDialog
      if (dialogTitle == null) {
        dialogTitle = "Open Input File";
      }
      awtChooser = new FileDialog (parent, dialogTitle, FileDialog.LOAD);
      if (currentDirectory != null) {
        awtChooser.setDirectory (currentDirectory.toString());
      }
      awtChooser.setVisible(true);
      fileName = awtChooser.getFile();
      if (fileName != null) {
        // System.out.println ("AWT file name = " + fileName);
        selectedFile = new File (awtChooser.getDirectory(), fileName);
        currentDirectory = new File (awtChooser.getDirectory());
      } // end if fileName not null
    } // end if using AWT chooser
    return selectedFile;
  } // end method

  /**
    Ask the user to pick a file to save.
   
    @return The File chosen by the user, or null if no successful choice
            was made. 
    @param  parent The parent Frame for the dialog.
   */
  public File showSaveDialog (Frame parent) {
    String fileName;
    // selectedFile = null;
    if (useSwingChooser) {
        //* || fileSelectionMode != FILES_ONLY) {
      
      // Using Swing Component JFileChooser
      // System.out.println ("Using Swing File Chooser");
      int result = swingChooser.showSaveDialog (parent);
      if (result == JFileChooser.APPROVE_OPTION) {
        selectedFile = swingChooser.getSelectedFile();
        currentDirectory = swingChooser.getCurrentDirectory();
      } else {
        selectedFile = null;
      }
    } else {
      
      // Using AWT Component FileDialog
      if (dialogTitle == null) {
        dialogTitle = "Save Output File";
      }
      if (fileSelectionMode == this.DIRECTORIES_ONLY) {
        awtChooser = new FileDialog (parent, dialogTitle, FileDialog.LOAD);
      } else {
        awtChooser = new FileDialog (parent, dialogTitle, FileDialog.SAVE);
      }
      if (currentDirectory != null) {
        awtChooser.setDirectory (currentDirectory.toString());
      }
      String startingExt = "";
      if (selectedFile != null) {
        awtChooser.setFile (selectedFile.getName());
        startingExt = getExt(selectedFile);
        
      }
      awtChooser.setVisible(true);
      fileName = awtChooser.getFile();
      if (fileName != null) {
        selectedFile = new File (awtChooser.getDirectory(), fileName);
        currentDirectory = new File (awtChooser.getDirectory());
        if (fileSelectionMode != this.DIRECTORIES_ONLY
            && startingExt.length() > 0
            && getExt(selectedFile).equals("")) {
          selectedFile = new File(selectedFile.getPath() + "." + startingExt);
        }
      } else {
        selectedFile = null;
      }
    } // end if using AWT chooser
    return selectedFile;
  } // end method
  
  public String getExt(File file) {
    int periodPosition = file.getName().lastIndexOf(".");
    if (periodPosition > 0) {
      return (file.getName().substring(periodPosition + 1));
    } else {
      return "";
    }
  }
  
  /**
    Returns the file last selected by the user with this chooser.
   
    @return The File chosen by the user, or null if no successful choice
            was made. 
   */
  public File getSelectedFile() {
    return selectedFile;
  }
} // end Class

