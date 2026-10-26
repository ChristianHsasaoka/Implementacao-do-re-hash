# Organização de Dados por Duplo Hashing

## Proposta

Este programa simula o armazenamento de números inteiros em uma tabela hash limitada a 100 endereços. A atividade solicita o sorteio de 100 valores, o tratamento das colisões e a interrupção das inserções quando **90% da tabela estiver ocupada**.

A solução foi escrita em Java, utilizando vetores e estruturas de repetição e decisão, sem bibliotecas de coleções.

## Tratamento das colisões

O primeiro endereço é calculado pelo resto da divisão do número por 100:

`numero % 100`

Quando esse endereço já está preenchido, é calculado um segundo valor, chamado **passo**, que determina para qual posição o programa deve tentar ir:

```java
int passo = 1 + 10 * ((numero / 100) % 10);
```

Depois, enquanto existir uma colisão, a próxima posição é calculada por:

```java
endereco = (endereco + passo) % 100;
```

Os passos possíveis são 1, 11, 21, 31, 41, 51, 61, 71, 81 e 91. Todos são coprimos com 100, o que permite percorrer toda a tabela sem repetir um endereço antes de completar o ciclo.

Uma colisão é contabilizada toda vez que uma posição já ocupada é consultada durante a tentativa de inserção. O programa guarda separadamente essa quantidade para cada número inserido.

## Geração e limite

A classe `Random` utiliza a seed **1090** para tornar o sorteio reproduzível. Os números são sorteados entre **0 e 100.000** (inclusive), intervalo adotado nesta implementação.

Embora sejam realizados 100 sorteios, apenas os 90 primeiros valores podem ser efetivamente inseridos. Após atingir essa quantidade, as outras dez tentativas são impedidas. Os índices não utilizados ficam com o marcador `-1`.

## Conferência realizada

Na execução com a semente definida, o programa apresentou:

| Informação | Valor |
| --- | --- |
| Números armazenados | 90 |
| Tentativas bloqueadas | 10 |
| Colisões durante as inserções | 117 |
| Posições livres | 6, 14, 27, 49, 50, 58, 69, 72, 88 e 93 |

**Números que sofreram mais colisões:**

- **35838:** 9 colisões; localizado na posição 67.
- **79368:** 9 colisões; localizado na posição 47.
- **55998:** 7 colisões; localizado na posição 35.

O desempate do ranking considera primeiro o elemento inserido anteriormente.

### Teste de busca

Depois de organizar os dados, o programa procura os três números destacados. A pesquisa utiliza o mesmo endereço inicial e o mesmo passo calculados para cada número durante a inserção, avançando até encontrar o valor ou uma posição livre.

Os três valores foram encontrados nas respectivas posições indicadas acima.

## Arquivo e execução

Código-fonte: `Main.java`.

Para executar em um terminal:

```bash
javac Main.java
java Main
```

O uso da API do Java se limita à geração de números aleatórios (`Random`) e à impressão dos resultados. A tabela hash, o cálculo das colisões, a seleção dos três elementos e a busca são implementados no próprio código.
