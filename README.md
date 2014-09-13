Eclipse Runner Plugin
===================

**Note**: this is a fork of [Eclipse Runner Plugin](https://code.google.com/p/eclipserunnerplugin/) with a few bug fixes and enhancements.

Overview
--------

Eclipse Runner plugin extends capability of running launch configurations in Eclipse IDE. 
User gets a new "Runner" view, which allows to categorize and bookmark launch configurations 
in groups, run them directly from the view by double clicking on the launch configuration icon. 
It speeds up development in projects containing many small java applications or many test suits.


Features
---------
Features
---------
 - browse your launch configurations in separate Eclipse Runner view
 - keep your launches organized   
 - run or debug launch configuration directly from the view by double clicking on it
   - remembers last launch mode (run, debug, profile...)
   - less clicks if you have to run more than one launc configuration   
 - categorize your launch configurations in custom groups
 - bookmark featured launch configurations with a star icon
 - filter launch configurations using following filters
   - closed projects
   - deleted projects
   - uncategorized launch configurations
   - current project
   - bookmarked launch configurations
 - open resource behind launch configuration in editor (f.e. Java main class)

For more information see [Help Files](EclipseRunner/help/Eclipse Runner.md).

History
-------

### 2014-09-13 Version 1.3.2 minor bug fixes

- help added
- removed warnings on stdout
- removed "Filter active working set", because it had no effect
- fixed persistence of collapse/expand all

### 2014-07-18 Version 1.3.1 bug fixes and usability improvements

- dynamically use the launch image form the launch configuration
- made node expansions persistent 
- do not close all nodes when a launch configurations change
- added a drop-down for the launching in the view tool bar
- check mark the default launch configuration in context menu (the one called on double click)
- show only supported launch configurations in context menu
- remember the last launch type per configuration
- removed dependencies to JDT
- fixed [resource leak](https://code.google.com/p/eclipserunnerplugin/issues/detail?id=12)


### Older releases

- **2014-03-23** Version **1.3.0.1** released Support for other launch types

- **2011-10-22** Version **1.3.0** released. See [change log](https://code.google.com/p/eclipserunnerplugin/wiki/Changelog) for more info.

- **2011-05-27** Version **1.2.0** released. See [change log](https://code.google.com/p/eclipserunnerplugin/wiki/Changelog) for more info.

- **2011-05-16** Eclipse runner supports installation via Marketplace in Eclipse >3.5

- **2011-05-15** Version **1.1.0** released. See change log for more info.

- **2010-03-29** Version **1.0.0** released.

- **2010-03-21** Initial Version
