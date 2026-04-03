import "./Card.css";

function Card({ titulo, descricao, imagem }) {
  return (
    <div className="card">

      <h2>{titulo}</h2>

      <p className="card-descricao">
      {descricao}
      </p>

      <img
        src={imagem}
        alt={titulo}
      />

    </div>
  );
}

export default Card;