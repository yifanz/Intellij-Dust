package com.linkedin.intellij.dust;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.linkedin.intellij.dust.psi.DustTypes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 3:13 PM
 */
public class DustSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey COMMENT = createTextAttributesKey("DUST_COMMENT", SyntaxHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey TAG = createTextAttributesKey("DUST_TAG", SyntaxHighlighterColors.KEYWORD);
  public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("DUST_IDENTIFIER", SyntaxHighlighterColors.KEYWORD);
  public static final TextAttributesKey STRING = createTextAttributesKey("DUST_STRING", SyntaxHighlighterColors.STRING);

  static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("DUST_BAD_CHARACTER",
      new TextAttributes(Color.RED, null, null, null, Font.BOLD));

  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
  private static final TextAttributesKey[] TAG_KEYS = new TextAttributesKey[]{TAG};
  private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new DustHtmlMergingLexerAdapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (isPartOfComment(tokenType)) {
      return COMMENT_KEYS;
    } else if (tokenType.equals(DustTypes.IDENTIFIER)) {
      return IDENTIFIER_KEYS;
    } else if (isPartOfTag(tokenType)) {
      return TAG_KEYS;
    } else if (tokenType.equals(DustTypes.STRING)) {
      return STRING_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }

  private static boolean isPartOfTag(IElementType tokenType) {
    return tokenType.equals(DustTypes.LD)
        || tokenType.equals(DustTypes.RD)
        || tokenType.equals(DustTypes.SLASH_RD)
        || tokenType.equals(DustTypes.SECTION)
        || tokenType.equals(DustTypes.EXISTANCE)
        || tokenType.equals(DustTypes.NOT_EXISTANCE)
        || tokenType.equals(DustTypes.HELPER)
        || tokenType.equals(DustTypes.PARTIAL)
        || tokenType.equals(DustTypes.INLINE_PARTIAL)
        || tokenType.equals(DustTypes.BLOCK)
        || tokenType.equals(DustTypes.CLOSE)
        || tokenType.equals(DustTypes.ELSE)
        || tokenType.equals(DustTypes.PERIOD)
        || tokenType.equals(DustTypes.PIPE);
  }

  private static boolean isPartOfComment(IElementType tokenType) {
    return tokenType.equals(DustTypes.COMMENT_START)
        || tokenType.equals(DustTypes.COMMENT_END)
        || tokenType.equals(DustTypes.COMMENT_CONTENT);
  }
}
