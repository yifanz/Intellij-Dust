package com.linkedin.intellij.dust.psi;

import com.intellij.psi.tree.IElementType;
import com.linkedin.intellij.dust.DustLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 1:23 PM
 */
public class DustElementType extends IElementType {
  public DustElementType(@NotNull @NonNls String debugName) {
    super(debugName, DustLanguage.INSTANCE);
  }
}
