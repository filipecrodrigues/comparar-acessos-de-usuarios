package com.meuapp;


import javax.swing.*;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompararGruposExcel {

        public static void executar() throws Exception{



            //Instacia da classe JFileChooser para usuário selecionar arquivos ou diretórios do sistema de arquivos
            JFileChooser escolherArquivo = new JFileChooser();

            //Seleciona  arquivo para o Primeiro Usuário
            JOptionPane.showMessageDialog(null, "Selecione o arquivo Excel para o primeiro usuário");
            escolherArquivo.showOpenDialog(null);
            File arquivoPrimeiroUsuario = escolherArquivo.getSelectedFile();


            //Seleciona arquivo para segundo usuário
            JOptionPane.showMessageDialog(null, "Selecione o arquivo Excel para o segundo usuário");
            escolherArquivo.showOpenDialog(null);
            File arquivoSegundoUsuario = escolherArquivo.getSelectedFile();

            //lê e processa os arquivos
            Map<String, Set<String>> dadosPrimeiroArquivo  = ExcelUtils.lerExcel(arquivoPrimeiroUsuario);
            Map<String, Set<String>> dadosSegundoArquivo = ExcelUtils.lerExcel(arquivoSegundoUsuario);

            //Variaveis de usuário
            String primeiroUsuario = dadosPrimeiroArquivo.keySet().iterator().next();
            String segundoUsuario = dadosSegundoArquivo.keySet().iterator().next();

            //variaveis para acessos
            Set<String> acessosPrimeiroUsuario = dadosPrimeiroArquivo.get(primeiroUsuario);
            Set<String> acessosSegundoUsuario = dadosSegundoArquivo.get(segundoUsuario);

            Set<String> gruposParaAdicionar = new HashSet<>(acessosPrimeiroUsuario);
            gruposParaAdicionar.removeAll(acessosSegundoUsuario);

            String nomeArquivoSaida = "grupos_para_"+segundoUsuario.toLowerCase()+ ".xlsx";
            ExcelUtils.gerarExcel(segundoUsuario, gruposParaAdicionar, nomeArquivoSaida);

            JOptionPane.showMessageDialog(null, "✅ Arquivo gerado com sucesso: "+ nomeArquivoSaida);


        }


}