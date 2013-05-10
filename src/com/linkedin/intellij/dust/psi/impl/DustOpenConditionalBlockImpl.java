package com.linkedin.intellij.dust.psi.impl;

import com.linkedin.intellij.dust.DustIcons;
import com.linkedin.intellij.dust.psi.DustOpenConditionalBlock;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:08 PM
 */
public class DustOpenConditionalBlockImpl extends DustOpenBlockImpl implements DustOpenConditionalBlock {
  public DustOpenConditionalBlockImpl(@NotNull ASTNode astNode) {
    super(astNode);
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    return DustIcons.FILE;
  }
}

