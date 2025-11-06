package Desafio4_DetectorDeTranferenciaDeDados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Desafio 4: Identificar Picos de Transferencia de Dados
 *
 * Detecta quando o volume de dados transferidos aumenta drasticamente.
 * Isso pode indicar roubo de dados (exfiltração).
 */
public class Desafio4_DetectorDeTransferenciaDeDados {
    public Map<Long, Long> identificarPicosTransferencia (String caminhoArquivo) throws IOException{
        Map<Long, Long> resultado = new HashMap<>();
        List<Long> timetamps = new ArrayList<>();
        List <Long> bytes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        try {
            String linha = br.readLine();
            while ((linha = br.readLine()) !=null) {

            }
        }
    }
}
