package com.linkedin.intellij.dust.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.linkedin.intellij.dust.DustFileType;
import com.linkedin.intellij.dust.DustLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 2:12 PM
 */
public class DustFile  extends PsiFileBase {
  public DustFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, DustLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return DustFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Dust File";
  }

  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }
}