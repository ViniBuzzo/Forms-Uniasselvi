<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sucesso</title>

   
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.3/css/select.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.9/css/responsive.dataTables.css">
    
    <style>
        body { background-color: #f0f7fc !important; color: #333333; margin: 0; }
        #banner { width: 100%; }
        #tabelaFuncionarios_wrapper { background-color: #f0f7fc !important; }
        #tabelaFuncionarios thead th { color: #333333; }
        #tabelaFuncionarios tbody tr { background-color: #ffffff; }
        #tabelaFuncionarios tbody tr.selected { background-color: #0d6efd !important; }
        #tabelaFuncionarios_info { color: #333333; }
        #tabelaFuncionarios_paginate .paginate_button:hover { background-color: #111111 !important; }
    </style>

    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.js"></script>
</head>
<body>
    <img id="banner" src="imagens/Banner.png" alt="Banner da Empresa">
    <h2>Cadastro efetuado com sucesso!</h2>

    <div style="float: right; width: 100%;">
        <table id="tabelaFuncionarios" class="display responsive" style="width: 100%;">
            
        </table>
    </div>

    <script>
        // Obtém o JSON dos funcionários do atributo da requisição
        var jsonFuncionarios = ${jsonFuncionarios};

        // Inicializa o DataTables com os dados JSON e estilos personalizados
        $(document).ready(function() {
            $('#tabelaFuncionarios').DataTable({
                data: jsonFuncionarios,
                columns: [
                    { data: 'codigo' },
                    { data: 'nome' },
                    { data: 'cargo' },
                    { data: 'descricaoCargo' },
                    { data: 'setor' },
                    { data: 'salario' }
                ],
                select: true,
                responsive: true,
                style: {
                    rowBackground: '#ffffff',
                    rowSelected: '#0d6efd',
                    controlText: '#333333',
                    pagingButtonHover: '#111111'
                }
            });
        });
    </script>
</body>
</html>
