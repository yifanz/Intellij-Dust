package com.linkedin.intellij.dust.exception;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/8/13
 * Time: 5:29 PM
 */
public class DustParsingException  extends RuntimeException {
  public DustParsingException() {
    super("Failed to parse Dust syntax");
  }
}
