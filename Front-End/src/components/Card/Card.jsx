import "./Card.css";

function Card({ titulo, descricao, imagem }) {
  return (
    <div className="card">
      <h2 className="card-titulo">{titulo}</h2>

      <p className="card-descricao">{descricao}</p>

      <img
        src={imagem}
        alt={titulo}
        className="card-imagem"
      />
    </div>
  );
}

export default Card;