import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { QrCode as QrIcon, Copy, CheckCircle, Wallet } from 'lucide-react';
import QRCode from 'qrcode'; // Biblioteca base

function App() {
  const [valor, setValor] = useState('');
  const [chave, setChave] = useState('');
  const [cobranca, setCobranca] = useState(null);
  const [qrImageUrl, setQrImageUrl] = useState('');
  const [loading, setLoading] = useState(false);

  const API_URL = 'https://zany-capybara-4rpw9xjww962jxvq-8080.app.github.dev/api/cobrancas';
  const authConfig = { auth: { username: 'admin', password: 'admin123' } };

  // Efeito para gerar a imagem do QR Code sempre que a cobrança mudar
  useEffect(() => {
    if (cobranca && cobranca.pixCopiaCola) {
      QRCode.toDataURL(cobranca.pixCopiaCola, { width: 300, margin: 2 }, (err, url) => {
        if (!err) setQrImageUrl(url);
      });
    }
  }, [cobranca]);

  const gerarPix = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post(API_URL, { valor: parseFloat(valor), chavePix: chave }, authConfig);
      setCobranca(response.data);
    } catch (error) {
      alert("Erro ao conectar com o Backend! Verifique a porta 8080.");
    } finally { setLoading(false); }
  };

  const simularPagamento = async () => {
    setLoading(true);
    try {
      const response = await axios.post(`${API_URL}/${cobranca.id}/pagar`, {}, authConfig);
      setCobranca(response.data);
    } catch (error) { alert("Erro ao processar pagamento."); }
    finally { setLoading(false); }
  };

  return (
    <div style={{ fontFamily: 'sans-serif', padding: '40px', maxWidth: '450px', margin: '40px auto', border: '1px solid #eee', borderRadius: '15px', boxShadow: '0 4px 12px rgba(0,0,0,0.1)', textAlign: 'center' }}>
      <h1 style={{ display: 'flex', alignItems: 'center', gap: '12px', justifyContent: 'center' }}>
        <QrIcon color="#24d" size={32} /> Pix Gateway
      </h1>

      {!cobranca ? (
        <form onSubmit={gerarPix} style={{ display: 'flex', flexDirection: 'column', gap: '15px', textAlign: 'left' }}>
          <label>Valor:</label>
          <input type="number" step="0.01" value={valor} onChange={(e) => setValor(e.target.value)} required style={{ padding: '10px', borderRadius: '8px', border: '1px solid #ddd' }} />
          <label>Chave Pix:</label>
          <input type="email" value={chave} onChange={(e) => setChave(e.target.value)} required style={{ padding: '10px', borderRadius: '8px', border: '1px solid #ddd' }} />
          <button type="submit" disabled={loading} style={{ padding: '14px', background: '#24d', color: '#fff', border: 'none', borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold' }}>
            {loading ? 'Processando...' : 'Gerar Cobrança'}
          </button>
        </form>
      ) : (
        <div style={{ background: '#fcfcfc', padding: '20px', borderRadius: '12px', border: '1px dashed #24d' }}>
          {cobranca.status === 'PAGO' ? (
            <CheckCircle color="#28a745" size={80} style={{ margin: '20px auto' }} />
          ) : (
            <div style={{ background: 'white', padding: '15px', borderRadius: '10px', display: 'inline-block', margin: '10px auto', boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }}>
              {/* USANDO UMA TAG IMG COMUM - NÃO DÁ ERRO DE COMPONENTE */}
              {qrImageUrl && <img src={qrImageUrl} alt="QR Code" style={{ width: '200px' }} />}
            </div>
          )}

          <h3>{cobranca.status === 'PAGO' ? 'Pago!' : 'Escaneie o QR Code'}</h3>
          <p style={{ fontSize: '24px', fontWeight: 'bold' }}>R$ {cobranca.valor.toFixed(2)}</p>
          
          <textarea readOnly value={cobranca.pixCopiaCola} style={{ width: '100%', height: '50px', fontSize: '10px', marginBottom: '10px' }} />

          {cobranca.status !== 'PAGO' && (
            <button onClick={simularPagamento} disabled={loading} style={{ width: '100%', padding: '12px', background: '#28a745', color: '#fff', border: 'none', borderRadius: '6px', fontWeight: 'bold', cursor: 'pointer' }}>
              {loading ? 'Confirmando...' : 'Simular Recebimento'}
            </button>
          )}
          <button onClick={() => { setCobranca(null); setQrImageUrl(''); }} style={{ marginTop: '15px', border: 'none', background: 'none', color: '#666', cursor: 'pointer', textDecoration: 'underline' }}>Nova Cobrança</button>
        </div>
      )}
    </div>
  );
}

export default App;