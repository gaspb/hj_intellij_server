package org.highjack.ms3.sController

import org.highjack.ms3.sService.ScalaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
class ScalaServiceController @Autowired()(myService: ScalaService) {

  @RequestMapping(path = Array("/api/test-scala"), method = Array(RequestMethod.GET), produces = Array(MediaType.TEXT_PLAIN_VALUE))
  @ResponseBody
  def handleRequest(): String = {
    "Hallo from a Scala controller! " + myService.getMessage
  }

}
