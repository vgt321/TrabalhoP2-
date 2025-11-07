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
        Stack<Integer> pilhaDeIndice = new Stack<>();
        int totalDeEventos = listaDeTimes.size();
        for (int i = totalDeEventos - 1; i >= 0; i--) {
            long bytesDoEventoAtual = listaDeBytes.get(i);
            while (!pilhaDeIndice.isEmpty()){
                int indiceDoTopo = pilhaDeIndice.peek();
                long bytesDoTopo = listaDeBytes.get(indiceDoTopo);
                if (!pilhaDeIndice.isEmpty()){
                    int indiceMaior = pilhaDeIndice.peek();
                    long timeAtual = listaDeTimes.get(i);
                    long timeMaior = listaDeTimes.get(indiceMaior);
                    picosEncontrados.put(timeAtual, timeMaior);
                }
                pilhasDeIndices.push(i);
            }
            return picosEncontrados;
        }
        //  ============================================================
        //  MÉTODO DE TESTE
        //  ============================================================

        public static void main(String[] args) {
            Desafio4_PicosTransferencia meuDesafio = new Desafio4_PicosTransferencia();
            String caminhoDoArquivo = "C:\\Users\\mards\\Documents\\GitHub\\TrabalhoP2-\\Trabalho P2\\DESAfIOS\\Desafio1_SessoesInvalidas\\forensic_logs_teste.csv";
            System.out.println("================================================================");
            System.out.println("              DESAFIO 4: PICOS DE TRANSFERÊRENCIA               ");
            System.out.println("================================================================");

            try {
                long tempoInicio = System.nanoTime();
                Map<Long, Long> resultadoDoPicos = meuDesafio.identificarPicosTransferencia(caminhoArquivo);
                long tempoFim = System.nanoTime();
                long tempoDecorrido = tempoFim - tempoInicio;
                double tempoEmMilissegundos = tempoDecorrido / 1_000_000.0;


                System.out.println("Arquivo: " + caminhoDoArquivo);
                // printf permite formatar números (%.3f = 3 casas decimais)
                System.out.printf("Tempo: %.3f ms%n", tempoEmMilissegundos);
                System.out.println("Total de picos encontrados: " + resultadoDoPicos.size());
                System.out.println("===========================================================\n");



                if (resultadoDoPicos.isEmpty()) {

                    System.out.println("Nenhum pico detectado!");
                } else {

                    System.out.println("Picos identificados:");
                    System.out.println("(Timestamp Atual → Próximo Maior)");
                    System.out.println("-----------------------------------------------------------");

                    List<Map.Entry<Long, Long>> listaDePicos = new ArrayList<>(resultadoDoPicos.entrySet());
                    listaDePicos.sort(Map.Entry.comparingByKey());

                    int numeroDoPico = 1;


                    for (Map.Entry<Long, Long> umPico : listaDePicos) {
                        long timeAtual = umPico.getKey();
                        long timeMaior = umPico.getValue();


                        System.out.printf("%3d. %d → %d%n", numeroDoPico, timeAtual, timeMaior);

                        numeroDoPico++;


                        if (numeroDoPico > 10) {
                            int picosRestantes = resultadoDoPicos.size() - 10;
                            System.out.println("... (e mais " + picosRestantes + " picos)");
                            break;
                        }
                    }
                }


                System.out.println("\n===========================================================");
                System.out.println("EXPLICAÇÃO:");
                System.out.println("Cada linha mostra: Momento X → Momento Y");
                System.out.println("Isso significa que no Momento X teve uma transferência menor,");
                System.out.println("mas no Momento Y teve uma transferência MAIOR.");
                System.out.println("Isso pode indicar roubo de dados!");
                System.out.println("===========================================================");

            } catch (IOException erro) {
                System.err.println("ERRO ao ler arquivo:");
                System.err.println(erro.getMessage());
                
            }
        }

    }
}
