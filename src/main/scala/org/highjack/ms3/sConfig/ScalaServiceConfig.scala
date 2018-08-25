package org.highjack.ms3.sConfig

import org.springframework.stereotype.Component

@Component
//@ConfigurationProperties("scala-service") +> declare this and the property in application.yml to configure
case class ScalaServiceConfig() {
 /* @BeanProperty */
  var someKey: String = "I AM SCALA SERVICE CONFIG"
}
