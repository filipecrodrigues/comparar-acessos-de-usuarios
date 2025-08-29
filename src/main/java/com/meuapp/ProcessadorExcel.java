package com.meuapp;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.sql.Types.NUMERIC;
import static javax.management.openmbean.SimpleType.STRING;

/**
 * Classe utilitária para processar arquivos Excel
 * Lê dados de usuários e seus grupos de acesso
 */
public class ProcessadorExcel {
    //Docuemntação java doc

    /**
     * Lê um arquivo Excel e extrai dados de usuários e seus grupos
     *
     * @param arquivoExcel Arquivo Excel a ser processado
     * @return Map com nome do usuário como chave e Set de grupos como valor
     * @throws Exception Se houver erro na leitura ou formato inválido
     */

    public static Map<String, Set<String>> lerDadosUsuario(File arquivoExcel) throws Exception {
        Map<String, Set<String>> dadosUsuarios = new HashMap<>();

        //Variaveis controles de colunas
        int posicaoColunLogin = -1;
        int posicaoColunAcesso = -1;

        try (FileInputStream entradaArquivo = new FileInputStream(arquivoExcel);
             Workbook plhanilhaExcel = new XSSFWorkbook(entradaArquivo)) {

            // obtem priemira aba da planilha
            Sheet abaDados = plhanilhaExcel.getSheetAt(0);

            if (abaDados.getPhysicalNumberOfRows() == 0) {
                throw new Exception("Arquivo Excel está vazio: " + arquivoExcel.getName());
            }

            //processa cabeçalho para localizar colunas
            Row linhacabecalho = abaDados.getRow(0);
            if (linhacabecalho == null) {
                throw new Exception("Arquivo sem cabeçalho: " + arquivoExcel.getName());
            }

            //Localiza colunas "login" e "acesso"
            for (Cell celulaCabecalho : linhacabecalho) {
                String textoCabecalho = obterTextoSeguro(celulaCabecalho);
                if (textoCabecalho != null) {
                    String nomeColuna = textoCabecalho.trim().toLowerCase();
                    if (nomeColuna.equals("login")) {
                        posicaoColunLogin = celulaCabecalho.getColumnIndex();
                    }
                    if (nomeColuna.equals("acesso")) {
                        posicaoColunAcesso = celulaCabecalho.getColumnIndex();
                    }
                }
            }

            // Valida se encontrou colunas obrigatórias
            if (posicaoColunLogin == -1 || posicaoColunAcesso == -1) {
                throw new Exception("Arquivo deve conter colunas 'Login' e 'Grupo': " + arquivoExcel.getName());
            }

            // Processa dados linha por linha
            for (int numeroLinha = 1; numeroLinha <= abaDados.getLastRowNum(); numeroLinha++) {
                Row linhaDados = abaDados.getRow(numeroLinha);

                if (linhaDados == null || isLinhaVazia(linhaDados)) {
                    continue;  //pula linhas vazias
                }

                //Lê login do usuário
                String loginUsuario = obterTextoSeguro(linhaDados.getCell(posicaoColunLogin));
                if ((loginUsuario == null || loginUsuario.trim().isEmpty())){
                    continue;  // Pula linhas sem login
                }

                //lê acesso do usuario
                String nomeAcesso = obterTextoSeguro(linhaDados.getCell(posicaoColunAcesso));
                if (nomeAcesso != null && !nomeAcesso.trim().isEmpty()){
                    nomeAcesso =nomeAcesso.trim().toUpperCase();

                    //Adiciona acesso ao usuario
                    dadosUsuarios.computeIfAbsent(loginUsuario, k -> new HashSet<>()).add(nomeAcesso);
                }
            }

        }catch(FileNotFoundException e){
            throw new Exception("Arquivo não encontrado: " + arquivoExcel.getName());
        } catch (IOException e) {
            throw new Exception("Erro ao ler arquivo: " + e.getMessage());
        }
        return dadosUsuarios;

    }
    /**
     * Converte célula para texto de forma segura
     */
    private static String obterTextoSeguro(Cell celula){
        if(celula == null) return null;
        switch (celula.getCellType()) {
            case STRING:
                return celula.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celula)) {

                }
        }
    }

}