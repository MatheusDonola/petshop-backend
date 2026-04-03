import { useState } from "react";
import "./Carrossel.css";
import CardProduto from "./CardProduto";

function Carrossel() {

  const [indiceAtual, setIndiceAtual] = useState(0);

  const produtos = [
  {
    id: 1,
    nome: "Ração Premium",
    preco: "R$ 89,90"
  },
  {
    id: 2,
    nome: "Coleira Azul",
    preco: "R$ 39,90"
  },
  {
    id: 3,
    nome: "Brinquedo Mordedor",
    preco: "R$ 24,90"
  },
  {
    id: 4,
    nome: "Shampoo Pet",
    preco: "R$ 32,90"
  },
  {
    id: 5,
    nome: "Caminha",
    preco: "R$ 119,90"
  },
  {
    id: 6,
    nome: "Peitoral",
    preco: "R$ 49,90"
  },
  {
    id: 7,
    nome: "Pote de Ração",
    preco: "R$ 27,90"
  }
];

  const proximo = () => {
    if (indiceAtual < produtos.length - 5) {
      setIndiceAtual(indiceAtual + 1);
    }
  };

  const anterior = () => {
    if (indiceAtual > 0) {
      setIndiceAtual(indiceAtual - 1);
    }
  };

  return (
    <div className="carrossel">

      <button className="botao-carrossel" onClick={anterior}>
        ◀
      </button>

      <div className="carrossel-janela">
        <div
          className="carrossel-trilha"
          style={{
            transform: `translateX(-${indiceAtual * 20}%)`
          }}
        >
          {produtos.map((produto) => (
            <CardProduto
              key={produto.id}
              produto={produto}
            />
          ))}
        </div>
      </div>

      <button className="botao-carrossel" onClick={proximo}>
        ▶
      </button>

    </div>
  );
}

export default Carrossel;