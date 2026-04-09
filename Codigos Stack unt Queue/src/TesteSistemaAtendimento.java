     class FilaEstatica<T> {
    private T[] itens;
    private int nElementos;
    private int capacidade;
    private int inicio; // Representa o índice do primeiro elemento da fila (quem será o próximo a sair)
    private int fim; // Representa o índice da próxima posição livre no array (onde o novo elemento entrará)

    @SuppressWarnings("unchecked")
    public FilaEstatica(int capacidade) {
        this.capacidade = capacidade;
        // Uso de Object[] porque uma Fila padrão não exige que os itens sejam comparáveis entre si
        this.itens = (T[]) new Object[capacidade];
        this.nElementos = 0;
        this.inicio = 0;
        this.fim = 0;
    }

    // --- Métodos de Verificação ---

    public boolean estaVazia() { // isEmpty
        return nElementos == 0;
    }

    public boolean estaCheia() { // isFull
        return nElementos == capacidade;
    }

    public int tamanho() { // size
        return nElementos;
    }

    // --- Métodos de Operação ---

    // enfileirar (enqueue)
    public void enfileirar(T item) {
        if (estaCheia()) {
            throw new IllegalStateException("Fila cheia: limite de capacidade atingido.");
        }
        itens[fim] = item;

        // LÓGICA CIRCULAR: Se fim atingir o limite, volta para o índice 0
        fim = (fim + 1) % capacidade;
        nElementos++;
    }

    // desenfileirar (dequeue)
    public T desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia: não há elementos para remover.");
        }
        T item = itens[inicio];
        itens[inicio] = null; // Limpa referência para o Garbage Collector

        // LÓGICA CIRCULAR: O início também rotaciona no array
        inicio = (inicio + 1) % capacidade;
        nElementos--;
        return item;
    }

    // --- Métodos de Consulta e Exibição ---

    // consultarFrente (peek ou front)
    public T consultarFrente() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia.");
        }
        return itens[inicio];
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Fila: [ vazia ]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Frente -> ");
        for (int i = 0; i < nElementos; i++) {
            int indiceReal = (inicio + i) % capacidade;
            sb.append("[").append(itens[indiceReal]).append("]");

            if (i < nElementos - 1) {
                sb.append(" - ");
            }
        }
        sb.append(" <- Fim");
        return sb.toString();
    }
}

// Classe simples para representar um Ticket de suporte
class Ticket {
    private String cliente;
    private String assunto;

    public Ticket(String cliente, String assunto) {
        this.cliente = cliente;
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return cliente + " (" + assunto + ")";
    }
}

public class TesteSistemaAtendimento {
    public static void main(String[] args) {
        // Criamos uma fila de tickets com capacidade para 3 atendimentos
        FilaEstatica<Ticket> filaEspera = new FilaEstatica<>(3);

        System.out.println("--- Chegada de Clientes ---");
        filaEspera.enfileirar(new Ticket("Alice", "Internet lenta"));
        filaEspera.enfileirar(new Ticket("Bruno", "Troca de roteador"));
        filaEspera.enfileirar(new Ticket("Carla", "Dúvida na fatura"));

        System.out.println(filaEspera);

        System.out.println("\n--- Iniciando Atendimento ---");
        // Remove 2 pessoas para abrir espaço circular
        System.out.println("Atendendo: " + filaEspera.desenfileirar());
        System.out.println("Atendendo: " + filaEspera.desenfileirar());

        System.out.println("\n--- Novos Clientes (Ocupando espaços liberados) ---");
        // Graças à lógica circular, os novos itens ocuparão os índices 0 e 1 novamente
        filaEspera.enfileirar(new Ticket("Diego", "Cancelamento"));

        System.out.println(filaEspera);
        System.out.println("Próximo da fila: " + filaEspera.consultarFrente());
    }
}