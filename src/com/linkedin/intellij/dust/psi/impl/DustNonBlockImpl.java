package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustNonBlock;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:11 PM
 */
public class DustNonBlockImpl extends DustPsiElementImpl implements DustNonBlock {
  DustNonBlockImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }
}

