# Dust Plugin for Intellij

Provides syntax highlighting for the Dust templating language.

![image](http://yifanz.github.com/Intellij-Dust/images/dust_screenshot_15FEB2013.png)

## How to use

Plugin is available through the [Jetbrains plugin repository](http://plugins.jetbrains.com/plugin/?idea&pluginId=7214)

**Version 0.1.1**

* Provides syntax highlighting for dust templates
* Enabled for all Jetbrains IDEs

**Install From Repository** *(recommended)*

1. Find and right click to install the plugin - File > Settings > Plugins > Browse repositories
2. Associate plugin with your dust file extension - File > Settings > File Types
3. Customize appearance - File > Settings > Editor > Colors & Fonts > Dust

**Manual Install**

1. Download the [plugin](http://yifanz.github.com/Intellij-Dust/downloads/dust_syntax_3e47663bf1e.jar)
2. Go to File > Settings > Plugins > Install plugin from disk

## Developer Notes

1. Open the project with Intellij. The project is already setup to be an Intellij Plugin Module and should have the build settings configured. The only build dependency is the IDEA SDK. However, you may need to configure the SDK verison and location specific to your system.

2. Install the [JFlex](http://plugins.jetbrains.com/plugin/?id=263) and [Grammar-Kit](http://plugins.jetbrains.com/plugin/?id=6606) plugins

3. Disable the external compiler via Settings > Compiler > Use external build option.

3. You will need the Grammar-Kit to generate the parser source files from Dust.bnf and JFlex to generate the DustLexer from Dust.flex. Since the generated sources are not checked into version control, you need to remember to generate the lexer/parser before compiling.

4. (Optional) Install [PSI Viewer](http://plugins.jetbrains.com/plugin/?id=227) plugin which lets you see the parse tree graphically.
