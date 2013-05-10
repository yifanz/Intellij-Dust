package com.linkedin.intellij.dust.psi.impl;

import com.intellij.openapi.util.Iconable;
import com.linkedin.intellij.dust.psi.DustBlockWrapper;
import com.linkedin.intellij.dust.psi.DustOpenBlock;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 10:07 AM
 */
public class DustBlockWrapperImpl extends DustPsiElementImpl implements DustBlockWrapper {
  public DustBlockWrapperImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    DustOpenBlock openBlockMustache = getDustOpenBlock();
    return openBlockMustache == null ? null : openBlockMustache.getName();
  }

  @Nullable
  @Override
  public Icon getIcon(@Iconable.IconFlags int flags) {
    DustOpenBlock openBlockMustache = getDustOpenBlock();
    return openBlockMustache == null ? null : openBlockMustache.getIcon(0);
  }

  private DustOpenBlock getDustOpenBlock() {
    return PsiTreeUtil.findChildOfType(this, DustOpenBlock.class);
  }
}

