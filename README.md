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
 - browse your launch configurations in separate Eclipse Runner view
 - run or debug launch configuration directly from the view by double clicking on it
 - categorize your launch configurations in custom groups
 - bookmark featured launch configurations with a star icon
 - filter launch configurations using following filters
   - closed projects
   - deleted projects
   - uncategorized launch configurations
   - current project
   - current working set
   - bookmarked launch configurations
 - decide if you want to run or debug
 - open resource behind launch configuration in editor (f.e. Java main class)


History
-------

### 12-09-2014 Version 1.3.2 minor bug fixes
- removed warnings on stdout
- removed "Filter active working set", because it had no effect
- fixed persistence of collapse/expand all

### 18-07-2014 Version 1.3.1 released with lots of bug fixes and usability improvements


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

- **23-03-2014 Version 1.3.0.1** released Support for other launch types

- **22-10-2011 Version 1.3.0** released. See [change log](https://code.google.com/p/eclipserunnerplugin/wiki/Changelog) for more info.

- **27-05-2011 Version 1.2.0** released. [change log](https://code.google.com/p/eclipserunnerplugin/wiki/Changelog) log for more info.

- **16-05-2011** Eclipse runner supports installation via Marketplace in Eclipse >3.5

- **15-05-2011 Version 1.1.0** released. See change log for more info.

- **29-03-2010** First stable version 1.0.0 released. See Installation notes.

- **29-03-2010** New screenshots released here.

- **21-03-2010** we're about to release the first version of Eclipse Runner Plugin. Estimated release date 05-04-2010
