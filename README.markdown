# Dust Plugin for Intellij

Provides syntax highlighting for the Dust templating language.

![image](http://yifanz.github.com/Intellij-Dust/images/dust_screenshot_15FEB2013.png)

## How to use

Plugin is available through the [Jetbrains plugin repository](http://plugins.jetbrains.com/plugin/?idea&pluginId=7214)

**Install From Repository** *(recommended)*

1. Find and right click to install the plugin - File > Settings > Plugins > Browse repositories
2. Associate plugin with your dust file extension - File > Settings > File Types
3. Customize appearance - File > Settings > Editor > Colors & Fonts > Dust

**Manual Install**

1. Download the [plugin](http://yifanz.github.com/Intellij-Dust/downloads/dust_syntax_b1fb3dbc795c7.jar)
2. Go to File > Settings > Plugins > Install plugin from disk

## Developer Notes

1. Open the project with Intellij. The project is already setup to be an Intellij Plugin Module and should have the build settings configured. The only build dependency is the IDEA SDK. However, you may need to configure the SDK verison and location specific to your system.

2. Install the [JFlex](http://plugins.jetbrains.com/plugin/?id=263) and [Grammar-Kit](http://plugins.jetbrains.com/plugin/?id=6606) plugins

3. Disable the external compiler via Settings > Compiler > Use external build option.

3. You will need the Grammar-Kit to generate the parser source files from Dust.bnf and JFlex to generate the DustLexer from Dust.flex. Since the generated sources are not checked into version control, you need to remember to generate the lexer/parser before compiling.

4. (Optional) Install [PSI Viewer](http://plugins.jetbrains.com/plugin/?id=227) plugin which lets you see the parse tree graphically.

## Release Notes

**Version 0.3.3**

* Fixed bug with using current context and numbers as attribute values

**Version 0.3.2**

* Fixed bug in comment parsing

**Version 0.3.1**

* Fixed compatibility issues with Intellij 11 and set it as the minimum supported version
* Fixed bugs in left curly brace and identifier token patterns in lexer

**Version 0.3**

* Added closing tag auto-completion
* Added goto declaration shortcut "Ctrl+b" on dust partial tag references
* Fix parsing error on self closing block tags
* Fix brace matcher bug when key tags are used in attribute strings
* Remove redundant HTML pattern rules in Dust lexer

**Version 0.2**

* Added Dust brace match highlighting
* Added "Ctrl+/" shortcut for Dust comments
* Fix syntax highlighting for subscript operator in tags (e.g. {#section[0]}...{/section[0]})
* Added TODO highlighting in comments

**Version 0.1.2**

* Fixes syntax highlighting when javascript is present in the template

**Version 0.1.1**

* Provides syntax highlighting for dust templates
* Enabled for all Jetbrains IDEs
