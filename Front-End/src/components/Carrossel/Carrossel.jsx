import { useEffect, useState } from "react";
import "./Carrossel.css";
import CardProduto from "./CardProduto";

function Carrossel() {
  const [indiceAtual, setIndiceAtual] = useState(0);
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/produtos")
      .then((resposta) => resposta.json())
      .then((dados) => setProdutos(dados))
      .catch((erro) =>
        console.error("Erro ao buscar produtos:", erro)
      );
  }, []);

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

      <button
        className="botao-carrossel"
        onClick={anterior}
      >
        <span className="seta-esquerda">
          ◀
        </span>
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

      <button
        className="botao-carrossel"
        onClick={proximo}
      >
        <span className="seta-direita">
          ▶
        </span>
      </button>

    </div>
  );
}

export default Carrossel;