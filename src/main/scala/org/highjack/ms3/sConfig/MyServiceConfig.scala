package org.highjack.ms3.sConfig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties("my-service")
case class MyServiceConfig() {
  @BeanProperty
  var someKey: String = _
}
