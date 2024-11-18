<%@ page import="java.sql.*, utils.Conexao, java.util.*, java.io.*" %>

<%
    String permissaoUsuario = (String) session.getAttribute("permissaoUsuario");
%>

<!DOCTYPE html>
<html> 
<head>
  <meta charset="UTF-8">   
  <link rel="shortcut icon" href="imagens/logo.png">
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="estilos/style.css"/>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet"/>
</head>
<body class="bg-gray-100 font-roboto">
    <nav class="bg-blue-600 p-4 flex justify-between items-center">
        <div class="flex items-center">
            <img alt="Logo" class="mr-4" height="40" src="imagens/logo.png" width="40"/>
            <a class="text-white text-lg font-bold" href="#Home.html">Tarefas</a>
        </div>
        <div class="flex space-x-8">
            <a class="text-white" href="Home.html">Home</a>
            <a class="text-white" href="Colaboradores.jsp">Colaboradores</a>
        </div>
        <div class="flex items-center">
            <span class="text-white mr-2"><%= permissaoUsuario != null ? permissaoUsuario : "Usuário não autenticado" %></span>
            <i class="fas fa-user-circle text-white text-2xl"></i>
        </div>
    </nav>
    
    <div class="container mx-auto mt-8">
        <h1 class="text-3xl font-bold text-center mb-8">TAREFAS</h1>

        <div class="flex justify-center space-x-4 mb-8">
            <% if ("Admin".equalsIgnoreCase(permissaoUsuario)) { %>
                <!-- Botão para adicionar tarefa (visível apenas para Administradores) -->
                <button id="adicionarTarefa" class="flex items-center border-2 border-black rounded-full px-4 py-2" onclick="abrirAddTarefaModal()">
                    <span class="mr-2">Adicionar Tarefa</span>
                    <i class="fas fa-plus-circle text-blue-600"></i>
                </button>
                <!-- Botão para gerar relatório (visível para Administradores e Analistas) -->
                <button class="flex items-center border-2 border-black rounded-full px-4 py-2" onclick="gerarRelatorio()">
                    <span class="mr-2">Gerar Relatório</span>
                    <i class="fas fa-file-alt text-blue-600"></i>
                </button>
            <% } else if ("Analista".equalsIgnoreCase(permissaoUsuario)) { %>
                <!-- Botão para gerar relatório (visível apenas para Analistas) -->
                <button class="flex items-center border-2 border-black rounded-full px-4 py-2" onclick="gerarRelatorio()">
                    <span class="mr-2">Gerar Relatório</span>
                    <i class="fas fa-file-alt text-blue-600"></i>
                </button>
            <% } %>
        </div>
    </div>

    <div class="container mx-auto mt-8 max-w-6xl">
        <table class="w-full bg-white border-collapse border border-gray-300" id="tabelaTarefas">
            <thead>
                <tr>
                    <th class="py-2 px-4 border-b">ID</th>
                    <th class="py-2 px-4 border-b">Descrição</th>
                    <th class="py-2 px-4 border-b">Tipo de tarefa</th>
                    <th class="py-2 px-4 border-b">Data de inclusão</th>
                    <th class="py-2 px-4 border-b">Prazo</th>
                    <th class="py-2 px-4 border-b">Status</th>
                </tr>
            </thead>
            <tbody id="corpoTabelaTarefas">
    <%
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql  = "SELECT t.id, t.descricao, tt.descricao AS tipo_tarefa, t.data_criacao, t.prazo, t.status "
                        + "FROM tarefa t "
                        + "JOIN tipo_tarefa tt ON t.tipo_tarefa_id = tt.id "
                        + "ORDER BY id";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String descricao = rs.getString("t.descricao");
                String tipoTarefa = rs.getString("tipo_tarefa");
                String dataInclusao = rs.getString("t.data_criacao");
                String prazo = rs.getString("t.prazo");
                String status = rs.getString("t.status");
                int id = rs.getInt("t.id");
    %>
                <tr onclick="editarTarefa('<%= id %>', '<%= descricao %>', '<%= tipoTarefa %>', '<%= prazo %>', '<%= status %>')">
                    <td class="py-2 px-4 border-b"><%= id %></td>
                    <td class="py-2 px-4 border-b"><%= descricao %></td>
                    <td class="py-2 px-4 border-b"><%= tipoTarefa %></td>
                    <td class="py-2 px-4 border-b"><%= dataInclusao %></td>
                    <td class="py-2 px-4 border-b"><%= prazo %></td>
                    <td class="py-2 px-4 border-b"><%= status %></td>
                </tr>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    %>
</tbody>
        </table>
    </div>

<!-- Modal Adicionar Tarefa -->
<div class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center hidden" id="modalAdicionarTarefa">
    <div class="bg-white p-8 rounded-lg shadow-lg w-1/2">
        <h2 class="text-2xl font-bold mb-4">Detalhes da Tarefa</h2>
        
        <form id="formTarefa" action="AdicionarTarefa.jsp" method="POST">
            <input type="hidden" id="id" name="id">
            
            <p class="mb-2">
                <strong>Descrição:</strong>
                <input type="text" id="descricaoTarefa" name="descricao" class="border rounded px-2 py-1 w-full" required />
            </p>

            <p class="mb-2">
                <strong>Prazo:</strong>
                <input type="date" id="prazoTarefa" name="prazo" class="border rounded px-2 py-1 w-full" required />
            </p>

            <p class="mb-2">
                <strong>Tipo de Tarefa:</strong>
                <select id="tipoTarefa" name="tipoTarefa" class="border rounded px-2 py-1 w-full" required>
                    <option value="1">Manutenção Corretiva</option>
                    <option value="2">Manutenção Geral</option>
                    <option value="3">Manutenção Preventiva</option>
                    <option value="4">Desenvolvimento Novo</option>
                    <option value="5">Melhoria de Sistema</option>
                </select>
            </p>

            <div class="flex justify-end space-x-4 mt-4">
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">Salvar</button>
                <button type="button" class="bg-gray-500 text-white px-4 py-2 rounded" onclick="fecharModal()">Fechar</button>
            </div>
        </form>
    </div>
</div>
        
<!-- Modal Editar Tarefa -->
<div class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center hidden" id="modalEditarTarefa">
    <div class="bg-white p-8 rounded-lg shadow-lg w-1/2">
        <h2 class="text-2xl font-bold mb-4">Editar Tarefa</h2>

        <form id="formEditarTarefa" action="AtualizarTarefa.jsp" method="POST">
            <input type="hidden" name="id" id="idTarefaEditar">
            
            <p class="mb-2">
                <strong>Descrição:</strong>
                <input type="text" id="descricaoTarefaEditar" name="descricao" class="border rounded px-2 py-1 w-full" required>
            </p>

            <p class="mb-2">
                <strong>Tipo de Tarefa:</strong>
                <select id="tipoTarefaEditar" name="tipoTarefa" class="border rounded px-2 py-1 w-full" required>
                    <option value="1">Manutenção Corretiva</option>
                    <option value="2">Manutenção Geral</option>
                    <option value="3">Manutenção Preventiva</option>
                    <option value="4">Desenvolvimento Novo</option>
                    <option value="5">Melhoria de Sistema</option>
                </select>
            </p>

            <p class="mb-2">
                <strong>Prazos:</strong>
                <input type="date" id="prazoTarefaEditar" name="prazo" class="border rounded px-2 py-1 w-full" required>
            </p>

            <p class="mb-2">
                <strong>Status:</strong>
                <select id="statusTarefaEditar" name="status" class="border rounded px-2 py-1 w-full" required>
                    <option value="Aberta">Aberto</option>
                    <option value="Atrasada">Atrasada</option>
                    <option value="Concluida">Concluída</option>
                </select>
            </p>

            <div class="flex justify-end space-x-4 mt-4">
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">Atualizar</button>
                <button type="button" class="bg-gray-500 text-white px-4 py-2 rounded" onclick="fecharModal()">Fechar</button>
                <button type="button" id="excluirBotao" class="bg-gray-500 text-white px-4 py-2 rounded" onclick="excluirTarefa()">Excluir</button>

            </div>
        </form>
    </div>
</div>
    <script>
        const permissaoUsuario = "<%= permissaoUsuario %>";
        
        function editarTarefa(id, descricao, tipoTarefa, prazo, status) {
            if (permissaoUsuario === "Analista") {
            alert("Analistas não têm permissão para editar tarefas.");
            return; // Interrompe a execução da função
        }
        
            // Se o usuário for um Desenvolvedor, permitir apenas a edição do status
        if (permissaoUsuario === "Dev") {
            // Desabilitar os campos que não são "Status" (para que o Desenvolvedor não edite)
            document.getElementById('descricaoTarefaEditar').disabled = true;
            document.getElementById('tipoTarefaEditar').disabled = true;
            document.getElementById('prazoTarefaEditar').disabled = true;
        } else {
            // Caso não seja Desenvolvedor, os campos podem ser editados normalmente
            document.getElementById('descricaoTarefaEditar').disabled = false;
            document.getElementById('tipoTarefaEditar').disabled = false;
            document.getElementById('prazoTarefaEditar').disabled = false;
        }
            // Preenche os campos do modal de edição com os dados da tarefa
            document.getElementById('idTarefaEditar').value = id;
            document.getElementById('descricaoTarefaEditar').value = descricao;
            document.getElementById('tipoTarefaEditar').value = tipoTarefa;
            document.getElementById('prazoTarefaEditar').value = prazo;
            document.getElementById('statusTarefaEditar').value = status;

            // Exibe o modal de edição
            document.getElementById('modalEditarTarefa').classList.remove('hidden');
        }


        function fecharModal() {
            // Fecha os modais
            document.getElementById('modalAdicionarTarefa').classList.add('hidden');
            document.getElementById('modalEditarTarefa').classList.add('hidden');
        }

        function abrirAddTarefaModal() {
            // Exibe o modal de adicionar tarefa
            document.getElementById('modalAdicionarTarefa').classList.remove('hidden');
        }
        
        function excluirTarefa() {
            // Captura o ID da tarefa do modal de edição
            const idTarefa = document.getElementById("idTarefaEditar").value;

            // Confirmação antes de excluir
            var resultado = confirm("Tem certeza que deseja excluir a tarefa " + idTarefa + "?");

            // Verifica a resposta do usuário
            if (resultado) {
                // Caso o usuário clique em "OK" (true), envia o idTarefa como parâmetro GET
                window.location.href = 'ExcluirTarefa.jsp?idTarefa=' + idTarefa;
            }
        }
        
        function gerarRelatorio() {
        var tarefas = [];
        var linhasTabela = document.querySelectorAll('#tabelaTarefas tbody tr');

        linhasTabela.forEach(function(linha) {
            var id = linha.cells[0].textContent;
            var descricao = linha.cells[1].textContent;
            var tipoTarefa = linha.cells[2].textContent;
            var dataInclusao = linha.cells[3].textContent;
            var prazo = linha.cells[4].textContent;
            var status = linha.cells[5].textContent;

            tarefas.push({
                id: id,
                descricao: descricao,
                tipoTarefa: tipoTarefa,
                dataInclusao: dataInclusao,
                prazo: prazo,
                status: status
            });
        });

        if (tarefas.length > 0) {
            var relatorio = 'Relatório de Tarefas:\n\n';

            tarefas.forEach(function(tarefa) {
                relatorio += 'ID: ' + tarefa.id + '\n';
                relatorio += 'Descrição: ' + tarefa.descricao + '\n';
                relatorio += 'Tipo de Tarefa: ' + tarefa.tipoTarefa + '\n';
                relatorio += 'Data de Inclusão: ' + tarefa.dataInclusao + '\n';
                relatorio += 'Prazo: ' + tarefa.prazo + '\n';
                relatorio += 'Status: ' + tarefa.status + '\n';
                relatorio += '------------------------------------\n';
            });

            var textoRelatorio = encodeURIComponent(relatorio);
            var link = document.createElement('a');
            link.href = 'data:text/plain;charset=utf-8,' + textoRelatorio;
            link.download = 'relatorio_tarefas.txt';
            link.click();
        } else {
            alert('Nenhuma tarefa encontrada para gerar o relatório.');
        }
    }


    </script>
</body>
</html>
