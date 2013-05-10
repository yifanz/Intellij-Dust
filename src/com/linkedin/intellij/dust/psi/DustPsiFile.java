package com.linkedin.intellij.dust.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.PsiFileEx;
import com.linkedin.intellij.dust.file.DustFileType;
import com.linkedin.intellij.dust.DustLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 2:12 PM
 */
public class DustPsiFile extends PsiFileBase implements PsiFileEx {

  public DustPsiFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, DustLanguage.INSTANCE);
  }

  @NotNull
  public FileType getFileType() {
    return DustFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "DustFile:" + getName();
  }
}