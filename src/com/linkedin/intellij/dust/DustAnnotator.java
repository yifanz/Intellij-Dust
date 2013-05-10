package com.linkedin.intellij.dust;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
//import com.linkedin.intellij.dust.psi.DustCloseTag;
//import com.linkedin.intellij.dust.psi.DustOpenTag;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 3/8/13
 * Time: 10:53 AM
 */
public class DustAnnotator implements Annotator {
  @Override
  public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {/*
    if (element instanceof DustOpenTag) {
      DustOpenTag openTag = (DustOpenTag) element;
      checkMatchingCloseTag(openTag, holder);
    }*/
  }

  private static boolean checkMatchingCloseTag( AnnotationHolder holder) {/*
    if (openTag == null) return false;

    String openTagName = getTagName(openTag);
    PsiElement sibling = openTag.getNextSibling();
    DustCloseTag closeTag = null;
    while (sibling != null) {
      if (sibling instanceof DustCloseTag) {
        closeTag = (DustCloseTag) sibling;
        if (getTagName(closeTag).equals(openTagName)) {
          return true;
        }
      }
      sibling = sibling.getNextSibling();
    }

    holder.createErrorAnnotation(openTag.getTextRange(), "Could not find matching closing tag " + getTagName(openTag));

    if (closeTag != null) {
      holder.createErrorAnnotation(closeTag.getTextRange(), "Could not find matching opening tag " + getTagName(closeTag));
    }*/

    return false;
  }

  private static String getTagName(PsiElement tag) {
    return tag.getChildren()[0].getText();
  }
}