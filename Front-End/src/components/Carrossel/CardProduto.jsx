import { useNavigate } from "react-router-dom";

function CardProduto({ produto }) {
  const navigate = useNavigate();

  return (
    <div className="card-produto">
      <div className="card-produto-conteudo">
        <img
          src={produto.imagemUrl}
          alt={produto.nome}
          className="card-produto-imagem"
        />

        <h3 className="card-produto-nome">
          {produto.nome}
        </h3>

        <p className="card-produto-preco">
          R$ {Number(produto.preco).toFixed(2).replace(".", ",")}
        </p>

        <button
          className="card-produto-botao"
          onClick={() => navigate(`/produto/${produto.id}`)}
        >
          Ver produto
        </button>
      </div>
    </div>
  );
}

export default CardProduto;