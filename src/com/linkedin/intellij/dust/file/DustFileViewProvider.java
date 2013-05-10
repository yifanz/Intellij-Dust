package com.linkedin.intellij.dust.file;

import com.linkedin.intellij.dust.DustLanguage;
import com.linkedin.intellij.dust.parsing.DustTokenTypes;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutors;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 2/1/13
 * Time: 1:15 PM
 */
public class DustFileViewProvider  extends MultiplePsiFilesPerDocumentFileViewProvider
    implements ConfigurableTemplateLanguageFileViewProvider {

  private final PsiManager myManager;
  private final VirtualFile myFile;

  public DustFileViewProvider(PsiManager manager, VirtualFile file, boolean physical) {
    super(manager, file, physical);

    myManager = manager;
    myFile = file;

    getTemplateDataLanguage(myManager, myFile);
  }

  private Language getTemplateDataLanguage(PsiManager manager, VirtualFile file) {
    Language dataLang = TemplateDataLanguageMappings.getInstance(manager.getProject()).getMapping(file);
    if (dataLang == null) {
      dataLang = DustLanguage.getDefaultTemplateLang().getLanguage();
    }

    Language substituteLang = LanguageSubstitutors.INSTANCE.substituteLanguage(dataLang, file, manager.getProject());

    // only use a substituted language if it's templateable
    if (TemplateDataLanguageMappings.getTemplateableLanguages().contains(substituteLang)) {
      dataLang = substituteLang;
    }

    return dataLang;
  }

  @NotNull
  @Override
  public Language getBaseLanguage() {
    return DustLanguage.INSTANCE;
  }

  @NotNull
  @Override
  public Language getTemplateDataLanguage() {
    return getTemplateDataLanguage(myManager, myFile);
  }

  @NotNull
  @Override
  public Set<Language> getLanguages() {
    return new THashSet<Language>(Arrays.asList(new Language[]{DustLanguage.INSTANCE, getTemplateDataLanguage(myManager, myFile)}));
  }

  @Override
  protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile virtualFile) {
    return new DustFileViewProvider(getManager(), virtualFile, false);
  }

  @Override
  protected PsiFile createFile(@NotNull Language lang) {
    ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang);
    if (parserDefinition == null) {
      return null;
    }

    Language templateDataLanguage = getTemplateDataLanguage(myManager, myFile);
    if (lang == templateDataLanguage) {
      PsiFileImpl file = (PsiFileImpl)parserDefinition.createFile(this);
      file.setContentElementType(
          new TemplateDataElementType("DUST_TEMPLATE_DATA", templateDataLanguage, DustTokenTypes.CONTENT, DustTokenTypes.OUTER_ELEMENT_TYPE));
      return file;
    }
    else if (lang == DustLanguage.INSTANCE) {
      return parserDefinition.createFile(this);
    }
    else {
      return null;
    }
  }
}



