package loginUser;

import java.util.Date;

public class Tarefa {

    private int Id;
    private String Descricao;
    private Date DataCriacao;
    private Date Prazo;
    private String Status;
    private int IdTipoTarefa;

    //getters e setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getPrazo() {
        return Prazo;
    }

    public void setPrazo(Date prazo) {
        Prazo = prazo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getIdTipoTarefa() {
        return IdTipoTarefa;
    }

    public void setIdTipoTarefa(int idTipoTarefa) {
        IdTipoTarefa = idTipoTarefa;
    }
}
