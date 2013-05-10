package com.linkedin.intellij.dust.psi;

import com.intellij.psi.PsiElement;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 10:14 AM
 */

/**
 * Base for all Dust elements
 */
public interface DustPsiElement extends PsiElement {
  String getName();
}

