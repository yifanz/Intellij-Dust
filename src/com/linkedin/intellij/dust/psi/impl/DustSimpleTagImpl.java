package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustPath;
import com.linkedin.intellij.dust.psi.DustPsiElement;
import com.linkedin.intellij.dust.psi.DustSimpleTag;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:16 PM
 */
public class DustSimpleTagImpl extends DustNonBlockImpl implements DustSimpleTag {
  public DustSimpleTagImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    for (PsiElement childElement : getChildren()) {
      // use the name of the first path or data to represent this mustache
      if (childElement instanceof DustPath) {
        return ((DustPsiElement)childElement).getName();
      }
    }

    return null;
  }

  @Nullable
  @Override
  public Icon getIcon(@IconFlags int flags) {/*
    PsiElement openStacheElem = getFirstChild();
    if (openStacheElem == null) {
      return null;
    }

    if (openStacheElem.getNode().getElementType() == HbTokenTypes.OPEN_UNESCAPED) {
      return HbIcons.OPEN_UNESCAPED;
    }

    return HbIcons.OPEN_MUSTACHE;*/
    return null;
  }
}

