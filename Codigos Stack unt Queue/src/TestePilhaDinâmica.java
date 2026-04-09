 class PilhaDinamica<T> {

    // Classe interna para representar os elos da pilha
    private class Nodo {
        T elemento;
        Nodo abaixo; // Referência para o próximo nó na pilha (o que está abaixo)

        Nodo(T elemento) {
            this.elemento = elemento;
            this.abaixo = null;
        }
    }

    private Nodo topo;
    private int nElementos;

    public PilhaDinamica() {
        this.topo = null;
        this.nElementos = 0;
    }

    // --- Métodos de Verificação ---

    public boolean estaVazia() { // isEmpty
        return topo == null;
    }

    public int tamanho() { // size
        return nElementos;
    }

    // --- Métodos de Operação ---

    public void empilhar(T elemento) { // push
        Nodo novoNodo = new Nodo(elemento);
        // O novo nó aponta para o antigo topo
        novoNodo.abaixo = topo;
        // O novo nó passa a ser o topo
        topo = novoNodo;
        nElementos++;
    }

    public T desempilhar() { // pop
        if (estaVazia()) {
            throw new IllegalStateException("Stack Underflow: A pilha está vazia.");
        }
        T valor = topo.elemento;
        // O topo passa a ser o nó que estava abaixo dele
        topo = topo.abaixo;
        nElementos--;
        return valor;
    }

    // --- Métodos de Consulta e Exibição ---

    public T consultarTopo() { // peek or top
        if (estaVazia()) {
            throw new IllegalStateException("A pilha está vazia.");
        }
        return topo.elemento;
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "Pilha: vazia";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Topo -> ");
        Nodo atual = topo;
        while (atual != null) {
            sb.append("[").append(atual.elemento).append("]");
            if (atual.abaixo != null) {
                sb.append("\n        "); // Quebra de linha para visualizar a pilha verticalmente
            }
            atual = atual.abaixo;
        }
        sb.append("\n        Base");
        return sb.toString();
    }
}

 class TestePilhaDinamica {
    public static void main(String[] args) {
        // Exemplo com Inteiros
        PilhaDinamica<Integer> p = new PilhaDinamica<>();

        System.out.println("--- Teste Pilha Dinâmica (Integer) ---");
        p.empilhar(10);
        p.empilhar(20);
        p.empilhar(30);

        System.out.println(p);
        /* Saída esperada:
           Topo -> [30]
                   [20]
                   [10]
                   Base
        */

        System.out.println("\nDesempilhando: " + p.desempilhar()); // 30
        System.out.println("Novo topo: " + p.consultarTopo());    // 20

        // Exemplo com Strings
        System.out.println("\n--- Teste Pilha Dinâmica (String) ---");
        PilhaDinamica<String> p2 = new PilhaDinamica<>();
        p2.empilhar("Prato 1");
        p2.empilhar("Prato 2");
        System.out.println(p2);
    }
}