 class FilaPrioridadeOrdenada<T extends Comparable<T>> {

    private class Nodo {
        public T elemento;
        public Nodo proximo;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    private Nodo inicio;
    private Nodo fim; // Mantido para inserção O(1) no final
    private int nElementos;

    public FilaPrioridadeOrdenada() {
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

    public void enfileirar(T elemento) { // enqueue
        insereOrdenado(elemento);
    }

    public T desenfileirar() { // dequeue
        if (estaVazia()) {
            throw new IllegalStateException("A fila de prioridade está vazia.");
        }
        T removido = inicio.elemento;
        inicio = inicio.proximo;
        nElementos--;
        if (estaVazia()) {
            fim = null;
        }
        return removido;
    }

    // --- Lógica de Inserção com Otimização de Final ---

    private void insereOrdenado(T elemento) {
        // CASO 1: Lista Vazia ou Maior Prioridade que o Início
        if (estaVazia() || elemento.compareTo(inicio.elemento) > 0) {
            Nodo novoNodo = new Nodo(elemento);
            if (estaVazia()) {
                inicio = novoNodo;
                fim = novoNodo;
            } else {
                novoNodo.proximo = inicio;
                inicio = novoNodo;
            }
            nElementos++;
        }
        // CASO 2: Menor Prioridade que o Fim (OTIMIZAÇÃO O(1))
        else if (elemento.compareTo(fim.elemento) <= 0) {
            Nodo novoNodo = new Nodo(elemento);
            fim.proximo = novoNodo;
            fim = novoNodo;
            nElementos++;
        }
        // CASO 3: Inserção no Meio (Percorre O(n))
        else {
            Nodo atual = inicio;
            while (atual.proximo != null && atual.proximo.elemento.compareTo(elemento) >= 0) {
                atual = atual.proximo;
            }
            Nodo novoNodo = new Nodo(elemento);
            novoNodo.proximo = atual.proximo;
            atual.proximo = novoNodo;
            nElementos++;
        }
    }

    @Override
    public String toString() {
        if (estaVazia()) return "Fila: vazia";

        StringBuilder sb = new StringBuilder("Frente -> ");
        Nodo atual = inicio;
        while (atual != null) {
            sb.append("[").append(atual.elemento).append("]");
            if (atual.proximo != null) sb.append(" - ");
            atual = atual.proximo;
        }
        sb.append(" <- Fim");
        return sb.toString();
    }
}

class Paciente implements Comparable<Paciente> {
    private String nome;
    private int nivelPrioridade; // 1 (Baixa) a 5 (Alta)

    public Paciente(String nome, int nivelPrioridade) {
        this.nome = nome;
        this.nivelPrioridade = nivelPrioridade;
    }

    @Override
    public int compareTo(Paciente outro) {
        return Integer.compare(this.nivelPrioridade, outro.nivelPrioridade);
    }

    @Override
    public String toString() {
        // Printa a prioridade junto com o elemento para clareza visual
        return nome + " (Prioridade: " + nivelPrioridade + ")";
    }
}

public class TesteHospital {
    public static void main(String[] args) {
        FilaPrioridadeOrdenada<Paciente> triagem = new FilaPrioridadeOrdenada<>();

        // Testando inserções que disparam as 3 lógicas:
        triagem.enfileirar(new Paciente("João", 3));   // Início (vazio)
        triagem.enfileirar(new Paciente("Maria", 5));  // Início (maior que o topo)
        triagem.enfileirar(new Paciente("José", 1));   // Fim (menor que o fim - Otimizado)
        triagem.enfileirar(new Paciente("Ana", 4));    // Meio (entre Maria e João)

        System.out.println("Estado da Fila:");
        System.out.println(triagem);

        System.out.println("\nAtendendo pacientes...");
        while (!triagem.estaVazia()) {
            System.out.println("Chamando: " + triagem.desenfileirar());
        }
    }
}