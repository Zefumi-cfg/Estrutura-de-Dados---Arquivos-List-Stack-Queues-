

class FilaPrioridadeHeap<T extends Comparable<T>> {
    private T[] heap;
    private int nElementos;
    private int capacidade;

    @SuppressWarnings("unchecked")
    public FilaPrioridadeHeap(int capacidade) {
        this.capacidade = capacidade;
        this.heap = (T[]) new Comparable[capacidade];
        this.nElementos = 0;
    }

    // --- Métodos de Verificação ---

    public boolean estaVazia() { return nElementos == 0; }
    public boolean estaCheia() { return nElementos == capacidade; }

    // --- Métodos de Operação (Interface da Fila) ---

    public void enfileirar(T elemento) { // enqueue
        if (estaCheia()) throw new IllegalStateException("Heap cheio");

        int indice = nElementos;
        heap[indice] = elemento;
        nElementos++;

        // Sobe o elemento (Subida / Sift-up)
        while (indice != 0 && heap[indice].compareTo(heap[pai(indice)]) > 0) {
            trocar(indice, pai(indice));
            indice = pai(indice);
        }
    }

    public T desenfileirar() { // dequeue
        if (estaVazia()) throw new IllegalStateException("Heap vazio");

        T raiz = heap[0];
        heap[0] = heap[nElementos - 1];
        heap[nElementos - 1] = null;
        nElementos--;

        if (!estaVazia()) {
            // Opções para o aluno (Iterativo ou Recursivo)
            descerIterativo(0);
            // descerRecursivo(0); 
        }

        return raiz;
    }

    // --- Lógica de Reorganização (Sift-down / Heapify) ---

    
     // Versão Iterativa: mais eficiente (memory-wise)
     
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

    
     // versão recursiva (+ intuitiva)
 
    private void descerRecursivo(int indice) {
        int maior = indice;
        int esquerda = filhoEsquerda(indice);
        int direita = filhoDireita(indice);

        if (esquerda < nElementos && heap[esquerda].compareTo(heap[maior]) > 0) {
            maior = esquerda;
        }

        if (direita < nElementos && heap[direita].compareTo(heap[maior]) > 0) {
            maior = direita;
        }

        if (maior != indice) {
            trocar(indice, maior);
            descerRecursivo(maior);
        }
    }

    // métodos auxiliares

    private int pai(int i) { return (i - 1) / 2; }
    private int filhoEsquerda(int i) { return 2 * i + 1; }
    private int filhoDireita(int i) { return 2 * i + 2; }

    private void trocar(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public String toString() {
        if (estaVazia()) return "Heap: [vazio]";
        StringBuilder sb = new StringBuilder("Heap: [");
        for (int i = 0; i < nElementos; i++) {
            sb.append(heap[i]).append(i < nElementos - 1 ? ", " : "");
        }
        return sb.append("]").toString();
    }
}

 class TesteHeap {
    public static void main(String[] args) {
        FilaPrioridadeHeap<Integer> heap = new FilaPrioridadeHeap<>(10);

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
