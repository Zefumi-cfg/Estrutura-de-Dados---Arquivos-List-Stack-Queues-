class FilaPrioridadeNaoOrdenada<T extends Comparable<T>> {

    
    private class Nodo {
        public T elemento;
        public Nodo proximo;
        public Nodo anterior; 

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

    

    public boolean estaVazia() { 
        return nElementos == 0;
    }

    public int tamanho() { 
        return nElementos;
    }

    
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

    
    public T consultarFrente() {
        if (estaVazia()) {
            throw new IllegalStateException("A fila está vazia.");
        }
        return buscarMaior().elemento;
    }

    

    private Nodo buscarMaior() {
        Nodo maior = inicio;
        Nodo atual = inicio.proximo;

       
        while (atual != null) {
            if (atual.elemento.compareTo(maior.elemento) > 0) {
                maior = atual;
            }
            atual = atual.proximo;
        }
        return maior;
    }

    private void removerNodo(Nodo alvo) {
        
        if (alvo == inicio) {
            inicio = inicio.proximo;
            if (inicio != null) {
                inicio.anterior = null;
            } else {
                fim = null; // Fila ficou vazia
            }
        }
        
        else if (alvo == fim) {
            fim = fim.anterior;
            fim.proximo = null;
        }
        
        else {
            alvo.anterior.proximo = alvo.proximo;
            alvo.proximo.anterior = alvo.anterior;
        }
    }

    

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
    private int urgencia; 

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

        
        fila.enfileirar(new Tarefa("Email", 2));
        fila.enfileirar(new Tarefa("Relatório", 8));
        fila.enfileirar(new Tarefa("Café", 1));
        fila.enfileirar(new Tarefa("Reunião Urgente", 10));

        System.out.println("Fila como foi inserida:");
        System.out.println(fila);

        
        System.out.println("\nExecutando tarefas por prioridade:");
        while (!fila.estaVazia()) {
            System.out.println("Fazendo: " + fila.desenfileirar());
        }
    }
}
