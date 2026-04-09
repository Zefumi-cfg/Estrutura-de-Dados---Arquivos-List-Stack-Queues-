 class FilaPrioridadeHeapEstavel<T extends Comparable<T>> {

    // Wrapper para associar o elemento à sua ordem de chegada
    private class ItemEstavel implements Comparable<ItemEstavel> {
        T conteudo;
        long sequencia;

        ItemEstavel(T conteudo, long sequencia) {
            this.conteudo = conteudo;
            this.sequencia = sequencia;
        }

        @Override
        public int compareTo(ItemEstavel outro) {
            int comparacao = this.conteudo.compareTo(outro.conteudo);

            // Se as prioridades forem iguais, quem chegou primeiro (menor sequencia)
            // deve ser considerado "maior" para subir no Max-Heap.
            if (comparacao == 0) {
                return Long.compare(outro.sequencia, this.sequencia);
            }
            return comparacao;
        }

        @Override
        public String toString() {
            return conteudo.toString();
        }
    }

    private ItemEstavel[] heap;
    private int nElementos;
    private int capacidade;
    private long contadorSequencial = 0; // Gera a ordem de chegada

    @SuppressWarnings("unchecked")
    public FilaPrioridadeHeapEstavel(int capacidade) {
        this.capacidade = capacidade;
        this.heap = (ItemEstavel[]) new FilaPrioridadeHeapEstavel.ItemEstavel[capacidade];
        this.nElementos = 0;
    }

    // --- Métodos de Operação ---

    public void enfileirar(T elemento) {
        if (nElementos == capacidade) throw new IllegalStateException("Heap cheio");

        // Criamos o wrapper com o ID de chegada atual
        ItemEstavel novoItem = new ItemEstavel(elemento, contadorSequencial++);
        int indice = nElementos;
        heap[indice] = novoItem;
        nElementos++;

        subir(indice);
    }

    public T desenfileirar() {
        if (nElementos == 0) throw new IllegalStateException("Heap vazio");

        T raiz = heap[0].conteudo;
        heap[0] = heap[nElementos - 1];
        heap[nElementos - 1] = null;
        nElementos--;

        if (nElementos > 0) {
            descerIterativo(0);
        }

        return raiz;
    }

    // --- Lógica de Reorganização ---

    private void subir(int indice) {
        while (indice != 0 && heap[indice].compareTo(heap[pai(indice)]) > 0) {
            trocar(indice, pai(indice));
            indice = pai(indice);
        }
    }

    private void descerIterativo(int indice) {
        while (filhoEsquerda(indice) < nElementos) {
            int maiorFilho = filhoEsquerda(indice);
            int direita = filhoDireita(indice);

            if (direita < nElementos && heap[direita].compareTo(heap[maiorFilho]) > 0) {
                maiorFilho = direita;
            }

            if (heap[indice].compareTo(heap[maiorFilho]) >= 0) break;

            trocar(indice, maiorFilho);
            indice = maiorFilho;
        }
    }

    // --- Auxiliares ---

    private int pai(int i) { return (i - 1) / 2; }
    private int filhoEsquerda(int i) { return 2 * i + 1; }
    private int filhoDireita(int i) { return 2 * i + 2; }

    private void trocar(int i, int j) {
        ItemEstavel temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public String toString() {
        if (nElementos == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nElementos; i++) {
            sb.append(heap[i]).append(i < nElementos - 1 ? ", " : "");
        }
        return sb.append("]").toString();
    }
}
class TesteHeapEstavel {
    public static void main(String[] args) {
        FilaPrioridadeHeapEstavel<Integer> heap = new FilaPrioridadeHeapEstavel<>(10);

        System.out.println("Inserindo: 10, 20, 5, 30, 15");
        heap.enfileirar(10);
        heap.enfileirar(20);
        heap.enfileirar(5);
        heap.enfileirar(30);
        heap.enfileirar(15);

        System.out.println("Estado do Heap: " + heap);

        System.out.println("Removendo maior (deve ser 30): " + heap.desenfileirar());
        System.out.println("Estado após remoção: " + heap);
    }
}