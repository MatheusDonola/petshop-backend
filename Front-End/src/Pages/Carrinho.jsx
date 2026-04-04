function Carrinho({ carrinho }) {

  const total = carrinho.reduce((soma, item) => {
    return soma + item.preco * item.quantidade;
  }, 0);

  return (
    <div style={{ padding: "40px" }}>

      <h1>Seu Carrinho</h1>

      {carrinho.length === 0 ? (

        <p style={{ marginTop: "20px" }}>
          Seu carrinho está vazio.
        </p>

      ) : (

        <div style={{ marginTop: "30px" }}>

          {carrinho.map((item) => (

            <div
              key={item.id}
              style={{
                display: "flex",
                alignItems: "center",
                gap: "20px",
                marginBottom: "20px",
                borderBottom: "1px solid #ddd",
                paddingBottom: "15px"
              }}
            >

              <img
                src={item.imagemUrl}
                alt={item.nome}
                style={{
                  width: "80px",
                  borderRadius: "8px"
                }}
              />

              <div>

                <h3>{item.nome}</h3>

                <p>
                  Quantidade: {item.quantidade}
                </p>

                <p>
                  R$ {Number(item.preco)
                    .toFixed(2)
                    .replace(".", ",")}
                </p>

              </div>

            </div>

          ))}

          <h2 style={{ marginTop: "30px" }}>
            Total: R$ {total
              .toFixed(2)
              .replace(".", ",")}
          </h2>

        </div>

      )}

    </div>
  );
}

export default Carrinho;