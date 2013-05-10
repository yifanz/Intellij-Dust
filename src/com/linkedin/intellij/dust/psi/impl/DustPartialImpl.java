package com.linkedin.intellij.dust.psi.impl;

import com.intellij.psi.util.PsiTreeUtil;
import com.linkedin.intellij.dust.psi.DustPartial;
import com.intellij.lang.ASTNode;
import com.linkedin.intellij.dust.psi.DustPartialName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:29 PM
 */
public class DustPartialImpl extends DustNonBlockImpl implements DustPartial {
  public DustPartialImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    DustPartialName partialName = PsiTreeUtil.findChildOfType(this, DustPartialName.class);
    return partialName == null ? null : partialName.getName();
  }

  @Nullable
  @Override
  public Icon getIcon(@IconFlags int flags) {
//    return HbIcons.OPEN_PARTIAL;
    return null;
  }
}
