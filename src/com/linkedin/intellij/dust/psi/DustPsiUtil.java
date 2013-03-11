package com.linkedin.intellij.dust.psi;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 3/11/13
 * Time: 1:01 PM
 */
public class DustPsiUtil {
  public static boolean isNonRootStatementsElement(PsiElement element) {
    PsiElement statementsParent = PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return element != null
            && element.getNode() != null
            && element.getNode().getElementType() == DustTypes.STATEMENTS;
      }
    });

    // we're a non-root statements if we're of type statements, and we have a statements parent
    return element.getNode().getElementType() == DustTypes.STATEMENTS
        && statementsParent != null;
  }
}
