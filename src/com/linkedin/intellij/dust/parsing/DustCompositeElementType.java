/*
Based on Intellij-handlebars plugin:
https://github.com/JetBrains/intellij-plugins/tree/master/handlebars
 */
package com.linkedin.intellij.dust.parsing;

import com.linkedin.intellij.dust.DustLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 2:11 PM
 */

/**
 * Distinct interface to distinguish the leaf elements we get from the lexer from the synthetic
 * composite elements we create in the parser
 */
public class DustCompositeElementType extends IElementType {
  public DustCompositeElementType(@NotNull @NonNls String debugName) {
    super(debugName, DustLanguage.INSTANCE);
  }
}
