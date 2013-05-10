package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustCloseBlock;
import com.linkedin.intellij.dust.psi.DustOpenBlock;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:05 PM
 */
public class DustCloseBlockImpl extends DustBlockImpl implements DustCloseBlock {
  public DustCloseBlockImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public DustOpenBlock getPairedElement() {
    PsiElement openBlockElement = getParent().getFirstChild();
    if (openBlockElement instanceof DustOpenBlock) {
      return (DustOpenBlock)openBlockElement;
    }

    return null;
  }
}

