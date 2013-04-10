package com.linkedin.intellij.dust;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.linkedin.intellij.dust.parser.DustParser;
import com.linkedin.intellij.dust.psi.DustFile;
import com.linkedin.intellij.dust.psi.DustTypes;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 1:54 PM
 */
public class DustParserDefinition  implements ParserDefinition{
  public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
  public static final TokenSet COMMENTS = TokenSet.create(DustTypes.COMMENT_CONTENT, DustTypes.COMMENT_START, DustTypes.COMMENT_END);

  public static final IFileElementType FILE = new IFileElementType(Language.<DustLanguage>findInstance(DustLanguage.class));

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new FlexAdapter(new DustLexer((Reader) null));
  }

  @NotNull
  public TokenSet getWhitespaceTokens() {
    return WHITE_SPACES;
  }

  @NotNull
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    return new DustParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  public PsiFile createFile(FileViewProvider viewProvider) {
    return new DustFile(viewProvider);
  }

  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }

  @NotNull
  public PsiElement createElement(ASTNode node) {
    return DustTypes.Factory.createElement(node);
  }
}

