package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustBlock;
import com.linkedin.intellij.dust.psi.DustPath;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 10:15 AM
 */
abstract class DustBlockImpl extends DustPsiElementImpl implements DustBlock {
  protected DustBlockImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  @Nullable
  public DustPath getBlockMainPath() {
    return PsiTreeUtil.findChildOfType(this, DustPath.class);
  }

  @Override
  @Nullable
  public String getName() {
    DustPath mainPath = getBlockMainPath();
    return mainPath == null ? null : mainPath.getName();
  }
}

