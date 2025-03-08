package br.com.joaodev.Striming.Service;

public interface IConverteDados {
    //Interface que converte dados json e de qualquer classe
    //<T> usado quando não sabe oque vai ser devolvido ainda, sabe que alguma coisa vai ser devolvida, mas ainda nao sebe oque.
   <T> T obterDados(String json, Class<T> classe);
}
