package aplicacao;

import biblioteca.Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
                    
                    System.out.println("adicionando: " + origem + "->" + destino);
                    
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
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        
        while(opcao != 9){
            System.out.println("\n========= SISTEMA LOGÍSTICA ES =========");
            System.out.println("1. Consultar Menor Rota (Dijkstra)");
            System.out.println("2. Planejar Infraestrutura (Prim)");
            System.out.println("3. Ver conexoes (BFS)");
            System.out.println("9. Sair");
            System.out.print("Escolha: ");
            
            try {
                String input = scanner.nextLine();
                opcao = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                opcao = 0;
            }

            switch(opcao){
                case 1:
                    System.out.print("Cidade ORIGEM: ");
                    String orig = scanner.nextLine();
                    System.out.print("Cidade DESTINO: ");
                    String dest = scanner.nextLine();
                    grafoLogistica.dijkstra(orig, dest);
                    break;
                case 2:
                    System.out.print("Cidade INICIAL da obra: ");
                    String inicio = scanner.nextLine();
                    grafoLogistica.prim(inicio);
                    break;
                case 3:
                    grafoLogistica.busca_em_largura();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }
}

