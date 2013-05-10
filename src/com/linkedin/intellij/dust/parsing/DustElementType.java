/*
Based on Intellij-handlebars plugin:
https://github.com/JetBrains/intellij-plugins/tree/master/handlebars
 */
package com.linkedin.intellij.dust.parsing;

import com.linkedin.intellij.dust.DustBundle;
import com.linkedin.intellij.dust.DustLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 2:04 PM
 */
public class DustElementType extends IElementType {
  private final String _parseExpectedMessageKey;

  /**
   * @param parseExpectedMessageKey Key to the {@link DustBundle} message to show the user when the parser
   *                                expected this token, but found something else.
   */
  public DustElementType(@NotNull @NonNls String debugName, @NotNull @NonNls String parseExpectedMessageKey) {
    super(debugName, DustLanguage.INSTANCE);
    _parseExpectedMessageKey = parseExpectedMessageKey;
  }

  @Override
  public String toString() {
    return "[Dust] " + super.toString();
  }

  public String parseExpectedMessage() {
    return DustBundle.message(_parseExpectedMessageKey);
  }
}

