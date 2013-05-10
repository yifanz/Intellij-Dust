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

  /**
   * Used to determine if an element is part of an "open tag" (i.e. "{{#open}}" or "{{^openInverse}}")
   * <p/>
   * If the given element is the descendant of an {@link DustOpenBlock}, this method returns
   * that parent.
   * <p/>
   * Otherwise, returns null.
   *
   * @param element The element whose ancestors will be searched
   * @return An ancestor of type {@link DustOpenBlock} or null if none exists
   */
  public static DustOpenBlock findParentOpenTagElement(PsiElement element) {
    return (DustOpenBlock)PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return element != null
            && element instanceof DustOpenBlock;
      }
    });
  }

  /**
   * Used to determine if an element is part of a "close tag" (i.e. "{{/closer}}")
   * <p/>
   * If the given element is the descendant of an {@link DustCloseBlock}, this method returns that parent.
   * <p/>
   * Otherwise, returns null.
   * <p/>
   *
   * @param element The element whose ancestors will be searched
   * @return An ancestor of type {@link DustCloseBlock} or null if none exists
   */
  public static DustCloseBlock findParentCloseTagElement(PsiElement element) {
    return (DustCloseBlock)PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return element != null
            && element instanceof DustCloseBlock;
      }
    });
  }

  /**
   * Tests to see if the given element is not the "root" statements expression of the grammar
   */
  public static boolean isNonRootStatementsElement(PsiElement element) {
    PsiElement statementsParent = PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return element != null
            && element instanceof DustStatements;
      }
    });

    // we're a non-root statements if we're of type statements, and we have a statements parent
    return element instanceof DustStatements
        && statementsParent != null;
  }
}

