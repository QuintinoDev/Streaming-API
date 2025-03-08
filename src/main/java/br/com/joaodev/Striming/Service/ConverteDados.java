package br.com.joaodev.Striming.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    //Uma classe que vem junto com a dependencia jackson, utilizada para traduzir oque vem do json.
    private ObjectMapper mapper = new ObjectMapper();

    //Sobreescrevndo o metodo que vem junto com a interfesse implemtentada.
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            //Utilizando o metodo que vem da classe que chamamos, para traduzir o json, como ela lanaça uma exeção, temos que colocar entre try catch
            return  mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
