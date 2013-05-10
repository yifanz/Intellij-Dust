/* Based on the Intellij-handlebars lexer:
  https://github.com/JetBrains/intellij-plugins/blob/master/handlebars/src/com/dmarcotte/handlebars/parsing/handlebars.flex
*/

package com.linkedin.intellij.dust.parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;
import com.linkedin.intellij.dust.parsing.DustTokenTypes;

// suppress various warnings/inspections for the generated class
@SuppressWarnings ({"FieldCanBeLocal", "UnusedDeclaration", "UnusedAssignment", "AccessStaticViaInstance", "MismatchedReadAndWriteOfArray", "WeakerAccess", "SameParameterValue", "CanBeFinal", "SameReturnValue", "RedundantThrows", "ConstantConditions"})
%%

%class _DustLexer
%implements FlexLexer
%final
%unicode
%function advance
%type IElementType
%eof{ return;
%eof}

%{
//private static final String[] checks = {"\"","(",")","=","\\s", ";"};

    private Stack<Integer> stack = new Stack<Integer>();

    public void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    public void yypopState() {
      yybegin(stack.pop());
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]


%state dust
%state escaped_dust
%state par
%state comment

%%

<YYINITIAL> {

  // jflex doesn't support lookaheads with potentially empty prefixes, so we can't directly port the Initial
  // state from handlebars.l, so we accomplish the same thing in a more roundabout way:

  // simulate the lookahead by matching with anything that ends in "{{", and then backtracking away from
  // any trailing "{" characters we've picked up
  ~"{" {
    // backtrack over any stache characters at the end of this string
    while (yylength() > 0 && yytext().subSequence(yylength() - 1, yylength()).toString().equals("{")) {
      yypushback(1);
    }

    if (yylength() > 0 && yytext().toString().substring(yylength() - 1, yylength()).equals("\\")) {
      yypushback(1); // put the escape char back
      yypushState(escaped_dust);
    } else {
      yypushState(dust);
    }

    // we stray from the Handlebars grammar a bit here since we need our WHITE_SPACE more clearly delineated
    //    and we need to avoid creating extra tokens for empty strings (makes the parser and formatter happier)
    if (!yytext().toString().equals("")) {
        if (yytext().toString().trim().length() == 0) {
            return DustTokenTypes.WHITE_SPACE;
        } else {
            return DustTokenTypes.CONTENT;
        }
    }
  }

  // Check for anything that is not a string containing "{{"; that's CONTENT
  !([^]*"{"[^]*)                         { return DustTokenTypes.CONTENT; }
}

<escaped_dust> {
    "\\" { return DustTokenTypes.ESCAPE_CHAR; }
    "{"~"{" { // grab everything up to the next open stache
          // backtrack over any stache characters at the end of this string
          while (yylength() > 0 && yytext().subSequence(yylength() - 1, yylength()).toString().equals("{")) {
            yypushback(1);
          }

          if (yylength() > 0 && yytext().toString().substring(yylength() - 1, yylength()).equals("\\")) {
            // the next mustache is escaped, push back the escape char so that we can lex it as such
            yypushback(1);
          } else {
            // the next mustache is not escaped, we're done in this state
            yypopState();
          }

          return DustTokenTypes.CONTENT;
    }
    "{"!([^]*"{"[^]*) { // otherwise, if the remaining text just contains the one escaped mustache, then it's all CONTENT
        return DustTokenTypes.CONTENT;
    }
}

<dust> {

  "{>" { yypushState(par); return DustTokenTypes.OPEN_PARTIAL; }
  "{#" { return DustTokenTypes.OPEN_SECTION; }
  "{/" { return DustTokenTypes.OPEN_ENDBLOCK; }
  "{^" { return DustTokenTypes.OPEN_INVERSE; }
  "{?" { return DustTokenTypes.OPEN_EXISTS; }
  "{@" { return DustTokenTypes.OPEN_HELPER; }
  "{+" { return DustTokenTypes.OPEN_BLOCK; }
  "{<" { return DustTokenTypes.OPEN_INLINE_PARTIAL; }
  // NOTE: a standard Handlebars lexer would check for "{{else" here.  We instead want to lex it as two tokens to highlight the "{{" and the "else" differently.  See where we make an HbTokens.ELSE below.
  "{!" { yypushback(3); yypopState(); yypushState(comment); }
  "{" { return DustTokenTypes.OPEN; }
  "=" { return DustTokenTypes.EQUALS; }
  "."/["}"\t \n\x0B\f\r] { return DustTokenTypes.ID; }
  ".." { return DustTokenTypes.ID; }
  [\/.] { return DustTokenTypes.SEP; }
  [\t \n\x0B\f\r]* { return DustTokenTypes.WHITE_SPACE; }
  "}" { yypopState(); return DustTokenTypes.CLOSE; }
  "/}" { yypopState(); return DustTokenTypes.CLOSE; }
  \"([^\"\\]|\\.)*\" { return DustTokenTypes.STRING; }
  '([^'\\]|\\.)*' { return DustTokenTypes.STRING; }
  ":else"/["}"\t \n\x0B\f\r] { return DustTokenTypes.ELSE; } // create a custom token for "else" so that we can highlight it independently of the "{{" but still parse it as an inverse operator
  "true"/["}"\t \n\x0B\f\r] { return DustTokenTypes.BOOLEAN; }
  "false"/["}"\t \n\x0B\f\r] { return DustTokenTypes.BOOLEAN; }
  \-?[0-9]+/[}\t \n\x0B\f\r]  { return DustTokenTypes.INTEGER; }
  [a-zA-Z0-9_$-]+/[=}\t \n\x0B\f\r\/.] { return DustTokenTypes.ID; }
  // TODO handlesbars.l extracts the id from within the square brackets.  Fix it to match handlebars.l?
  "["[^\]]*"]" { return DustTokenTypes.ID; }
}

<par> {
    [a-zA-Z0-9_$-/]+ { yypopState(); return DustTokenTypes.PARTIAL_NAME; }
}

<comment> {
  "{!"~"!}" { yypopState(); return DustTokenTypes.COMMENT; }
  // lex unclosed comments so that we can give better errors
  "{!"!([^]*"!}"[^]*) { yypopState(); return DustTokenTypes.UNCLOSED_COMMENT; }
}

{WhiteSpace}+ { return DustTokenTypes.WHITE_SPACE; }
. { return DustTokenTypes.INVALID; }