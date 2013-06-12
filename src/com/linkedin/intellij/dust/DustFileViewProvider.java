package com.linkedin.intellij.dust;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import com.linkedin.intellij.dust.psi.DustPsiUtil;
import com.linkedin.intellij.dust.psi.DustTokenType;
import com.linkedin.intellij.dust.psi.DustTypes;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 2/1/13
 * Time: 1:15 PM
 */
public class DustFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements TemplateLanguageFileViewProvider {
  public static IElementType OUTER_TYPE = new DustTokenType("OUTER");
  private static final TemplateDataElementType templateDataElementType = new TemplateDataElementType("DUST_TEMPLATE_DATA", DustLanguage.INSTANCE, DustTypes.HTML, OUTER_TYPE);

  // main language of the file (HTML)
  private final Language myTemplateDataLanguage;


  // default constructor from parent
  public DustFileViewProvider(PsiManager manager, VirtualFile file, boolean physical) {
    super(manager, file, physical);

    // get the main language of the file
    Language dataLang = TemplateDataLanguageMappings.getInstance(manager.getProject()).getMapping(file);
    if (dataLang == null) dataLang = StdFileTypes.HTML.getLanguage();

    // some magic?
    if (dataLang instanceof TemplateLanguage) {
      myTemplateDataLanguage = PlainTextLanguage.INSTANCE;
    } else {
      myTemplateDataLanguage = LanguageSubstitutors.INSTANCE.substituteLanguage(dataLang, file, manager.getProject());
    }
  }

  // constructor to be used by self
  public DustFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean physical, Language myTemplateDataLanguage) {
    super(psiManager, virtualFile, physical);
    this.myTemplateDataLanguage = myTemplateDataLanguage;
  }


  @NotNull
  @Override
  public Language getBaseLanguage() {
    return DustLanguage.INSTANCE;
  }

  @NotNull
  @Override
  public Language getTemplateDataLanguage() {
    return myTemplateDataLanguage;
  }

  @NotNull
  @Override
  public Set<Language> getLanguages() {
    return new THashSet<Language>(Arrays.asList(new Language[]{DustLanguage.INSTANCE, myTemplateDataLanguage}));
  }

  @Override
  protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile virtualFile) {
    return new DustFileViewProvider(getManager(), virtualFile, false, myTemplateDataLanguage);
  }


  @Override
  protected PsiFile createFile(Language lang) {
    // creating file for main lang (HTML)
    if (lang == myTemplateDataLanguage) {
      PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
      file.setContentElementType(templateDataElementType);
      return file;
    } else if (lang == DustLanguage.INSTANCE) {
      return LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
    } else {
      return null;
    }
  }

  /**
   * TODO This method is for just for supporting Intellij 11. Otherwise com.intellij.codeInsight.navigation.actions.GotoDeclarationAction lines 149-151 will throw nullpointerexception or assertion fail.
   *
   * @param offset
   * @return PsiReference
   */
  @Override
  @Nullable
  public PsiReference findReferenceAt(int offset) {
    for (final Language language : getLanguages()) {
      final PsiElement psiRoot = getPsi(language);
      if (psiRoot != null) {
        final PsiElement element = psiRoot.getContainingFile().getViewProvider().findElementAt(offset, language);
        if (element != null && !(element instanceof OuterLanguageElement)
            && DustPsiUtil.findParentPartialTagElement(element) != null) {

          PsiElement[] foundElements = DustGotoDeclarationHandler.gotoReferences(element);
          PsiReference fakeReference = null;
          if (foundElements != null && foundElements.length > 0) {
            fakeReference = new PsiReference() {

              @Override
              public PsiElement getElement() {
                return element;
              }

              @Override
              public TextRange getRangeInElement() {
                TextRange range = element.getTextRange();
                int start = range.getStartOffset();
                TextRange newRange = new TextRange(0, range.getEndOffset() - start);
                return newRange;
              }

              @Nullable
              @Override
              public PsiElement resolve() {
                return element;
              }

              @NotNull
              @Override
              public String getCanonicalText() {
                return element.getText();
              }

              @Override
              public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
                return null;
              }

              @Override
              public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
                return null;
              }

              @Override
              public boolean isReferenceTo(PsiElement element) {
                return false;
              }

              @NotNull
              @Override
              public Object[] getVariants() {
                return new Object[0];
              }

              @Override
              public boolean isSoft() {
                return false;
              }
            };
          }

          return fakeReference;
        }
      }
    }

    return super.findReferenceAt(offset);
  }
}

