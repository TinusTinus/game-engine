Tinustris
=========

Tinustris is a Tetris clone written by Martijn van de Rijdt.


How to run
----------

Start the game by running tinustris.bat (Windows) or tinustris.sh (Linux / Mac).

Alternately, start it by running the following command:

Windows:
  java -Djava.library.path=<path to game>\natives -cp <path to game>\etc;<path to game>\lib\* nl.mvdr.tinustris.gui.ConfigurationScreen
Linux / Mac / other platforms:
  java -Djava.library.path=<path to game>/natives -cp <path to game>/etc:<path to game>/lib/* nl.mvdr.tinustris.gui.ConfigurationScreen

A Java Runtime Environment, version 1.8.0 or higher, is required. You can get one at http://java.oracle.com.


Troubleshooting
---------------

If the game refuses to start, shows unexpected error messages or has other obvious bugs, you can try the following:

* Make sure you're running the latest version. New releases are available at: https://github.com/TinusTinus/tinustris/releases.
* See if the error message or the log file gives any obvious hints to the problem. The log file is located in the /log subdirectory.
* File a bug at https://github.com/TinusTinus/tinustris/issues. Be sure to add any relevant screenshots and/or log snippets.


More info
---------

See https://github.com/TinusTinus/tinustris for more info.