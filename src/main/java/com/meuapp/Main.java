package com.meuapp;

public class Main {

    public static void main(String[] args) {

        //Tratamento de exceções

        try{
            //Execulta o comparador de acessos
            ComparadorAcessosExcel.executar();

        }catch (Exception erro){
            System.out.println("❌ Erro na aplicação: "+ erro.getMessage());
            erro.printStackTrace();
        }
    }
}
