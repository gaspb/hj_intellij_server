package org.highjack.ms3.sService

import org.highjack.ms3.sConfig.MyServiceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MyService @Autowired()(serviceConfig: MyServiceConfig) {
  def getMessage: String = {
    s"The service says: '${serviceConfig.someKey}'"
  }
}
