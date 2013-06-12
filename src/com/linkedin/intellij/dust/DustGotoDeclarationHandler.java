package com.linkedin.intellij.dust;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.linkedin.intellij.dust.psi.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 6/5/13
 * Time: 2:02 PM
 *
 * Allows goto declaration shortcut on dust partial tags
 */
public class DustGotoDeclarationHandler implements GotoDeclarationHandler {
  @Nullable
  @Override
  public PsiElement[] getGotoDeclarationTargets(PsiElement sourceElement, int offset, Editor editor) {
    return gotoReferences(sourceElement);
  }

  @Nullable
  @Override
  public String getActionText(DataContext context) {
    return null;
  }

  public static PsiElement[] gotoReferences(PsiElement sourceElement) {
    if (sourceElement != null) {
      PsiElement parent = sourceElement.getParent();
      if (parent instanceof DustPath) {
        parent = parent.getParent();
      }

      if (parent instanceof DustTagName
          && parent.getParent() instanceof DustSelfCloseTag
          && parent.getParent().getFirstChild().getNode().getElementType() == DustTypes.PARTIAL) {
        String filePathStr = sourceElement.getText();

        if (filePathStr != null && !filePathStr.trim().equals("") && filePathStr.length() > 2) {
          if (filePathStr.charAt(0) == '"' || filePathStr.charAt(0) == '\'') {
            filePathStr = filePathStr.trim().substring(1, filePathStr.length() - 1);
          }
          String[] filePath = filePathStr.split("/");

          if (filePath != null && filePath.length > 0) {
            String fileName = filePath[filePath.length - 1];

            PsiManager manager = sourceElement.getManager();
            if (manager != null) {
              Project project = manager.getProject();
              Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
                  FileTypeIndex.NAME, DustFileType.INSTANCE, GlobalSearchScope.allScope(project)
              );
              List<PsiElement> foundElements = new ArrayList<PsiElement>();
              PsiFile containingFile = sourceElement.getContainingFile();
              for (VirtualFile vFile : virtualFiles) {
                if (fileName.equals(vFile.getNameWithoutExtension())) {
                  String vFilePathStr = vFile.getPath();
                  String basePath = project.getBasePath();
                  if (basePath != null && vFilePathStr != null
                      && basePath.length() + 1 < vFilePathStr.length()
                      && vFilePathStr.startsWith(basePath)) {
                    vFilePathStr = vFilePathStr.substring(basePath.length() + 1);
                    String[] vFilePath = vFilePathStr.split("/");
                    if (vFilePath != null && vFilePath.length > 0) {
                      boolean match = true;
                      int j = vFilePath.length - 2;
                      int i = filePath.length - 2;
                      for (; i >= 0; i--, j--) {
                        if (j < 0) {
                          match = false;
                          break;
                        }
                        if (!vFilePath[j].equals(filePath[i])) {
                          match = false;
                          break;
                        }
                      }

                      if (match) {
//                        System.out.println("@@@@@@@@@ FOUND! " + vFile.getPath());
                        PsiFile psiFile = manager.findFile(vFile);
                        if (psiFile instanceof DustFile && psiFile != containingFile) {
                          foundElements.add(psiFile);
                        }
                      }
                    }
                  }
                }
              }
              int size = foundElements.size();
              if (size > 0) {
                PsiElement[] foundArray = new PsiElement[size];
                for (int i = 0; i < size; i++) {
                  foundArray[i] = foundElements.get(i);
                }

                return foundArray;
              }
            }
          }
        }
      }
    }
    return null;
  }
}
