import java.util.Random;

public class Main {

    // Passos 1, 11, 21, ..., 91: todos percorrem as 100 posicoes.
    static int calcularPasso(int numero) {
        return 1 + 10 * ((numero / 100) % 10);
    }

    // A busca repete a sequencia de posicoes utilizada na insercao.
    static int buscar(int[] tabela, int numero) {
        int posicao = numero % 100;
        int passo = calcularPasso(numero);
        int tentativas = 0;
        int resultado = -1;

        while (tentativas < 100 && resultado == -1 && tabela[posicao] != -1) {
            if (tabela[posicao] == numero) {
                resultado = posicao;
            } else {
                posicao = (posicao + passo) % 100;
                tentativas++;
            }
        }

        return resultado;
    }

    public static void main(String[] args) {
        int[] tabela = new int[100];
        int[] inseridos = new int[90];
        int[] choques = new int[90];
        Random sorteio = new Random(1090);

        for (int i = 0; i < 100; i++) {
            tabela[i] = -1;
        }

        int quantidade = 0;
        int recusados = 0;
        int colisoesTotais = 0;

        // As 100 tentativas sao realizadas com a mesma semente.
        for (int tentativa = 0; tentativa < 100; tentativa++) {
            int numero = sorteio.nextInt(100001);

            if (quantidade < 90) {
                int endereco = numero % 100;
                int salto = calcularPasso(numero);
                int colisoes = 0;

                // Em colisao, aplica uma segunda funcao de hash.
                while (tabela[endereco] != -1) {
                    endereco = (endereco + salto) % 100;
                    colisoes++;
                }

                tabela[endereco] = numero;
                inseridos[quantidade] = numero;
                choques[quantidade] = colisoes;
                colisoesTotais += colisoes;
                quantidade++;
            } else {
                recusados++;
            }
        }

        int[] destaque = {-1, -1, -1};

        // Ranking calculado manualmente, sem ordenacao pronta.
        for (int i = 0; i < quantidade; i++) {
            if (destaque[0] == -1 || choques[i] > choques[destaque[0]]) {
                destaque[2] = destaque[1];
                destaque[1] = destaque[0];
                destaque[0] = i;
            } else if (destaque[1] == -1 || choques[i] > choques[destaque[1]]) {
                destaque[2] = destaque[1];
                destaque[1] = i;
            } else if (destaque[2] == -1 || choques[i] > choques[destaque[2]]) {
                destaque[2] = i;
            }
        }

        System.out.println("TABELA HASH - DUPLO HASHING");
        System.out.println("Tentativas de insercao: 100");
        System.out.println("Elementos armazenados: " + quantidade);
        System.out.println("Insercoes bloqueadas (90%): " + recusados);
        System.out.println("Numero total de colisoes: " + colisoesTotais);

        System.out.print("Indices que permaneceram livres: ");
        for (int i = 0; i < 100; i++) {
            if (tabela[i] == -1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        System.out.println("Valores com maior numero de colisoes:");
        for (int i = 0; i < 3; i++) {
            int indice = destaque[i];
            System.out.println("Colocacao " + (i + 1) + ": " + inseridos[indice]
                    + " (colisoes: " + choques[indice] + ")");
        }

        System.out.println("Buscando os tres valores de maior colisao:");
        for (int i = 0; i < 3; i++) {
            int numero = inseridos[destaque[i]];
            int posicao = buscar(tabela, numero);

            if (posicao == -1) {
                System.out.println(numero + " -> nao encontrado");
            } else {
                System.out.println(numero + " -> encontrado no indice " + posicao);
            }
        }
    }
}
