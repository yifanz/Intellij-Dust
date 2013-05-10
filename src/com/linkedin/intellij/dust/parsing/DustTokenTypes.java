/*
Based on Intellij-handlebars plugin:
https://github.com/JetBrains/intellij-plugins/tree/master/handlebars
 */
package com.linkedin.intellij.dust.parsing;

import com.linkedin.intellij.dust.DustLanguage;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 1:59 PM
 */
public class DustTokenTypes {

  /**
   * private constructor since this is a constants class
   */
  private DustTokenTypes() {
  }

  public static final IElementType BLOCK_WRAPPER = new DustCompositeElementType("BLOCK_WRAPPER");
  // used to delineate blocks in the PSI tree. The formatter requires this extra structure.
  public static final IElementType OPEN_BLOCK_TAG = new DustCompositeElementType("OPEN_BLOCK_TAG");
  public static final IElementType OPEN_CONDITIONAL_BLOCK_TAG = new DustCompositeElementType("OPEN_CONDITIONAL_BLOCK_TAG");
  public static final IElementType CLOSE_BLOCK_TAG = new DustCompositeElementType("CLOSE_BLOCK_TAG");
  public static final IElementType KEY_TAG = new DustCompositeElementType("KEY_TAG");
  public static final IElementType PATH = new DustCompositeElementType("PATH");
  public static final IElementType PARAM = new DustCompositeElementType("PARAM");
  public static final IElementType PARTIAL_TAG = new DustCompositeElementType("PARTIAL_TAG");
  public static final IElementType SIMPLE_CONDITIONAL = new DustCompositeElementType("SIMPLE_CONDITIONAL");
  public static final IElementType STATEMENTS = new DustCompositeElementType("STATEMENTS");

  public static final IElementType CONTENT = new DustElementType("CONTENT", "dust.parsing.element.expected.content");
  public static final IElementType OUTER_ELEMENT_TYPE = new DustElementType("HB_FRAGMENT", "dust.parsing.element.expected.outer_element_type");

  public static final IElementType WHITE_SPACE = new DustElementType("WHITE_SPACE", "dust.parsing.element.expected.white_space");
  public static final IElementType COMMENT = new DustElementType("COMMENT", "dust.parsing.element.expected.comment");
  public static final IElementType UNCLOSED_COMMENT = new DustElementType("UNCLOSED_COMMENT", "");

  public static final IElementType OPEN = new DustElementType("OPEN", "dust.parsing.element.expected.open");
  public static final IElementType OPEN_SECTION = new DustElementType("OPEN_SECTION", "dust.parsing.element.expected.open_block");
  public static final IElementType OPEN_PARTIAL = new DustElementType("OPEN_PARTIAL", "dust.parsing.element.expected.open_partial");
  public static final IElementType OPEN_ENDBLOCK = new DustElementType("OPEN_ENDBLOCK", "dust.parsing.element.expected.open_end_block");
  public static final IElementType OPEN_INVERSE = new DustElementType("OPEN_INVERSE", "dust.parsing.element.expected.open_inverse");
  public static final IElementType OPEN_EXISTS = new DustElementType("OPEN_EXISTS", "dust.parsing.element.expected.open_exists");
  public static final IElementType OPEN_HELPER = new DustElementType("OPEN_HELPER", "dust.parsing.element.expected.open_helper");
  public static final IElementType OPEN_BLOCK = new DustElementType("OPEN_BLOCK", "dust.parsing.element.expected.open_block");
  public static final IElementType OPEN_INLINE_PARTIAL = new DustElementType("OPEN_INLINE_PARTIAL", "dust.parsing.element.expected.open_inline_partial");
  public static final IElementType EQUALS = new DustElementType("EQUALS", "dust.parsing.element.expected.equals");
  public static final IElementType ID = new DustElementType("ID", "dust.parsing.element.expected.id");
  public static final IElementType PARTIAL_NAME = new DustElementType("PARTIAL_NAME", "dust.parsing.element.expected.partial.name");
  public static final IElementType SEP = new DustElementType("SEP", "dust.parsing.element.expected.separator");
  public static final IElementType CLOSE = new DustElementType("CLOSE", "dust.parsing.element.expected.close");
  public static final IElementType ELSE = new DustElementType("ELSE", "");
  public static final IElementType BOOLEAN = new DustElementType("BOOLEAN", "dust.parsing.element.expected.boolean");
  public static final IElementType INTEGER = new DustElementType("INTEGER", "dust.parsing.element.expected.integer");
  public static final IElementType STRING = new DustElementType("STRING", "dust.parsing.element.expected.string");
  public static final IElementType ESCAPE_CHAR = new DustElementType("ESCAPE_CHAR", "");
  public static final IElementType INVALID = new DustElementType("INVALID", "dust.parsing.element.expected.invalid");

  public static final IFileElementType FILE = new IFileElementType("FILE", DustLanguage.INSTANCE);

  public static final TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
  public static final TokenSet COMMENTS = TokenSet.create(COMMENT);
  public static final TokenSet STRING_LITERALS = TokenSet.create(STRING);
}

