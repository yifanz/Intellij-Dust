package com.linkedin.intellij.dust.psi;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 2:00 PM
 */

/**
 * Element for close block mustaches: "{/foo}"
 */
public interface DustCloseBlock extends DustBlock {

  @Override
  DustOpenBlock getPairedElement();
}
