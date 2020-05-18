## Development Environment

**Repository/Project Rules**
1. Check the files you are staging to add. The .svnignore file should stop any bad files from ending up in the remote repository, but check first.
2. Commit messages should start with some variation of "[JIRA-TASK-ID]" if they are applicable to a Jira task.
3. Don't push broken code to the master branch.

**Needed to develop:**
- Project SDK: Java 11 JDK
	- Recommended: openjdk 11.0.6
- IDE: IntelliJ IDEA
	- Recommended: 2019/2020 Ultimate (free for students)
- JavaFX UI: Gluon Scene Builder
- VCS: SVN
	- Recommended: TortoiseSVN
	
**To setup:**
1. In IntelliJ choose open project
2. Navigate to the local repository and double click on the "source_files" folder. This is the root directory for IntelliJ's purposes.

**To run:**
1. On sidebar choose Maven (or View -> Tool Windows -> Maven) 
2. Under [Plugins -> javafx] double click javafx:run

**To create easy run configuration:**
1. Goto [Run -> Edit Configurations]
2. Choose Maven template for run configuration
3. Choose "Create onfiguration" at top right
4. Under command line options put "clean javafx:run"
5. Click apply. You can now run this project like normal
	
**To build the jar:**
1. On sidebar choose Maven (or View -> Tool Windows -> Maven)
2. Under [Lifecycle] double click package
3. Executable jar saved in "/sources_files/shade" folder of local directory

**To add filetypes/folders that should not be allowed in remote repository:**
1. Add the filetype/folder to the ".svnignore" file in the "source_files" folder of local directory. Use a guide like https://labs.consol.de/development/git/2017/02/22/gitignore.html
2. In command prompt cd to the "source_files" directory and run the following command: `svn propset svn:ignore -F .svnignore .`
