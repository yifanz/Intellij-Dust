package com.linkedin.intellij.dust;

import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import com.linkedin.intellij.dust.parsing.*;
import com.linkedin.intellij.dust.parsing.DustLexer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 3:13 PM
 */
public class DustSyntaxHighlighter extends SyntaxHighlighterBase {
  private static final Map<IElementType, TextAttributesKey> keys1;
  private static final Map<IElementType, TextAttributesKey> keys2;

  @NotNull
  public Lexer getHighlightingLexer() {
    return new DustLexer();
  }

  private static final TextAttributesKey TAG = TextAttributesKey.createTextAttributesKey(
      "DUST.TAG",
      new TextAttributes(null, null, null, null, 1)
  );

  private static final TextAttributesKey IDENTIFIERS = TextAttributesKey.createTextAttributesKey(
      "DUST.IDENTIFIERS",
      SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
  );

  private static final TextAttributesKey COMMENTS = TextAttributesKey.createTextAttributesKey(
      "DUST.COMMENTS",
      SyntaxHighlighterColors.DOC_COMMENT.getDefaultAttributes()
  );

  private static final TextAttributesKey OPERATORS = TextAttributesKey.createTextAttributesKey(
      "DUST.OPERATORS",
      SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
  );

  private static final TextAttributesKey VALUES = TextAttributesKey.createTextAttributesKey(
      "DUST.VALUES",
      SyntaxHighlighterColors.NUMBER.getDefaultAttributes()
  );

  private static final TextAttributesKey STRINGS = TextAttributesKey.createTextAttributesKey(
      "DUST.STRINGS",
      SyntaxHighlighterColors.STRING.getDefaultAttributes()
  );

  @SuppressWarnings("UseJBColor") // TODO port to JBColor when we stop supporting IDEA 11
  private static final TextAttributesKey ESCAPE = TextAttributesKey.createTextAttributesKey(
      "DUST.ESCAPE",
      new TextAttributes(Color.BLUE, null, null, null, 0)
  );

  static {
    keys1 = new HashMap<IElementType, TextAttributesKey>();
    keys2 = new HashMap<IElementType, TextAttributesKey>();

    keys1.put(DustTokenTypes.OPEN, TAG);
    keys1.put(DustTokenTypes.OPEN_SECTION, TAG);
    keys1.put(DustTokenTypes.OPEN_PARTIAL, TAG);
    keys1.put(DustTokenTypes.OPEN_ENDBLOCK, TAG);
    keys1.put(DustTokenTypes.OPEN_INVERSE, TAG);
    keys1.put(DustTokenTypes.OPEN_EXISTS, TAG);
    keys1.put(DustTokenTypes.OPEN_BLOCK, TAG);
    keys1.put(DustTokenTypes.OPEN_INLINE_PARTIAL, TAG);
    keys1.put(DustTokenTypes.OPEN_HELPER, TAG);
    keys1.put(DustTokenTypes.CLOSE, TAG);
    keys1.put(DustTokenTypes.ID, IDENTIFIERS);
    keys1.put(DustTokenTypes.PARTIAL_NAME, IDENTIFIERS);
    keys1.put(DustTokenTypes.COMMENT, COMMENTS);
    keys1.put(DustTokenTypes.UNCLOSED_COMMENT, COMMENTS);
    keys1.put(DustTokenTypes.EQUALS, OPERATORS);
    keys1.put(DustTokenTypes.SEP, OPERATORS);
    keys1.put(DustTokenTypes.INTEGER, VALUES);
    keys1.put(DustTokenTypes.ELSE, IDENTIFIERS);
    keys1.put(DustTokenTypes.BOOLEAN, VALUES);
    keys1.put(DustTokenTypes.STRING, STRINGS);
    keys1.put(DustTokenTypes.ESCAPE_CHAR, ESCAPE);
  }

  @NotNull
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(keys1.get(tokenType), keys2.get(tokenType));
  }

  public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES
      = new LinkedHashMap<TextAttributesKey, Pair<String, HighlightSeverity>>();

  static {
    DISPLAY_NAMES.put(TAG, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.tag_braces.key"), null));
    DISPLAY_NAMES.put(IDENTIFIERS, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.identifiers.key"), null));
    DISPLAY_NAMES.put(COMMENTS, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.comments.key"), null));
    DISPLAY_NAMES.put(OPERATORS, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.operators.key"), null));
    DISPLAY_NAMES.put(VALUES, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.values.key"), null));
    DISPLAY_NAMES.put(STRINGS, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.strings.key"), null));
    DISPLAY_NAMES.put(ESCAPE, new Pair<String, HighlightSeverity>(DustBundle.message("dust.page.colors.descriptor.escape.key"), null));
  }
}

