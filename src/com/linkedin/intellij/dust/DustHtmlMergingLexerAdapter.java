package com.linkedin.intellij.dust;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.linkedin.intellij.dust.psi.DustTypes;

import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 2/5/13
 * Time: 3:01 PM
 */
public class DustHtmlMergingLexerAdapter extends MergingLexerAdapter {
  public static final TokenSet TOKENS_TO_MERGE = TokenSet.create(
      DustTypes.HTML,
      TokenType.WHITE_SPACE
  );

  public DustHtmlMergingLexerAdapter() {
    super(new LookAheadLexer(new FlexAdapter(new DustLexer((Reader) null))) {

      @Override
      protected void lookAhead(Lexer lex) {
        IElementType type = lex.getTokenType();

        // TODO this does nothing right now, maybe useful later when we want to label different types of HTML tokens
        /*
        if(type != null && type.equals(DustTypes.HTML)) {
          addToken(DustTypes.HTML);
          lex.advance();
          type = lex.getTokenType();

          while (type != null && type.equals(DustTypes.HTML)) {
            addToken(DustTypes.HTML);
            lex.advance();
            type = lex.getTokenType();
          }
        } else {
          addToken(type);
          lex.advance();
        }*/
        addToken(type);
        lex.advance();
      }
    }, TOKENS_TO_MERGE); // TODO the HTML tokens don't get merged, highlighting still works though
  }
}
