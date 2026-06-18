package br.com.conectateabackend.aulaawsjavards.controler;


import br.com.conectateabackend.aulaawsjavards.service.SnsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sns")
public class SnsController {

    private final SnsService snsService;

    public SnsController(SnsService snsService) {
        this.snsService = snsService;
    }

    @GetMapping("/publish")
    public ResponseEntity<?> publish(@RequestParam String message) {
        String messageId = snsService.publish(message);

        return ResponseEntity.ok(Map.of(
                "status", "Mensagem publicada com sucesso",
                "messageId", messageId
        ));
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> subscribeEmail(@RequestParam String email) {
        String subscriptionArn = snsService.subscribeEmail(email);

        return ResponseEntity.ok(Map.of(
                "status", "Assinatura por e-mail solicitada",
                "email", email,
                "subscriptionArn", subscriptionArn,
                "aviso", "Verifique o e-mail e confirme a assinatura"
        ));
    }

    @GetMapping("/subscribe-app")
    public ResponseEntity<?> subscribeApp(@RequestParam String endpointUrl) {
        String subscriptionArn = snsService.subscribeHttp(endpointUrl);

        return ResponseEntity.ok(Map.of(
                "status", "Assinatura HTTP solicitada",
                "endpointUrl", endpointUrl,
                "subscriptionArn", subscriptionArn
        ));
    }
}