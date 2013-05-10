package com.linkedin.intellij.dust;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 3:17 PM
 */
public class DustColorSettingsPage implements ColorSettingsPage {
  /*
  private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
      new AttributesDescriptor("Dust Tag", DustSyntaxHighlighter.TAG),
      new AttributesDescriptor("Identifier", DustSyntaxHighlighter.IDENTIFIER),
      new AttributesDescriptor("String", DustSyntaxHighlighter.STRING),
      new AttributesDescriptor("Comments", DustSyntaxHighlighter.COMMENT),
      new AttributesDescriptor("Comments", DustSyntaxHighlighter.TODO)
  };*/
  private static final AttributesDescriptor[] ATTRS;

  static {
    ATTRS = new AttributesDescriptor[DustSyntaxHighlighter.DISPLAY_NAMES.size()];
    Set<TextAttributesKey> textAttributesKeys = DustSyntaxHighlighter.DISPLAY_NAMES.keySet();
    TextAttributesKey[] keys = textAttributesKeys.toArray(new TextAttributesKey[textAttributesKeys.size()]);
    for (int i = 0; i < keys.length; i++) {
      TextAttributesKey key = keys[i];
      String name = DustSyntaxHighlighter.DISPLAY_NAMES.get(key).getFirst();
      ATTRS[i] = new AttributesDescriptor(name, key);
    }
  }

  private String demo = "";

  public DustColorSettingsPage() {
    try {
      final InputStream is = DustColorSettingsPage.class.getResourceAsStream("demo_text.tl");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      int c;
      while ((c = br.read()) != -1) {
        sb.append((char) c);
      }
      demo = sb.toString();
    } catch (Throwable e) {
      // Fallback demo
      demo = "{! You are reading the DUST template example !}\n" +
          "{?person test=something keya=valuea keyb=\"linked{expression}in\"}\n" +
          "  something abc=abc\n" +
          "  <div class=\"abc\">hello</div>\n" +
          "{/person}";
    }
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return AllIcons.FileTypes.Properties;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new DustSyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return demo;
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return null;
  }

  @NotNull
  @Override
  public AttributesDescriptor[] getAttributeDescriptors() {
    return ATTRS;
  }

  @NotNull
  @Override
  public ColorDescriptor[] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }

  @NotNull
  public String getDisplayName() {
    return DustBundle.message("dust.files.file.type.description");
  }
}