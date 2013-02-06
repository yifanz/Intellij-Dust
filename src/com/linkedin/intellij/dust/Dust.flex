package com.linkedin.intellij.dust;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.linkedin.intellij.dust.psi.DustTypes;
import com.intellij.psi.TokenType;
import java.util.Stack;

%%

%class DustLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}
%debug

%{
  private Stack<Integer> lexStateStack = new Stack<java.lang.Integer>();

  private void pushState(int state) {
    lexStateStack.push(yystate());
    yybegin(state);
  }

  private void popState() {
    if (lexStateStack.empty()) {
      yybegin(YYINITIAL);
    } else {
      yybegin(lexStateStack.pop());
    }
  }
%}

CRLF= \n|\r|\r\n
WS=[\ \t\f]

COMMENT_START=\{\!
COMMENT_CONTENT=.
COMMENT_END=\!\}

SECTION=\{\#
EXISTANCE=\{\?
NOT_EXISTANCE=\{\^
HELPER=\{@
PARTIAL=\{>
INLINE_PARTIAL=\{<
BLOCK=\{\+
CLOSE=\{\/
ELSE=\{:

LD=\{
RD=\}
SLASH_RD=\/\}

EQUAL==
PIPE=\|
PERIOD=\.

STRING=\"((\\.)|[^\"])*\"

IDENTIFIER=[a-zA-Z_][a-zA-Z_0-9]*

%state DUST_TAG
%state COMMENT

%state TAG_STARTED
%state CLOSING_TAG_STARTED
%state IN_TAG
%state AFTER_ATTR_NAME
%state ATTR_VALUE
%state ATTR_VALUE_SIMPLE
%state ATTR_VALUE_SINGLE
%state ATTR_VALUE_DOUBLE

%%

{COMMENT_START}                       { pushState(COMMENT); return DustTypes.COMMENT_START; }

<COMMENT> {
  {COMMENT_END}                       { popState(); return DustTypes.COMMENT_END; }
  {COMMENT_CONTENT}                   { return DustTypes.COMMENT_CONTENT; }
  {CRLF}                              { return DustTypes.COMMENT_CONTENT; }
}

/* Rules for HTML */
<YYINITIAL> {
	"<!--" .* "-->"              { return DustTypes.HTML; }
    "</"                         { pushState(CLOSING_TAG_STARTED); return DustTypes.HTML; /* TAG_CLOSING; */ }
    "<" / [!a-zA-Z0-9:]          { pushState(TAG_STARTED); return DustTypes.HTML; /* TAG_START; */ }
    [{}<>] / {WS}  { return DustTypes.HTML; }
    "{}"                         { return DustTypes.HTML; }
    [^{<]+                       { return DustTypes.HTML; }
}

<TAG_STARTED, CLOSING_TAG_STARTED> {
    [-!a-zA-Z0-9:]+               { yybegin(IN_TAG); return DustTypes.HTML; } /* TAG_NAME */
}

<IN_TAG> {
    [-a-zA-Z0-9_]+?             { yybegin(AFTER_ATTR_NAME); return DustTypes.HTML; } /* ATTRIBUTE NAME */
}

<IN_TAG, AFTER_ATTR_NAME> {
    {WS}+                       { return DustTypes.HTML; }
    "/" {WS}* ">"               { popState(); return DustTypes.HTML; }
    ">"                         { popState(); return DustTypes.HTML; /* TAG_CLOSING; */ }
}

<AFTER_ATTR_NAME> {
    "=" / ['\"]                 { yybegin(ATTR_VALUE); return DustTypes.HTML; }
    "="                         { yybegin(ATTR_VALUE_SIMPLE); return DustTypes.HTML; }
}

<ATTR_VALUE> {
    "\""                        { yybegin(ATTR_VALUE_DOUBLE); return DustTypes.HTML; }
    "\'"                        { yybegin(ATTR_VALUE_SINGLE); return DustTypes.HTML; }
}

<ATTR_VALUE_SIMPLE> {
    [^ {}]+                     { return DustTypes.HTML; }
    ">"                         { popState(); return DustTypes.HTML; /*TAG_CLOSING;*/ }
    " "                         { yybegin(IN_TAG); return DustTypes.HTML; /*WHITE_SPACE*/ }
}

<ATTR_VALUE_SINGLE> {
    [^'{}]+                     { return DustTypes.HTML; }
    "'"                         { yybegin(IN_TAG); return DustTypes.HTML; }
}

<ATTR_VALUE_DOUBLE> {
    [^\"{}]+                    { return DustTypes.HTML; }
    "\""                        { yybegin(IN_TAG); return DustTypes.HTML; }
}
/* END Rules for HTML */

{SECTION}                             { pushState(DUST_TAG); return DustTypes.SECTION; }
{EXISTANCE}                           { pushState(DUST_TAG); return DustTypes.EXISTANCE; }
{NOT_EXISTANCE}                       { pushState(DUST_TAG); return DustTypes.NOT_EXISTANCE; }
{HELPER}                              { pushState(DUST_TAG); return DustTypes.HELPER; }
{PARTIAL}                             { pushState(DUST_TAG); return DustTypes.PARTIAL; }
{INLINE_PARTIAL}                      { pushState(DUST_TAG); return DustTypes.INLINE_PARTIAL; }
{BLOCK}                               { pushState(DUST_TAG); return DustTypes.BLOCK; }
{CLOSE}                               { pushState(DUST_TAG); return DustTypes.CLOSE; }
{ELSE}                                { pushState(DUST_TAG); return DustTypes.ELSE; }

{LD}                                  { pushState(DUST_TAG); return DustTypes.LD; }

<DUST_TAG> {
  {STRING}                              { return DustTypes.STRING; }

  {RD}                                  { popState(); return DustTypes.RD; }
  {SLASH_RD}                            { popState(); return DustTypes.SLASH_RD; }

  {EQUAL}                               { return DustTypes.EQUAL; }
  {PIPE}                                { return DustTypes.PIPE; }
  {PERIOD}                              { return DustTypes.PERIOD; }

  {IDENTIFIER}+                         { return DustTypes.IDENTIFIER; }
}

{CRLF}                                { return DustTypes.CRLF; }

{WS}+                                 { return TokenType.WHITE_SPACE; }

.                                     { return TokenType.BAD_CHARACTER; }