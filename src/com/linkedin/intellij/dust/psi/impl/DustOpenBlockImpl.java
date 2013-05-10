package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.DustIcons;
import com.linkedin.intellij.dust.psi.DustCloseBlock;
import com.linkedin.intellij.dust.psi.DustOpenBlock;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:02 PM
 */
public class DustOpenBlockImpl extends DustBlockImpl implements DustOpenBlock {
  public DustOpenBlockImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public DustCloseBlock getPairedElement() {
    PsiElement closeBlockElement = getParent().getLastChild();
    if (closeBlockElement instanceof DustCloseBlock) {
      return (DustCloseBlock)closeBlockElement;
    }

    return null;
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    return DustIcons.FILE;
  }
}

