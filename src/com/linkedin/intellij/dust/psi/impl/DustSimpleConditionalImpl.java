package com.linkedin.intellij.dust.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.linkedin.intellij.dust.parsing.DustTokenTypes;
import com.linkedin.intellij.dust.psi.DustSimpleConditional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:21 PM
 */
public class DustSimpleConditionalImpl extends DustNonBlockImpl implements DustSimpleConditional {
  public DustSimpleConditionalImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Override
  public String getName() {
    ASTNode elseNode = getElseNode();
    if (elseNode != null) {
      return elseNode.getText();
    }
    return ""; // no name for "{{^}}" expressions
  }

  @Nullable
  @Override
  public Icon getIcon(@IconFlags int flags) {
//    if (getElseNode() != null) {
//      return HbIcons.OPEN_MUSTACHE;
//    }
//    return HbIcons.OPEN_INVERSE;
    return null;
  }

  /**
   * If this element was created from an "{{else}}" expression, it will have an {@link DustTokenTypes#ELSE} child.
   * Otherwise, it was created from "{{^}}"
   *
   * @return the {@link DustTokenTypes#ELSE} element if it exists, null otherwise
   */
  private ASTNode getElseNode() {
    ASTNode[] elseChildren = getNode().getChildren(TokenSet.create(DustTokenTypes.ELSE));
    if (elseChildren != null && elseChildren.length > 0) {
      return elseChildren[0];
    }
    return null;
  }
}
