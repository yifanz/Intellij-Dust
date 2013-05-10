package com.linkedin.intellij.dust.psi.impl;

import com.intellij.lang.ASTNode;
import com.linkedin.intellij.dust.psi.DustComment;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:36 PM
 */
public class DustCommentImpl extends DustPsiElementImpl implements DustComment {
  public DustCommentImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }
}
