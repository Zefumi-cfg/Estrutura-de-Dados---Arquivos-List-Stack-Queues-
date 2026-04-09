class FilaPrioridadeNaoOrdenada<T extends Comparable<T>> {

    // Classe interna para representar os nós (elos) da fila
    private class Nodo {
        public T elemento;
        public Nodo proximo;
        public Nodo anterior; // Permite remoção O(1) sem percorrer a lista novamente

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
            this.anterior = null;
        }
    }

    private Nodo inicio;
    private Nodo fim;
    private int nElementos;

    public FilaPrioridadeNaoOrdenada() {
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

    // --- Métodos de Operação (Interface da Fila) ---

    /**
     * Insere o elemento no final da fila. Operação muito rápida.
     * Nomenclatura equivalente: enqueue (O(1))
     */
    public void enfileirar(T elemento) {
        Nodo novoNodo = new Nodo(elemento);
        if (estaVazia()) {
            inicio = novoNodo;
            fim = novoNodo;
        } else {
            fim.proximo = novoNodo;
            novoNodo.anterior = fim;
            fim = novoNodo;
        }
        nElementos++;
    }

    /**
     * Localiza o maior elemento e o remove.
     * Nomenclatura equivalente: dequeue (O(n) para busca, O(1) para remoção física)
     */
    public T desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("A fila de prioridade está vazia.");
        }

        Nodo maior = buscarMaior();
        T elementoRemovido = maior.elemento;

        removerNodo(maior);
        nElementos--;

        return elementoRemovido;
    }

    /**
     * Localiza e retorna o maior elemento sem remover.
     * Nomenclatura equivalente: peek / front (O(n))
     */
    public T consultarFrente() {
        if (estaVazia()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return buscarMaior().elemento;
    }

    // --- Métodos Auxiliares de Lógica Interna ---

    private Nodo buscarMaior() {
        Nodo maior = inicio;
        Nodo atual = inicio.proximo;

        // Percorre a lista inteira para encontrar a maior prioridade
        while (atual != null) {
            if (atual.elemento.compareTo(maior.elemento) > 0) {
                maior = atual;
            }
            atual = atual.proximo;
        }
        return maior;
    }

    private void removerNodo(Nodo alvo) {
        // Caso 1: O nó é o único ou o primeiro da lista
        if (alvo == inicio) {
            inicio = inicio.proximo;
            if (inicio != null) {
                inicio.anterior = null;
            } else {
                fim = null; // Fila ficou vazia
            }
        }
        // Caso 2: O nó é o último (Otimizado pelo ponteiro anterior)
        else if (alvo == fim) {
            fim = fim.anterior;
            fim.proximo = null;
        }
        // Caso 3: O nó está no meio
        else {
            alvo.anterior.proximo = alvo.proximo;
            alvo.proximo.anterior = alvo.anterior;
        }
    }

    // --- Métodos de Exibição ---

    @Override
    public String toString() {
        if (estaVazia()) return "Fila: vazia";

        StringBuilder sb = new StringBuilder("Fila (Não Ordenada): [ ");
        Nodo atual = inicio;
        while (atual != null) {
            sb.append(atual.elemento);
            if (atual.proximo != null) sb.append(" | ");
            atual = atual.proximo;
        }
        sb.append(" ]");
        return sb.toString();
    }
}

class Tarefa implements Comparable<Tarefa> {
    private String nome;
    private int urgencia; // 1 a 10

    public Tarefa(String nome, int urgencia) {
        this.nome = nome;
        this.urgencia = urgencia;
    }

    @Override
    public int compareTo(Tarefa outra) {
        return Integer.compare(this.urgencia, outra.urgencia);
    }

    @Override
    public String toString() {
        return nome + " (Urgência: " + urgencia + ")";
    }
}

public class TestePrioridadeNaoOrdenada {
    public static void main(String[] args) {
        FilaPrioridadeNaoOrdenada<Tarefa> fila = new FilaPrioridadeNaoOrdenada<>();

        // Inserção O(1) - Muito rápida
        fila.enfileirar(new Tarefa("Email", 2));
        fila.enfileirar(new Tarefa("Relatório", 8));
        fila.enfileirar(new Tarefa("Café", 1));
        fila.enfileirar(new Tarefa("Reunião Urgente", 10));

        System.out.println("Fila como foi inserida:");
        System.out.println(fila);

        // Remoção O(n) - Precisa procurar em cada passo
        System.out.println("\nExecutando tarefas por prioridade:");
        while (!fila.estaVazia()) {
            System.out.println("Fazendo: " + fila.desenfileirar());
        }
    }
}