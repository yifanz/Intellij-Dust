package com.linkedin.intellij.dust;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.linkedin.intellij.dust.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 6/4/13
 * Time: 1:53 PM
 *
 * Based on the intellij mustache plugin
 */
public class DustTypedHandler extends TypedHandlerDelegate{
//  @Override
//  public Result beforeCharTyped(char c, Project project, Editor editor, PsiFile file, FileType fileType) {
//    int offset = editor.getCaretModel().getOffset();
//
//    if (offset == 0 || offset > editor.getDocument().getTextLength()) {
//      return Result.CONTINUE;
//    }
//
//    String previousChar = editor.getDocument().getText(new TextRange(offset - 1, offset));
//
//    if (file.getViewProvider() instanceof DustFileViewProvider) {
//      PsiDocumentManager.getInstance(project).commitAllDocuments();
//
//      // we suppress the built-in "}" auto-complete when we see "{{"
//      if (c == '{' && previousChar.equals("{")) {
//        // since the "}" autocomplete is built in to IDEA, we need to hack around it a bit by
//        // intercepting it before it is inserted, doing the work of inserting for the user
//        // by inserting the '{' the user just typed...
//        editor.getDocument().insertString(offset, Character.toString(c));
//        // ... and position their caret after it as they'd expect...
//        editor.getCaretModel().moveToOffset(offset + 1);
//
//        // ... then finally telling subsequent responses to this charTyped to do nothing
//        return Result.STOP;
//      }
//    }
//
//    return Result.CONTINUE;
//  }

  @Override
  public Result charTyped(char c, Project project, Editor editor, @NotNull PsiFile file) {
    int offset = editor.getCaretModel().getOffset();
    FileViewProvider provider = file.getViewProvider();

    if (offset < 2 || offset > editor.getDocument().getTextLength()) {
      return Result.CONTINUE;
    }

    String previousChar = editor.getDocument().getText(new TextRange(offset - 2, offset - 1));

    if (file.getViewProvider() instanceof DustFileViewProvider) {
      // if we're looking at a closing brace, then auto complete the closing tag
      if (c == '}' && !previousChar.equals("\\") && !previousChar.equals("/")) {
        autoInsertCloseTag(project, offset, editor, provider);
        adjustFormatting(project, offset, editor, file, provider);
      }
    }

    return Result.CONTINUE;
  }

  /**
   * When appropriate, auto-inserts closing tags.  i.e.  When "{#tagName}" or "{^tagName} is typed,
   *      {/tagName} is automatically inserted
   */
  private void autoInsertCloseTag(Project project, int offset, Editor editor, FileViewProvider provider) {
    PsiDocumentManager.getInstance(project).commitAllDocuments();

    PsiElement elementAtCaret = provider.findElementAt(offset - 1, DustLanguage.class);

    PsiElement openTag = DustPsiUtil.findParentOpenTagElement(elementAtCaret);

    String tagName = getTagName(openTag);

    if (!tagName.trim().equals("")) {
      boolean addCloseTag = true;
      PsiElement sibling = openTag.getNextSibling();
      DustCloseTag closeTag;
      while (sibling != null && addCloseTag) {
        if (sibling instanceof DustCloseTag) {
          closeTag = (DustCloseTag) sibling;
          if (getTagName(closeTag).equals(tagName)) {
            addCloseTag = false;
          }
        }
        sibling = sibling.getNextSibling();
      }

      if (addCloseTag) {
        // insert the corresponding close tag
        editor.getDocument().insertString(offset, "{/" + tagName + "}");
      }
    }
  }

  private String getTagName(PsiElement tag) {
    String tagName = "";

    if (tag != null && tag.getChildren().length > 1) {
      PsiElement[] children = tag.getChildren();

      if (children.length > 0) {
        PsiElement tagNameEl = children[0];

        if (tagNameEl != null
            && tagNameEl.getNode().getElementType() == DustTypes.TAG_NAME) {
          tagName += tagNameEl.getText();

          if (children.length > 1) {
            PsiElement indexDeref = children[1];

            if (indexDeref != null
                && indexDeref.getNode().getElementType() == DustTypes.INDEX_DEREF) {
              tagName += indexDeref.getText();
            }
          }
        }
      }
    }

    return tagName;
  }

  /**
   * When appropriate, automatically reduce the indentation for else tags "{:else}"
   */
  private void adjustFormatting(Project project, int offset, Editor editor, PsiFile file, FileViewProvider provider) {
    PsiElement elementAtCaret = provider.findElementAt(offset - 1, DustLanguage.class);
    PsiElement elseParent = PsiTreeUtil.findFirstParent(elementAtCaret, true, new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return element != null
            && (element instanceof DustElseTag);
      }
    });

    // run the formatter if the user just completed typing a SIMPLE_INVERSE or a CLOSE_BLOCK_STACHE
    if (elseParent != null) {
      // grab the current caret position (AutoIndentLinesHandler is about to mess with it)
      PsiDocumentManager.getInstance(project).commitAllDocuments();
      CaretModel caretModel = editor.getCaretModel();
      CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
      codeStyleManager.adjustLineIndent(file, editor.getDocument().getLineStartOffset(caretModel.getLogicalPosition().line));
    }
  }
}
