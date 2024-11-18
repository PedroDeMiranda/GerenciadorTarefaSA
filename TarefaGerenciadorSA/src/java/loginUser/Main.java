package loginUser;

import java.sql.Connection;
import java.sql.Date; // Para usar java.sql.Date
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import utils.Conexao;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, ParseException { 
        Connection con = Conexao.conectar();
        if (con == null) {
            System.out.println("Não deu certo");
        } else {
            System.out.println("Banco conectado com sucesso");
        }
    }
    
    public static Date stringDate(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = sdf.parse(string);
        return new Date(utilDate.getTime());
    }
}

