package com.linkedin.intellij.dust;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.linkedin.intellij.dust.psi.DustCloseTag;
import com.linkedin.intellij.dust.psi.DustOpenTag;
import com.linkedin.intellij.dust.psi.DustTag;
import com.linkedin.intellij.dust.psi.DustTokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 3/8/13
 * Time: 10:53 AM
 */
public class DustAnnotator implements Annotator {
  @Override
  public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
    if (element instanceof DustOpenTag) {
      DustOpenTag openTag = (DustOpenTag) element;
      if (!hasMatchingCloseTag(openTag)) {
        holder.createErrorAnnotation(element.getTextRange(), "Could not find matching closing tag " + getTagName(openTag));
      }
    }
  }

  private static boolean hasMatchingCloseTag(DustOpenTag openTag) {
    if (openTag == null) return false;

    String openTagName = getTagName(openTag);
    PsiElement sibling = openTag.getNextSibling();
    while (sibling != null) {
      if (sibling instanceof DustCloseTag) {
        DustCloseTag closeTag = (DustCloseTag) sibling;
        if (getTagName(closeTag).equals(openTagName)) {
          return true;
        }
      }
      sibling = sibling.getNextSibling();
    }

    return false;
  }

  private static String getTagName(PsiElement tag) {
    return tag.getChildren()[0].getText();
  }
}