package br.com.conectateabackend.aulaawsjavards.controler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/sns")
public class SnsReceiverController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/receiver")
    public ResponseEntity<String> receive(
            @RequestBody String body,
            @RequestHeader(value = "x-amz-sns-message-type", required = false) String messageType
    ) {
        try {
            System.out.println("==================================");
            System.out.println("Mensagem recebida do SNS");
            System.out.println("Tipo: " + messageType);
            System.out.println("Body: " + body);
            System.out.println("==================================");

            JsonNode jsonNode = objectMapper.readTree(body);

            if ("SubscriptionConfirmation".equals(messageType)) {
                String subscribeUrl = jsonNode.get("SubscribeURL").asText();

                System.out.println("Confirmando assinatura SNS...");
                System.out.println("SubscribeURL: " + subscribeUrl);

                confirmSubscription(subscribeUrl);

                return ResponseEntity.ok("Assinatura confirmada com sucesso");
            }

            if ("Notification".equals(messageType)) {
                String message = jsonNode.get("Message").asText();

                System.out.println("Mensagem SNS recebida:");
                System.out.println(message);

                return ResponseEntity.ok("Mensagem recebida com sucesso");
            }

            if ("UnsubscribeConfirmation".equals(messageType)) {
                System.out.println("Confirmação de cancelamento recebida");
                return ResponseEntity.ok("Unsubscribe recebido");
            }

            return ResponseEntity.ok("Tipo de mensagem SNS não tratado");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar mensagem SNS: " + e.getMessage());
        }
    }

    private void confirmSubscription(String subscribeUrl) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(subscribeUrl))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Resposta da confirmação SNS:");
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}
