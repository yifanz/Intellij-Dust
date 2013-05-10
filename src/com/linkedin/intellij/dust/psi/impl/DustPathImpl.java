package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustPath;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 10:23 AM
 */
public class DustPathImpl extends DustPsiElementImpl implements DustPath {
  public DustPathImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    return getText();
  }
}
