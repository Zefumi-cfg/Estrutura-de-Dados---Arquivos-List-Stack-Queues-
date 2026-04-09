

class FilaDinamica<T> {

    // Classe interna para representar os elos da fila
    private class Nodo {
        T elemento;
        Nodo proximo;

        Nodo(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    private Nodo inicio; // Referência para o primeiro elemento (quem sai primeiro)
    private Nodo fim; // Referência para o último elemento (quem acabou de entrar)
    private int nElementos;

    public FilaDinamica() {
        this.inicio = null;
        this.fim = null;
        this.nElementos = 0;
    }

    // --- Métodos de Verificação ---

    public boolean estaVazia() { // isEmpty
        return nElementos == 0;
    }

    public int tamanho() { // size
        return nElementos;
    }

    // --- Métodos de Operação ---

    // enfileirar (enqueue)
    public void enfileirar(T elemento) {
        Nodo novoNodo = new Nodo(elemento);

        if (estaVazia()) {
            inicio = novoNodo;
            fim = novoNodo;
        } else {
            // O atual último aponta para o novo
            fim.proximo = novoNodo;
            // O novo passa a ser o último
            fim = novoNodo;
        }
        nElementos++;
    }

    // desenfileirar (dequeue)
    public T desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Stack Underflow: A fila está vazia.");
        }

        T valor = inicio.elemento;
        // O início avança para o próximo da fila
        inicio = inicio.proximo;

        // Se a fila ficou vazia, o fim também deve ser nulo
        if (inicio == null) {
            fim = null;
        }

        nElementos--;
        return valor;
    }

    // --- Métodos de Consulta e Exibição ---

    // consultarFrente (peek ou front)
    public T consultarFrente() {
        if (estaVazia()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return inicio.elemento;
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Fila: vazia";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Frente -> ");
        Nodo atual = inicio;
        while (atual != null) {
            sb.append("[").append(atual.elemento).append("]");
            if (atual.proximo != null) {
                sb.append(" - ");
            }
            atual = atual.proximo;
        }
        sb.append(" <- Fim");
        return sb.toString();
    }
}

// Classe para representar um Pedido no sistema
class Pedido {
    private int id;
    private String descricao;

    public Pedido(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "#" + id + " " + descricao;
    }
}

class TesteFilaDinamica {
    public static void main(String[] args) {
        // Criando uma fila dinâmica de objetos do tipo Pedido
        FilaDinamica<Pedido> filaPedidos = new FilaDinamica<>();

        System.out.println("--- 1. Recebendo Novos Pedidos ---");
        filaPedidos.enfileirar(new Pedido(101, "Notebook"));
        filaPedidos.enfileirar(new Pedido(102, "Mouse Gamer"));
        filaPedidos.enfileirar(new Pedido(103, "Monitor 4K"));

        System.out.println(filaPedidos);
        System.out.println("Tamanho atual da fila: " + filaPedidos.tamanho());

        System.out.println("\n--- 2. Consultando o Próximo da Fila ---");
        // Consulta sem remover
        System.out.println("O próximo pedido a ser processado é: " + filaPedidos.consultarFrente());

        System.out.println("\n--- 3. Processando Pedidos (Removendo) ---");
        while (!filaPedidos.estaVazia()) {
            Pedido pedidoSendoProcessado = filaPedidos.desenfileirar();
            System.out.println("Enviando para transportadora: " + pedidoSendoProcessado);
            System.out.println("Estado atual: " + filaPedidos);
        }

        System.out.println("\n--- 4. Teste com outro tipo (String) ---");
        FilaDinamica<String> filaNomes = new FilaDinamica<>();
        filaNomes.enfileirar("Carlos");
        filaNomes.enfileirar("Diana");
        System.out.println(filaNomes);
    }
}