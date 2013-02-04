package com.linkedin.intellij.dust;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 12:42 PM
 */
public class DustLanguage extends Language {
  public static final DustLanguage INSTANCE = new DustLanguage();

  private DustLanguage() {
    super("Dust");
  }
}
