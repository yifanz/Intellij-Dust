package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.psi.DustStatements;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:25 PM
 */
public class DustStatementsImpl extends DustPsiElementImpl implements DustStatements {
  public DustStatementsImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }
}
