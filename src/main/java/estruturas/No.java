package estruturas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class No<Tipo> {

    private Tipo valor;
    private No<Tipo> prox;
    private No<Tipo> ant;
    public No(Tipo valor) {
        this.valor = valor;
        this.prox = null;
        this.ant = null;
    }
}
