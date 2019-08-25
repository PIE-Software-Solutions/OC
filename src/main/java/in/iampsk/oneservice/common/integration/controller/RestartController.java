package in.iampsk.oneservice.common.integration.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.iampsk.oneservice.common.integration.config.OneServiceInit;

@RestController
public class RestartController {
     
    @PostMapping("/restart")
    public void restart() {
    	OneServiceInit.restart();
    } 
}
