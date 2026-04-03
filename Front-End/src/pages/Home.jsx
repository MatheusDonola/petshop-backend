import Card from "../components/Card/Card";
import card1Img from "../assets/card1.svg";
import card2Img from "../assets/card2.svg";
import card3Img from "../assets/card3.svg";
import "./Home.css";

function Home() {
  return (
    <div className="home-cards">
      <Card
        titulo="Produtos"
        descricao="Temos os melhores produtos para seu precioso companheiro"
        imagem={card1Img}
      />

      <Card
        titulo="Serviços"
        descricao="Aqui você encontra tudo que ele precisa para se cuidar"
        imagem={card2Img}
      />

      <Card
        titulo="Cadastre-se"
        descricao="Ainda não se cadastrou? Cadastre seus pets!"
        imagem={card3Img}
      />
    </div>
  );
}

export default Home;