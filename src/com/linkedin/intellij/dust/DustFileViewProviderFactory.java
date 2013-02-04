package com.linkedin.intellij.dust;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 2/1/13
 * Time: 2:40 PM
 */
public class DustFileViewProviderFactory  implements FileViewProviderFactory {
  @Override
  public FileViewProvider createFileViewProvider(VirtualFile virtualFile, Language language, PsiManager psiManager, boolean physical) {
    return new DustFileViewProvider(psiManager, virtualFile, physical);
  }
}
