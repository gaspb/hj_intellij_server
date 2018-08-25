package org.highjack.ms3.sService

import org.highjack.ms3.sConfig.ScalaServiceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScalaService @Autowired()(serviceConfig: ScalaServiceConfig) {
  def getMessage: String = {
    s"The service says: '${serviceConfig.someKey}'"
  }
}
