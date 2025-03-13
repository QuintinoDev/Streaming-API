package br.com.joaodev.Striming.Service;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultarChatGpt {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService("coloque sua chave da Api aqui");

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(5000)
                .temperature(0.7)
                .build();
        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
