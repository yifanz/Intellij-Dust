# Dust Plugin for Intellij

Provides syntax highlighting for the Dust templating language.

![image](http://yifanz.github.com/Intellij-Dust/images/dust_screenshot_15FEB2013.png)

## How to use

WORK IN PROGRESS

Only tested in Intellij 12 Ultimate (must have license)

1. Download the [plugin](http://yifanz.github.com/Intellij-Dust/downloads/dust_syntax_b370981.jar)

2. Go to File > Settings > Plugins > Install plugin from disk

## Developer Notes

1. Open the project with Intellij. The project is already setup to be an Intellij Plugin Module and should have the build settings configured. The only build dependency is the IDEA SDK.

2. Install the [JFlex](http://plugins.jetbrains.com/plugin/?id=263) and [Grammar-Kit](http://plugins.jetbrains.com/plugin/?id=6606) plugins

3. Disable the external compiler via Settings > Compiler > Use external build option.

3. You will need the Grammar-Kit to generate the parser source files from Dust.bnf and JFlex to generate the DustLexer from Dust.flex.

4. (Optional) Install [PSI Viewer](http://plugins.jetbrains.com/plugin/?id=227) plugin which lets you see the parse tree graphically.
