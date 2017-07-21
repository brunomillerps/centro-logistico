# centro-logistico
Disponibilizar APIs para facilitar o abastecimento e organização de cargas em veículos de centro logístico

## Pré requisitos
* Java 8
* O projeto está configurado na porta 8089
* Testes via curl, POSTMAN ou relacionado
## Chamada das APIs

### Api que relaciona Pacotes X Caminhão X Entrega
* Não pode cadastrar vehicle e delivery 2+ vezes
* Não pode empilhar pacotes com o mesmo peso, apenas em ordem piramidal
```
POST /delivery
{
    "vehicle" : "123456",
    "deliveryId" : "1234567890"
    "packages" : [
        { "id": "1", "weight": "14.50"},
        { "id": "2", "weight": "12.15"},
        { "id": "3", "weight": "19.50"}
    ]
}

---
201 CREATED
```

### Api que retorna os passos para abastecer o Caminhão.
O processo de abastecimento do veículo possui disponível uma zona de transferência (T) e
por segurança, há algumas restrições:
* Todas as zonas (zona de abastecimento [A], zona de transferência [T] e zona do
caminhão [C]) possuem espaço somente para pacotes empilhados e somente uma
pilha pode ser formada em cada zona (inclusive no caminhão);
* Somente um pacote pode ser movimentado por vez;
* Nunca um pacote mais pesado deve ser colocado sobre um pacote mais leve;

Essa API retorna a consulta de instruções para abastecimento de carga no veículo, dado um ID de
entrega e ID de veículo. A consulta deve informar os passos para movimentação da
carga entre o centro logístico, a zona de transferência e o veículo, como no exemplo
abaixo.


```
GET /delivery/{deliveryId}/{vehicle}/step
    [
        { "step" : 1, "packageId": 2, "from": "zona de abastecimento", "to": "zona do caminhão" },
        { "step" : 2, "packageId": 1, "from": "zona de abastecimento", "to": "zona de transferência" },
        { "step" : 3, "packageId": 2, "from": "zona do caminhão", "to": "zona de transferência" },
        { "step" : 4, "packageId": 3, "from": "zona de abastecimento", "to": "zona do caminhão" },
        { "step" : 5, "packageId": 2, "from": "zona de transferência", "to": "zona de abastecimento" },
        { "step" : 6, "packageId": 1, "from": "zona de transferência", "to": "zona de caminhão" },
        { "step" : 7, "packageId": 2, "from": "zona de abastecimento", "to": "zona de caminhão" }
    ]

---
200 OK
```