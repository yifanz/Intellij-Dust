package com.linkedin.intellij.dust.file;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.FileAttribute;
import com.intellij.openapi.vfs.newvfs.NewVirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.linkedin.intellij.dust.DustBundle;
import com.linkedin.intellij.dust.DustIcons;
import com.linkedin.intellij.dust.DustLanguage;
import com.linkedin.intellij.dust.DustLayeredSyntaxHighlighter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 10:48 AM
 */
public class DustFileType extends LanguageFileType implements TemplateLanguageFileType, FileTypeIdentifiableByVirtualFile {
  private static final FileAttribute DUST_ATTRIBUTE = new FileAttribute("is.dust", 1, true);
  public static final DustFileType INSTANCE = new DustFileType();
  @NonNls public static final String DEFAULT_EXTENSION = "tl;dust";

  private DustFileType() {
    super(DustLanguage.INSTANCE);
    // register highlighter - lazy singleton factory
    FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
      public EditorHighlighter getEditorHighlighter(@Nullable Project project,
                                                    @NotNull FileType fileType,
                                                    @Nullable VirtualFile virtualFile,
                                                    @NotNull EditorColorsScheme editorColorsScheme) {
        return new DustLayeredSyntaxHighlighter(project, virtualFile, editorColorsScheme);
      }
    });
  }

  @Override
  @NotNull
  public String getName() {
    return "Dust";
  }

  @Override
  @NotNull
  public String getDescription() {
    return DustBundle.message("dust.files.file.type.description");
  }

  @Override
  @NotNull
  public String getDefaultExtension() {
    return DEFAULT_EXTENSION;
  }

  @Override
  public Icon getIcon() {
    return DustIcons.FILE;
  }

  public Charset extractCharsetFromFileContent(@Nullable final Project project,
                                               @Nullable final VirtualFile file,
                                               @NotNull final String content) {
    LanguageFileType associatedFileType = getAssociatedFileType(file, project);

    if (associatedFileType == null) {
      return null;
    }

    return associatedFileType.extractCharsetFromFileContent(project, file, content);
  }

  private static LanguageFileType getAssociatedFileType(VirtualFile file, Project project) {
    if (project == null) {
      return null;
    }
    Language language = TemplateDataLanguageMappings.getInstance(project).getMapping(file);

    LanguageFileType associatedFileType = null;
    if (language != null) {
      associatedFileType = language.getAssociatedFileType();
    }

    if (language == null || associatedFileType == null) {
      associatedFileType = DustLanguage.getDefaultTemplateLang();
    }
    return associatedFileType;
  }

  @Override
  public boolean isMyFileType(VirtualFile file) {
    return hasDustAttribute(file);
  }

  public static boolean hasDustAttribute(@Nullable VirtualFile file) {
    if (!(file instanceof NewVirtualFile) || !file.isValid()) {
      return false;
    }
    try {
      DataInputStream inputStream = DUST_ATTRIBUTE.readAttribute(file);
      try {
        return inputStream != null && inputStream.readBoolean();
      }
      finally {
        if (inputStream != null) {
          inputStream.close();
        }
      }
    }
    catch (IOException e) {
      return false;
    }
  }

  public static void markAsDustFile(@Nullable VirtualFile file, boolean value) {
    if (!(file instanceof NewVirtualFile) || !file.isValid()) {
      return;
    }
    DataOutputStream outputStream = DUST_ATTRIBUTE.writeAttribute(file);
    try {
      try {
        outputStream.writeBoolean(value);
      }
      finally {
        outputStream.close();
      }
    }
    catch (IOException e) {
      // ignore
    }
  }
}
