package com.linkedin.intellij.dust.psi.impl;

import com.intellij.lang.ASTNode;
import com.linkedin.intellij.dust.psi.DustParam;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:27 PM
 */
public class DustParamImpl extends DustPsiElementImpl implements DustParam {
  public DustParamImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }
}
