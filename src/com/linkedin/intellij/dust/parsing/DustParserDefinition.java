package com.linkedin.intellij.dust.parsing;

import com.linkedin.intellij.dust.psi.DustPsiFile;
import com.linkedin.intellij.dust.psi.impl.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 4:16 PM
 */
public class DustParserDefinition implements ParserDefinition {
  @NotNull
  public Lexer createLexer(Project project) {
    return new DustLexer();
  }

  public PsiParser createParser(Project project) {
    return new DustParser();
  }

  public IFileElementType getFileNodeType() {
    return DustTokenTypes.FILE;
  }

  @NotNull
  public TokenSet getWhitespaceTokens() {
    return DustTokenTypes.WHITESPACES;
  }

  @NotNull
  public TokenSet getCommentTokens() {
    return DustTokenTypes.COMMENTS;
  }

  @NotNull
  public TokenSet getStringLiteralElements() {
    return DustTokenTypes.STRING_LITERALS;
  }

  @NotNull
  public PsiElement createElement(ASTNode node) {
    if (node.getElementType() == DustTokenTypes.BLOCK_WRAPPER) {
      return new DustBlockWrapperImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.OPEN_BLOCK_TAG) {
      return new DustOpenBlockImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.OPEN_CONDITIONAL_BLOCK_TAG) {
      return new DustOpenConditionalBlockImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.CLOSE_BLOCK_TAG) {
      return new DustCloseBlockImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.KEY_TAG) {
      return new DustSimpleTagImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.PATH) {
      return new DustPathImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.PARAM) {
      return new DustParamImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.PARTIAL_TAG) {
      return new DustPartialImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.PARTIAL_NAME) {
      return new DustPartialNameImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.SIMPLE_CONDITIONAL) {
      return new DustSimpleConditionalImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.STATEMENTS) {
      return new DustStatementsImpl(node);
    }

    if (node.getElementType() == DustTokenTypes.COMMENT) {
      return new DustCommentImpl(node);
    }

    return new DustPsiElementImpl(node);
  }

  public PsiFile createFile(FileViewProvider viewProvider) {
    return new DustPsiFile(viewProvider);
  }

  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }
}
