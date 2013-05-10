package com.linkedin.intellij.dust.psi.impl;

import com.intellij.lang.ASTNode;
import com.linkedin.intellij.dust.psi.DustPartialName;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:32 PM
 */
public class DustPartialNameImpl extends DustPsiElementImpl implements DustPartialName {
  public DustPartialNameImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    return getText();
  }
}
