package com.linkedin.intellij.dust.config;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Created with IntelliJ IDEA.
 * User: yzhang
 * Date: 5/9/13
 * Time: 4:34 PM
 */

/**
 * Class responsible for reads and writes of properties
 */
class PropertyAccessor {

  private final PropertiesComponent myPropertiesComponent;

  PropertyAccessor(PropertiesComponent myPropertiesComponent) {
    this.myPropertiesComponent = myPropertiesComponent;
  }

  String getPropertyValue(Property property) {

    // We getOrInit to ensure that the default is written for this user the first time it is fetched
    // This will ensure that users preferences stay stable in the future, even if defaults change
    return myPropertiesComponent.getOrInit(property.getStringName(), property.getDefault());
  }

  void setPropertyValue(Property property, String propertyValue) {
    myPropertiesComponent.setValue(property.getStringName(),
        propertyValue);
  }
}

