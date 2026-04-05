import "./Navbar.css";
import logo from "../../assets/logo.png";
import carrinho from "../../assets/carrinho.png";
import perfil from "../../assets/perfil.png";
import pata from "../../assets/pata.png";
import lupa from "../../assets/lupa.png";
import { useNavigate } from "react-router-dom";

function Navbar() {

  const navigate = useNavigate();
  
  return (
    <header className="navbar">
      <div className="navbar__logo">
        <img
          src={logo}
          alt="PetHub Logo"
          className="navbar__logo-img"
        />
        <h1>PetHub</h1>
      </div>

      <div className="navbar__search">
        <input
          type="text"
          placeholder="Buscar produtos..."
          className="navbar__search-input"
        />
        <img
          src={lupa}
          alt="Buscar"
          className="navbar__search-icon"
        />
      </div>

      <nav className="navbar__menu">
        <a href="#" className="navbar__pets">
          <img
            src={pata}
            alt="Pets"
            className="navbar__pets-img"
          />
          PETS
        </a>

        <a href="#" className="navbar__entrar">
          <img
            src={perfil}
            alt="Entrar"
            className="navbar__entrar-img"
          />
          ENTRAR
        </a>

        <a href="#" className="navbar__carrinho">
          <img
            src={carrinho}
            onClick={() => navigate("/carrinho")}
            alt="Carrinho"
            className="navbar__carrinho-img"
          />
        </a>
      </nav>
    </header>
  );
}

export default Navbar;