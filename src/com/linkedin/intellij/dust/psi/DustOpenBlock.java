package com.linkedin.intellij.dust.psi;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/10/13
 * Time: 1:59 PM
 */

/**
 * Base element for mustaches which open blocks (i.e. "{#foo}" and "{^foo}")
 */
public interface DustOpenBlock extends DustBlock {

  @Override
  DustCloseBlock getPairedElement();
}
