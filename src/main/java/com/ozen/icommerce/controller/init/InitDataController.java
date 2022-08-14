package com.ozen.icommerce.controller.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozen.icommerce.service.init.InitialService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/init")
public class InitDataController implements InitOperations {

	@Autowired
	private InitialService initialService;
	
	@Override
	public void initialData() {
		initialService.init();
	}
}
