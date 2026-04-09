

     class PilhaEstatica {
    private char[] itens;
    private int topo; 
    private int capacidade;

    public PilhaEstatica(int capacidade) {
        this.capacidade = capacidade;
        this.itens = new char[capacidade];
        this.topo = -1; 
    }



    public boolean estaVazia() {
        return topo == -1;
    }

    public boolean estaCheia() {
        return topo == capacidade - 1;
    }

    public int tamanho() {
        return topo + 1;
    }



    //push
    public void empilhar(char item) {
        if (estaCheia()) {
            throw new IllegalStateException("A pilha está cheia");
        }
        topo++;
        itens[topo] = item;
    }


    //pop
    public char desempilhar() {
        if (estaVazia()) {
            throw new IllegalStateException("A pilha está vazia");
        }
        char item = itens[topo];
        topo--;
        return item;
    }

    

    //peek
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
        for (int i = topo; i >= 0; i--) {
            sb.append("[").append(itens[i]).append("]");
            if (i > 0) {
                sb.append("\n        "); 
            }
        }
        sb.append("\n        Base");
        return sb.toString();
    }
}
    class TestePilhaExpressao {

    
    public static boolean verificaExpressao(String expressao) {
        PilhaEstatica pilha = new PilhaEstatica(expressao.length());

        for (int i = 0; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);

            
            if (caractere == '(' || caractere == '[' || caractere == '{') {
                pilha.empilhar(caractere);
            }
            
            else if (caractere == ')' || caractere == ']' || caractere == '}') {
                if (pilha.estaVazia()) {
                    return false; 
                }

                char topoPilha = pilha.desempilhar();
                if ((caractere == ')' && topoPilha != '(') ||
                        (caractere == ']' && topoPilha != '[') ||
                        (caractere == '}' && topoPilha != '{')) {
                    return false; 
                }
            }
        }

        
        return pilha.estaVazia();
    }

    public static void main(String[] args) {
        
        System.out.println("--- Testes Básicos de Pilha ---");
        PilhaEstatica p = new PilhaEstatica(5);
        p.empilhar('A');
        p.empilhar('B');
        System.out.println(p);
        System.out.println("Desempilhando: " + p.desempilhar());
        System.out.println(p);

     
        System.out.println("\n--- Testes de Verificação de Expressão ---");
        String expressao1 = "((a + b) * (c - d))";
        String expressao2 = "((a + b) * (c - d)";
        String expressao3 = "[(a + b) * {c - d}]";
        String expressao4 = "([)]"; 

        System.out.println("Expressão 1 balanceada? " + verificaExpressao(expressao1)); // true
        System.out.println("Expressão 2 balanceada? " + verificaExpressao(expressao2)); // false
        System.out.println("Expressão 3 balanceada? " + verificaExpressao(expressao3)); // true
        System.out.println("Expressão 4 balanceada? " + verificaExpressao(expressao4)); // false
    }
}
