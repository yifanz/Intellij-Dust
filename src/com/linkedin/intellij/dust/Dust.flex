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
COMMENT_CONTENT=(\\.)|[^\!\{\}]
COMMENT_END=\!\}

SECTION=\{\#
EXISTANCE=\{\?
NOT_EXISTANCE=\{\^
HELPER=\{@
PARTIAL=\{>
INLINE_PARTIAL=\{<
BLOCK=\{\+
CLOSE=\{\/
ELSE=\{:else\}

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

%%

{COMMENT_START}                       { pushState(COMMENT); return DustTypes.COMMENT_START; }

<COMMENT> {
  {COMMENT_END}                       { popState(); return DustTypes.COMMENT_END; }
  {COMMENT_CONTENT}+                  { return DustTypes.COMMENT_CONTENT; }
}

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

.                                     { return DustTypes.HTML; }