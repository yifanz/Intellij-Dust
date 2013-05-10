package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustPsiElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 10:16 AM
 */
public class DustPsiElementImpl extends ASTWrapperPsiElement implements DustPsiElement {
  public DustPsiElementImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public ItemPresentation getPresentation() {
    return ItemPresentationProviders.getItemPresentation(this);
  }
}

