package com.linkedin.intellij.dust;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 1/16/13
 * Time: 1:07 PM
 */
public class DustFileTypeFactory extends FileTypeFactory {
  public void createFileTypes(@NotNull FileTypeConsumer consumer) {
    consumer.consume(DustFileType.INSTANCE, DustFileType.DEFAULT_EXTENSION);
  }
}
