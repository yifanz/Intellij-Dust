package com.linkedin.intellij.dust;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 3:17 PM
 */
public class DustColorSettingsPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
      new AttributesDescriptor("Dust Tag", DustSyntaxHighlighter.TAG),
      new AttributesDescriptor("Number", DustSyntaxHighlighter.NUMBER),
      new AttributesDescriptor("Identifier", DustSyntaxHighlighter.IDENTIFIER),
      new AttributesDescriptor("String", DustSyntaxHighlighter.STRING),
      new AttributesDescriptor("Comments", DustSyntaxHighlighter.COMMENT),
      new AttributesDescriptor("Todo", DustSyntaxHighlighter.TODO)
  };

  private static final Map<String, TextAttributesKey>additionalHighlightingMap = new HashMap<String, TextAttributesKey>();
  static {
    additionalHighlightingMap.put("todocomment", DustSyntaxHighlighter.TODO);
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
    return DustIcons.FILE;
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
    return additionalHighlightingMap;
  }

  @NotNull
  @Override
  public AttributesDescriptor[] getAttributeDescriptors() {
    return DESCRIPTORS;
  }

  @NotNull
  @Override
  public ColorDescriptor[] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "Dust";
  }
}