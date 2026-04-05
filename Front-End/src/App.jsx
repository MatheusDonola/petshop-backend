import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState } from "react";

import Navbar from "./components/Navbar/Navbar";
import Home from "./pages/Home";
import ProdutoDetalhe from "./pages/ProdutoDetalhe";
import Carrinho from "./pages/Carrinho";

function App() {

  const [carrinho, setCarrinho] = useState([]);

  function adicionarAoCarrinho(produto) {

    const produtoExistente = carrinho.find(
      (item) => item.id === produto.id
    );

    if (produtoExistente) {

      const novoCarrinho = carrinho.map((item) =>
        item.id === produto.id
          ? { ...item, quantidade: item.quantidade + 1 }
          : item
      );

      setCarrinho(novoCarrinho);

    } else {

      setCarrinho([
        ...carrinho,
        { ...produto, quantidade: 1 }
      ]);

    }

    console.log("Carrinho:", carrinho);
  }

  return (
    <BrowserRouter>

      <Navbar />

      <Routes>

        <Route
          path="/"
          element={<Home />}
        />

        <Route
          path="/produto/:id"
          element={
            <ProdutoDetalhe
              adicionarAoCarrinho={adicionarAoCarrinho}
            />
          }
        />

        <Route
        path="/carrinho"
        element={<Carrinho carrinho={carrinho} />}
      />

      </Routes>

    </BrowserRouter>
  );
}

export default App;