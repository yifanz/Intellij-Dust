package com.linkedin.intellij.dust;

import com.intellij.codeInsight.highlighting.BraceMatcher;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.linkedin.intellij.dust.psi.DustTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 5:17 PM
 */
public class DustBraceMatcher implements BraceMatcher {

  private static final Set<IElementType> LEFT_BRACES = new HashSet<IElementType>();
  private static final Set<IElementType> RIGHT_BRACES = new HashSet<IElementType>();

  static {
    LEFT_BRACES.add(DustTypes.LD);
    LEFT_BRACES.add(DustTypes.BLOCK);
    LEFT_BRACES.add(DustTypes.SECTION);
    LEFT_BRACES.add(DustTypes.PARTIAL);
    LEFT_BRACES.add(DustTypes.HELPER);
    LEFT_BRACES.add(DustTypes.EXISTANCE);
    LEFT_BRACES.add(DustTypes.NOT_EXISTANCE);
    LEFT_BRACES.add(DustTypes.INLINE_PARTIAL);
    LEFT_BRACES.add(DustTypes.ELSE);

    RIGHT_BRACES.add(DustTypes.SLASH_RD);
    RIGHT_BRACES.add(DustTypes.RD);
  }

  @Override
  public boolean isPairBraces(IElementType tokenType1, IElementType tokenType2) {
    return LEFT_BRACES.contains(tokenType1) && RIGHT_BRACES.contains(tokenType2)
        || RIGHT_BRACES.contains(tokenType1) && LEFT_BRACES.contains(tokenType2);
  }

  @Override
  public boolean isLBraceToken(HighlighterIterator iterator, CharSequence fileText, FileType fileType) {
    return LEFT_BRACES.contains(iterator.getTokenType());
  }

  @Override
  public boolean isRBraceToken(HighlighterIterator iterator, CharSequence fileText, FileType fileType) {
    if (!RIGHT_BRACES.contains(iterator.getTokenType())) {
      // definitely not a right brace
      return false;
    } else if (iterator.getTokenType() == DustTypes.SLASH_RD) {
      return true;
    }

    boolean isRBraceToken = false;
    int iteratorRetreatCount = 0;
    final int INITIAL = 0;
    final int STRING = 1;
    final int BRACE = 2;
    Stack<Integer> state = new Stack<Integer>();
    state.push(INITIAL);
    while (true) {
      iterator.retreat();
      iteratorRetreatCount++;

      if (iterator.atEnd()) {
        break;
      }

      if (iterator.getTokenType() == DustTypes.STRING_END && state.peek().equals(INITIAL)) {
        state.push(STRING);
      } else if (iterator.getTokenType() == DustTypes.STRING_START && state.peek().equals(STRING)) {
        state.pop();
      } else if (RIGHT_BRACES.contains(iterator.getTokenType()) && state.peek().equals(INITIAL)) {
        state.push(BRACE);
      } else if (LEFT_BRACES.contains(iterator.getTokenType()) && state.peek().equals(BRACE)) {
        state.pop();
      }

      if (state.peek().equals(STRING)
          || state.peek().equals(BRACE)) {
        continue;
      }

      if (iterator.getTokenType() == DustTypes.SECTION
          || iterator.getTokenType() == DustTypes.EXISTANCE
          || iterator.getTokenType() == DustTypes.NOT_EXISTANCE
          || iterator.getTokenType() == DustTypes.INLINE_PARTIAL
          || iterator.getTokenType() == DustTypes.BLOCK
          || iterator.getTokenType() == DustTypes.HELPER) {
        // If first open type token we encountered is a block opener, the this is not a close brace because
        // the paired close brace for these block openers is at the end of the corresponding block close tags
        break;
      }

      if (iterator.getTokenType() == DustTypes.LD
          || iterator.getTokenType() == DustTypes.PARTIAL
          || iterator.getTokenType() == DustTypes.CLOSE
          || iterator.getTokenType() == DustTypes.ELSE) {
        // If the first open token we encountered was a simple opener (i.e. didn't start a block)
        // or the closing brace of a closing tag, then this is definitely a right brace.
        isRBraceToken = true;
      }
    }

    // reset the given iterator before returning
    while (iteratorRetreatCount-- > 0) {
      iterator.advance();
    }

    return isRBraceToken;
  }

  @Override
  public int getBraceTokenGroupId(IElementType tokenType) {
    return 1;
  }

  @Override
  public boolean isStructuralBrace(HighlighterIterator iterator, CharSequence text, FileType fileType) {
    return false;
  }

  @Nullable
  @Override
  public IElementType getOppositeBraceTokenType(@NotNull IElementType type) {
    return null;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
    return true;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}

