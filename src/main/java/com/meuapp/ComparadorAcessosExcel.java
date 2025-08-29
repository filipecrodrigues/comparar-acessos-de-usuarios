package com.meuapp;


import javax.swing.*;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe responsável por comparar grupos de usuários em arquivos Excel
 * e gerar relatório com as diferenças encontradas
 */
public class ComparadorAcessosExcel {
    public static  void executar() throws Exception{

        //Seleciona o arquivo do priemiro usuario
        JOptionPane.showMessageDialog(null,"🔍 Selecione o arquivo Excel do PRIMEIRO usuário (referência)");

        File arquivoUsuarioReferencia = selecionarArquivo();
        if (arquivoUsuarioReferencia == null){
            JOptionPane.showMessageDialog(null,"⚠️ Operação cancelada pelo usuário");
            return;
        }

        //seleciona arquivo segundo usuario
        JOptionPane.showMessageDialog(null, "🔍 Selecione o arquivo Excel do SEGUNDO usuário (para comparar)");
        File arquivoUsuarioComparar  = selecionarArquivo();
        if (arquivoUsuarioComparar == null){
            JOptionPane.showMessageDialog(null,  "⚠️ Operação cancelada pelo usuário");
            return;
        }

        //Processa os arquivos excel
        Map<String, Set<String>> dadosUsuarioReferencia = ProcessadorExcel.lerDadosUsuario(arquivoUsuarioReferencia);
        Map<String, Set<String>> dadosUsuarioComparar = ProcessadorExcel.lerDadosUsuario(arquivoUsuarioComparar);

        //obter nomes dos usuarios(primeiro de cada arquivo)
        String nomeUsuarioReferencia = obterPrimeiroUsuario(dadosUsuarioReferencia);
        String nomeUsuarioComparar = obterPrimeiroUsuario(dadosUsuarioComparar);

        //obter acessos de cada usuario
        String acessosUsuarioReferencia = dadosUsuarioReferencia.get(nomeUsuarioReferencia);
        String acessosUsuarioComparar = dadosUsuarioComparar.get(nomeUsuarioComparar);

        //Encontrar os  grupos que o segundo usuario não possui
        Set<String> acessosFaltantes = encontrarAcessosFaltantes(acessosUsuarioReferencia, acessosUsuarioComparar);

        //verifica se há grupos para adicionar
        if (acessosFaltantes.isEmpty()){
            JOptionPane.showMessageDialog(null,"✅ Os usuários possuem os mesmos grupos!\n" +
                    "Nenhum grupo adicional necessário." );
            return;
        }

        //gera arquivo excel com grupos faltantes

        String nomeArquivoSaida = "grupos_adicionar_" + nomeUsuarioComparar.toLowerCase() + ".xlsx";
        GeradorExcel.criarArquivoAcessos(nomeUsuarioComparar, acessosFaltantes, nomeArquivoSaida);

        //Confirma sucesso da operação
        JOptionPane.showMessageDialog(null,
                "✅ Comparação concluída!\n\n" +
                        "📊 Usuário referência: " + nomeUsuarioReferencia + "\n" +
                        "👤 Usuário comparado: " + nomeUsuarioComparar + "\n" +
                        "📁 Arquivo gerado: " + nomeArquivoSaida + "\n" +
                        "🔢 Grupos faltantes: " + acessosFaltantes.size());
    }

    /**
     * Abre dialog para seleção de arquivo Excel
     */

    private static File selecionarArquivo(){
        JFileChooser seletorArquivo = new JFileChooser();

        //configurar filtro para arquivo excel
        seletorArquivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter( "Arquivos Excel (*.xlsx, *.xls)", "xlsx", "xls"));

        int resultado = seletorArquivo.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION){
            return seletorArquivo.getSelectedFile();
        }
        return null;
    }

    /**
     * Obtém o nome do primeiro usuário encontrado no Map
     */

        private static String obterPrimeiroUsuario(Map<String, Set<String>> dadosUsuarios) throws Exception{
            if (dadosUsuarios.isEmpty()){
                throw new Exception("Nenhum usuário encontrado no arquivo Excel");
            }
            return dadosUsuarios.keySet().iterator().next();
        }


    /**
     * Encontra grupos que estão na referência mas não no usuário comparado
     */

        private static Set<String> encontrarAcessosFaltantes(Set<String> acessosReferencia, Set<String> acessosComparar){
            Set<String> acessosFaltantes = new HashSet<>(acessosReferencia);
            acessosFaltantes.removeAll(acessosComparar);
            return acessosFaltantes;
        }
}