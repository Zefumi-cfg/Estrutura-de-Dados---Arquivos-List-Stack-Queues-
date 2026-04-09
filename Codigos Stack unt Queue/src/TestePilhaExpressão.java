/*
 * OBSERVAÇÃO PARA EXECUÇÃO EM ARQUIVO ÚNICO:
 * Salve como "TestePilhaExpressao.java" e remova o "public" da classe PilhaEstatica.
 */

     class PilhaEstatica {
    private char[] itens;
    private int topo; // Índice do elemento no topo
    private int capacidade;

    public PilhaEstatica(int capacidade) {
        this.capacidade = capacidade;
        this.itens = new char[capacidade];
        this.topo = -1; // Pilha inicia vazia (topo aponta para índice inexistente)
    }

    // --- Métodos de Verificação ---

    public boolean estaVazia() {
        return topo == -1;
    }

    public boolean estaCheia() {
        return topo == capacidade - 1;
    }

    public int tamanho() {
        return topo + 1;
    }

    // --- Métodos de Operação ---

    // Insere um elemento no topo (O(1)) -> push
    public void empilhar(char item) {
        if (estaCheia()) {
            throw new IllegalStateException("A pilha está cheia");
        }
        topo++;
        itens[topo] = item;
    }


    // Remove e retorna o elemento do topo (O(1)) -> pop
    public char desempilhar() {
        if (estaVazia()) {
            throw new IllegalStateException("A pilha está vazia");
        }
        char item = itens[topo];
        topo--;
        return item;
    }

    // --- Métodos de Consulta e Exibição ---

    // Retorna o elemento do topo sem remover (O(1)) -> peek
    public char consultarTopo() {
        if (estaVazia()) {
            throw new IllegalStateException("A pilha está vazia");
        }
        return itens[topo];
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Pilha: vazia";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Topo -> ");
        // Itera do topo para a base para manter a representação visual correta
        for (int i = topo; i >= 0; i--) {
            sb.append("[").append(itens[i]).append("]");
            if (i > 0) {
                sb.append("\n        "); // Alinhamento visual para os elementos abaixo do topo
            }
        }
        sb.append("\n        Base");
        return sb.toString();
    }
}
    class TestePilhaExpressao {

    // Método utilitário para verificar o balanceamento de parênteses, colchetes e chaves
    public static boolean verificaExpressao(String expressao) {
        PilhaEstatica pilha = new PilhaEstatica(expressao.length());

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            // Se for um símbolo de abertura, empilha
            if (caractere == '(' || caractere == '[' || caractere == '{') {
                pilha.empilhar(caractere);
            }
            // Se for um símbolo de fechamento
            else if (caractere == ')' || caractere == ']' || caractere == '}') {
                if (pilha.estaVazia()) {
                    return false; // Fechamento sem abertura correspondente
                }

                char topoPilha = pilha.desempilhar();
                if ((caractere == ')' && topoPilha != '(') ||
                        (caractere == ']' && topoPilha != '[') ||
                        (caractere == '}' && topoPilha != '{')) {
                    return false; // Símbolo de fechamento não combina com o de abertura
                }
            }
        }

        // Se ao final a pilha estiver vazia, tudo foi balanceado
        return pilha.estaVazia();
    }

    public static void main(String[] args) {
        // Testes da Pilha simples
        System.out.println("--- Testes Básicos de Pilha ---");
        PilhaEstatica p = new PilhaEstatica(5);
        p.empilhar('A');
        p.empilhar('B');
        System.out.println(p); // Pilha: [A, B] <- TOPO
        System.out.println("Desempilhando: " + p.desempilhar());
        System.out.println(p);

        // Testes de Expressão
        System.out.println("\n--- Testes de Verificação de Expressão ---");
        String expressao1 = "((a + b) * (c - d))";
        String expressao2 = "((a + b) * (c - d)";
        String expressao3 = "[(a + b) * {c - d}]";
        String expressao4 = "([)]"; // Incorreta (cruzamento)

        System.out.println("Expressão 1 balanceada? " + verificaExpressao(expressao1)); // true
        System.out.println("Expressão 2 balanceada? " + verificaExpressao(expressao2)); // false
        System.out.println("Expressão 3 balanceada? " + verificaExpressao(expressao3)); // true
        System.out.println("Expressão 4 balanceada? " + verificaExpressao(expressao4)); // false
    }
}