package aplicacao;

import biblioteca.Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author VdiRemy
 */
public class LogisticaES {
    public static void main(String[] args){
        
        //instancia grafo
        //aqui as cidades sao strings
        Grafo<String> grafoLogistica = new Grafo<>();

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        
        while(opcao != 9){
                    System.out.println("\n========= SISTEMA LOGÍSTICA ES =========");
                    System.out.println("1. Cadastrar Nova Cidade Consultar");
                    System.out.println("2. Construir Nova Estrada");
                    System.out.println("3. Menor Rota (Dijkstra)");
                    System.out.println("4. Planejar Infraestrutura (Prim) ");
                    System.out.println("5. Ver conexoes da rede (BFS)");
                    System.out.println("6. Ler arquivo (cidades.txt)");
                    System.out.println("9. Sair");
                    System.out.print("Escolha: ");
            
            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                opcao = 0;
            }

            switch(opcao){
                case 1: // Cadastrar Cidade
                    System.out.print("Nome da Cidade: ");
                    String cidade = scanner.nextLine();
                    grafoLogistica.adicionar_vertice(cidade);
                    System.out.println("Cidade cadastrada!");
                    break;

                case 2: // Construir Estrada
                    System.out.print("De (Origem): ");
                    String c1 = scanner.nextLine();
                    System.out.print("Para (Destino): ");
                    String c2 = scanner.nextLine();
                    System.out.print("Distância: ");
                    try {
                        float dist = Float.parseFloat(scanner.nextLine());
                        // Adiciona IDA e VOLTA (Estrada é mão dupla)
                        grafoLogistica.adicionar_Aresta(c1, c2, dist);
                        grafoLogistica.adicionar_Aresta(c2, c1, dist);
                        System.out.println("Estrada criada!");
                    } catch (Exception e) {
                        System.out.println("Distância inválida.");
                    }
                    break;

                case 3: // Dijkstra
                    System.out.print("Cidade ORIGEM: ");
                    String orig = scanner.nextLine();
                    System.out.print("Cidade DESTINO: ");
                    String dest = scanner.nextLine();
                    grafoLogistica.dijkstra(orig, dest);
                    break;

                case 4: // Prim
                    System.out.print("Cidade INICIAL da obra: ");
                    String inicio = scanner.nextLine();
                    grafoLogistica.prim(inicio);
                    break;

                case 5: // BFS
                    grafoLogistica.busca_em_largura();
                    break;
                case 6:
                    String arquivo = "cidades.txt";
      
                    //tenta ler linha a partir de arquivo
                    try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
                        String linha;
                        //enquanto houverem linhas para ler
                        while((linha = br.readLine())!=null){
                            //formato de linha "Vila Velha;Serra;40"
                            String[] textoSeparado = linha.split(";|;\\s");

                            //verificar se tudo foi extraido corretamente
                            if(textoSeparado.length>=3){
                                String origem = textoSeparado[0].trim();
                                String destino = textoSeparado[1].trim();

                                //converte str para float
                                float peso = Float.parseFloat(textoSeparado[2]);

                                System.out.println("adicionando: " + origem + "->" + destino + " com peso "+ peso);

                                //adicionar aresta no grafo
                                grafoLogistica.adicionar_Aresta(origem,destino,peso);
                            }
                        }
                    }
                    catch(IOException e){
                    System.out.println("Erro ao ler: "+e.getMessage());
                    }
                    catch(NumberFormatException e){
                        System.out.println("Erro ao converter peso para float: "+e.getMessage());
                }
                case 9:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
}
