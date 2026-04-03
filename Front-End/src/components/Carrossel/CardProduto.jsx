function CardProduto({ produto }) {
  return (
    <div className="card-produto">
      <div className="card-produto-conteudo">

        <div className="card-produto-placeholder">
          Produto
        </div>

        <h3 className="card-produto-nome">
          {produto.nome}
        </h3>

        <p className="card-produto-preco">
          {produto.preco}
        </p>

        <button className="card-produto-botao">
          Ver produto
        </button>

      </div>
    </div>
  );
}

export default CardProduto;