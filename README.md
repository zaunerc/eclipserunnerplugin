Eclipse Runner Plugin [![Build Status](https://travis-ci.org/zaunerc/eclipserunnerplugin.svg?branch=master)](https://travis-ci.org/zaunerc/eclipserunnerplugin)
===================

Install
-------

To install, use [this update site](https://dl.bintray.com/zaunerc/p2/eclipserunner/latest/).
On the Eclipse marketplace this plugin can be found [here](https://marketplace.eclipse.org/content/eclipse-runner).

Overview
--------

Eclipse Runner plugin extends capability of running launch configurations in Eclipse IDE. 
User gets a new "Runner" view, which allows to categorize and bookmark launch configurations 
in groups, run them directly from the view by double clicking on the launch configuration icon. 
It speeds up development in projects containing many small java applications or many test suits.

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

Release
--------

1. Change working directory to parent project: `$ cd com.eclipserunner.parent`

1. Build sources and run tests (using tycho-surefire-plugin): `$ mvn clean verify`. **Only if everything succeeds proceed with the following steps.** Otherwise fix the build errors and/or unit tests first instead. 

1. Set new version numer: `$ mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion="1.3.3"`

1. Update version number of the target artifact to `1.3.3` in `com.eclipserunner.parent/pom.xml`.

1. Create a new commit: `$ git commit -a -m "Creating release v1.3.3."`

1. Tag the commit: `$ git tag v1.3.3`

1. Build project: `$ mvn clean install`

1. The P2 update will be located under `$ com.eclipserunner.update_site/target/repository`. Upload the contents of this directory to e.g. `https://bintray.com/zaunerc/p2/com.eclipserunner.p2_site/latest`.

1. Start the next development iteration: `$ mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion="1.3.4.qualifier"`

1. Update version number of the target artifact to `1.3.4-SNAPSHOT` in `com.eclipserunner.parent/pom.xml`.

1. Create a new commit: `$ git commit -a -m "Creating v1.3.4 snapshot build."`

1. Push the tag to origin: `$ git push origin v1.3.3`

1. Finally merge / push any pending commits to origin/master on GitHub.

History
-------

### 2016-06-23 Version 1.3.4 build system improvements

- Fix automatic builds on [Travis CI](https://travis-ci.org/zaunerc/eclipserunnerplugin).

### 2016-06-21 Version 1.3.3 build system improvements

- Introduce Maven Tycho build system: Headless builds are now supported. See e.g. [Travis CI](https://travis-ci.org/zaunerc/eclipserunnerplugin).
- Move unit tests to their own fragment: Unit tests are now run automatically as part of the build process using the Tycho Surefire Plugin.
- P2 update site is now automatically generated: See `com.eclipserunner.update_site` project.

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
