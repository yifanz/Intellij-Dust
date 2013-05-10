package com.linkedin.intellij.dust.parsing;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 4:10 PM
 */
public class DustLexer  extends FlexAdapter {
  public DustLexer() {
    super(new _DustLexer((Reader)null));
  }
}