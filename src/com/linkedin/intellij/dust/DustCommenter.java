package com.linkedin.intellij.dust;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 11:37 AM
 */
public class DustCommenter implements Commenter {
  @Nullable
  @Override
  public String getLineCommentPrefix() {
    return null;
  }

  @Nullable
  @Override
  public String getBlockCommentPrefix() {
    return "{!";
  }

  @Nullable
  @Override
  public String getBlockCommentSuffix() {
    return "!}";
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentPrefix() {
    return null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentSuffix() {
    return null;
  }

}
