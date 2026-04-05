import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

function ProdutoDetalhe({ adicionarAoCarrinho }) {
  const { id } = useParams();
  const navigate = useNavigate();

  const [produto, setProduto] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/produtos/${id}`)
      .then((resposta) => resposta.json())
      .then((dados) => setProduto(dados))
      .catch((erro) =>
        console.error("Erro ao buscar produto:", erro)
      );
  }, [id]);

  if (!produto) {
    return <p>Carregando produto...</p>;
  }

  return (
    <div style={{ padding: "40px" }}>

      {/* BOTÃO VOLTAR */}
      <button
        onClick={() => navigate(-1)}
        style={{
          marginBottom: "30px",
          backgroundColor: "#ff7a00",
          color: "white",
          border: "none",
          padding: "10px 18px",
          borderRadius: "8px",
          cursor: "pointer",
          fontWeight: "bold"
        }}
      >
        ← Voltar
      </button>

      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          gap: "60px",
          maxWidth: "1000px",
          margin: "0 auto"
        }}
      >
        {/* IMAGEM */}
        <div>
          <img
            src={produto.imagemUrl}
            alt={produto.nome}
            style={{
              width: "350px",
              borderRadius: "12px"
            }}
          />
        </div>

        {/* INFORMAÇÕES */}
        <div style={{ maxWidth: "400px" }}>
          <h1 style={{ color: "#111111" }}>
            {produto.nome}
          </h1>

          <p
            style={{
              marginTop: "15px",
              color: "#1f1e1e",
              fontSize: "16px"
            }}
          >
            {produto.descricao}
          </p>

          <p style={{ marginTop: "15px" }}>
            <strong>Marca:</strong>{" "}
            {produto.marca}
          </p>

          <p
            style={{
              marginTop: "20px",
              fontSize: "22px",
              fontWeight: "bold",
              color: "#ff7a00"
            }}
          >
            R$ {Number(produto.preco)
              .toFixed(2)
              .replace(".", ",")}
          </p>
          <button
          onClick={() => adicionarAoCarrinho(produto)}
          style={{
            marginTop: "20px",
            backgroundColor: "#ff7a00",
            color: "white",
            border: "none",
            padding: "12px 20px",
            borderRadius: "8px",
            cursor: "pointer",
            fontWeight: "bold"
          }}
        >
          🛒 Adicionar ao carrinho
        </button>

        </div>
      </div>
    </div>
  );
}

export default ProdutoDetalhe;