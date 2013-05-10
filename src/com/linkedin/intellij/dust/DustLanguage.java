/*
Based on Intellij-handlebars plugin:
https://github.com/JetBrains/intellij-plugins/tree/master/handlebars
 */
package com.linkedin.intellij.dust;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.templateLanguages.TemplateLanguage;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 12:42 PM
 */
public class DustLanguage extends Language implements TemplateLanguage {
  public static final DustLanguage INSTANCE = new DustLanguage();

  @SuppressWarnings("SameReturnValue") // ideally this would be public static, but the static inits in the tests get cranky when we do that
  public static LanguageFileType getDefaultTemplateLang() {
    return StdFileTypes.HTML;
  }

  public DustLanguage() {
    super("Dust", "text/x-dust-template", "text/x-dust");
  }
}
