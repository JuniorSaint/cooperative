//package br.com.cooperative.controllers;
//
//import br.com.proonto.models.requests.MailRequest;
//import br.com.proonto.models.responses.MailResponse;
//import br.com.proonto.services.EmailService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@CrossOrigin(origins = "*", maxAge = 60 * 60)
//@RequestMapping("/v1/email")
//@Tag(name = "Email", description = "Manager email")
//public class EmailController {
//    @Autowired
//    private EmailService service;
//
//    @PostMapping("/sending")
//    public MailResponse sendEmail(@RequestBody MailRequest request) throws IOException {
//        Map<String, Object> model = new HashMap<>();
//        model.put("Name", request.getName());
//        model.put("location", "Bangalore,India");
//        return service.sendEmail(request, model);
//    }
//}
