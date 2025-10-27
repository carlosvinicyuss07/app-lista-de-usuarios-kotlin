# 📱 Cadastro de Usuários (Kotlin + MVI + Room)

Aplicativo Android desenvolvido em **Kotlin**, com arquitetura **MVI (Model–View–Intent)**, utilizando **Room Database** para persistência local.  
O app tem como objetivo gerenciar um **CRUD de usuários**, com validação em tempo real dos campos, seleção de foto de perfil (via **Photo Picker** ou câmera), e exibição dos cadastros em uma lista na tela inicial (**HomeFragment**).

---

## 🚀 Funcionalidades

- 🧾 **Cadastro de usuários completo**  
  Inclui campos como nome, e-mail, telefone, endereço e dados da empresa.

- 🖼️ **Foto de perfil personalizada**  
  O usuário pode escolher uma imagem da galeria ou tirar uma foto na hora, com suporte ao **Photo Picker (API 33+)** e compatibilidade com versões anteriores do Android.

- ✅ **Validação em tempo real (MVI)**  
  O formulário realiza validações automáticas conforme o usuário digita:
  - Nome mínimo de 3 caracteres  
  - E-mail válido e não duplicado  
  - Campos obrigatórios não vazios  
  - Habilitação dinâmica do botão “Salvar” apenas quando todos os campos forem válidos  

- 🗂️ **Lista de usuários (HomeFragment)**  
  Exibe os usuários cadastrados localmente, com foto, nome e e-mail.  
  Diferencia visualmente usuários cadastrados **localmente** de usuários **vindos da API** (cores distintas nos cards).

- 🔍 **Toolbar personalizada**  
  Toolbar configurada na Activity principal, com controle dinâmico de ícones:
  - O botão “Search” é exibido apenas na tela Home  
  - O botão “Settings” permanece disponível globalmente  

- 💾 **Persistência local com Room**  
  Todas as informações são armazenadas localmente em um banco de dados Room (`ListaDeUsuariosDatabase`).

- ⚙️ **Arquitetura moderna e desacoplada**  
  - MVI (Model–View–Intent) para controle de estado  
  - ViewModel com `StateFlow` e `SharedFlow`  
  - Repository Pattern  
  - Funções puras reutilizáveis para validação  
  - Atualizações reativas de UI via `collect` em coroutines  

---

## 🧩 Padrão MVI (Model–View–Intent)

O app segue o padrão **MVI**, onde cada camada tem responsabilidades claras:

| Camada | Responsabilidade |
|--------|------------------|
| **Intent** | Representa ações do usuário (ex: `NameChanged`, `Submit`) |
| **ViewModel** | Processa intents, atualiza o `State` e emite efeitos |
| **State** | Mantém o estado atual da tela (campos, erros, loading, etc.) |
| **View (Fragment)** | Observa o estado e atualiza a UI de forma reativa |

---
## ⚡ Tecnologias e Bibliotecas

- Kotlin

- Coroutines / Flow

- AndroidX ViewModel

- Room Database

- Photo Picker API

- Material Components

- RecyclerView

- Lifecycle KTX

--- 

## 🧑‍💻 Autor

Carlos Vinícyus Silva Nascimento <br>
Desenvolvido como projeto de estudo de arquitetura moderna Android e boas práticas em Kotlin.

## 🧾 Licença

Este projeto é de uso educacional e pode ser livremente adaptado e distribuído.
