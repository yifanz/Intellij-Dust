package com.linkedin.intellij.dust.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DustParser implements PsiParser {

  @NotNull
  public ASTNode parse(IElementType root, PsiBuilder builder) {
    final PsiBuilder.Marker rootMarker = builder.mark();

    new DustParsing(builder).parse();

    rootMarker.done(root);

    return builder.getTreeBuilt();
  }
}
