package dijkstra;

import estruturas.fila.Fila;
import estruturas.lista.Lista;
import estruturas.pilha.Pilha;
import grafo.Grafo;
import grafo.Vertice;
import grafo.Aresta;

import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra {

    public static Fila<Vertice> encontrarMenorCaminho(Grafo grafo, Vertice origem, Vertice destino) {
        HashMap<Vertice, Integer> distancias = new HashMap<>();
        HashMap<Vertice, Vertice> anteriores = new HashMap<>();
        HashSet<Vertice> visitados = new HashSet<>();

        Lista<Vertice> todosVertices = grafo.vertices;
        for (int i = 0; i < todosVertices.getTamanho(); i++) {
            Vertice v = todosVertices.get(i);
            distancias.put(v, Integer.MAX_VALUE);
            anteriores.put(v, null);
        }
        distancias.put(origem, 0);

        while (visitados.size() < todosVertices.getTamanho()) {
            Vertice atual = encontrarMenorVertice(distancias, visitados);
            if (atual == null) break;
            visitados.add(atual);

            Lista<Aresta> adjacentes = atual.ArestasTotais();
            for (int i = 0; i < adjacentes.getTamanho(); i++) {
                Aresta aresta = adjacentes.get(i);
                Vertice vizinho = aresta.getTarget();
                if (!visitados.contains(vizinho)) {
                    int novaDist = distancias.get(atual) + aresta.getLength();
                    if (novaDist < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDist);
                        anteriores.put(vizinho, atual);
                    }
                }
            }
        }

        return construirCaminho(anteriores, origem, destino);
    }

    private static Vertice encontrarMenorVertice(HashMap<Vertice, Integer> distancias, HashSet<Vertice> visitados) {
        Vertice menor = null;
        int menorDistancia = Integer.MAX_VALUE;
        for (Vertice v : distancias.keySet()) {
            if (!visitados.contains(v) && distancias.get(v) < menorDistancia) {
                menor = v;
                menorDistancia = distancias.get(v);
            }
        }
        return menor;
    }

    private static Fila<Vertice> construirCaminho(HashMap<Vertice, Vertice> anteriores, Vertice origem, Vertice destino) {
        Pilha<Vertice> pilha = new Pilha<>();
        Vertice atual = destino;

        while (atual != null) {
            pilha.add(atual);
            atual = anteriores.get(atual);
        }

        Fila<Vertice> caminho = new Fila<>();
        while (!pilha.isEmpty()) {
            caminho.add(pilha.remover());
        }

        return caminho;
    }
}

