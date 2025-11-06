package Desafio4_DetectorDeTranferenciaDeDados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Desafio 4: Identificar Picos de Transferência de Dados
 *
 * Detecta quando o volume de dados transferidos aumenta drasticamente.
 * Isso pode indicar roubo de dados (exfiltração).
 */

public class Desafio4_PicosTransferencia {

    /**
     * Encontra picos de transferência no arquivo de logs.
     *
     * Um pico acontece quando um evento tem menos bytes que outro evento futuro.
     * Exemplo: Se agora foram 100 bytes e depois foram 500 bytes, isso é um pico!
     *
     * @param caminhoArquivo Caminho do arquivo CSV
     * @return Map com pares: timestamp atual → timestamp do próximo maior
     * @throws IOException Se der erro ao ler o arquivo
     */
    public Map<Long, Long> identificarPicosTransferencia(String caminhoArquivo) throws IOException {
        Map<Long, Long> picosEncontrados = new HashMap<>();
        List<Long> listaDeTimes = new ArrayList<>();
        List<Long> listaDeBytes = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));

        try {
            String linhaDoArquivo = leitor.readLine();
            while ((linhaDoArquivo = leitor.readLine()) != null) {


                if (linhaDoArquivo.isEmpty()) {
                    continue;
                }

                String[] colunasDoCSV = linhaDoArquivo.split(",");
                if (colunasDoCSV.length >= 7) {

                    try {
                        String textoDoTimestamp = colunasDoCSV[0].trim();
                        long numeroDoTimestamp = Long.parseLong(textoDoTimestamp);
                        long quantidadeDeBytes = 0;
                        String textoDosBytes = colunasDoCSV[6].trim();
                        if (!textoDosBytes.isEmpty()) {
                            quantidadeDeBytes = Long.parseLong(textoDosBytes);
                        }

                        listaDeTimes.add(numeroDoTimestamp);
                        listaDeBytes.add(quantidadeDeBytes);

                    } catch (NumberFormatException erro) {

                    }
                }
            }

        } finally {
            leitor.close();
        }
        if (listaDeTimes.size() == 0) {
            return picosEncontrados;
        }
        Stack<Integer> pilhasDeIndices = new Stack<>();
        int totalDeEventos = listaDeTimes.size() == 0) {
            return picosEncontrados;
        }
    }
}
