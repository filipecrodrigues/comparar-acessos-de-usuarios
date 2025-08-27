package com.meuapp;

public class Main {

    public static void main(String[] args) {

        //Tratamento de exceções

        try{
            CompararGruposExcel.executar();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
